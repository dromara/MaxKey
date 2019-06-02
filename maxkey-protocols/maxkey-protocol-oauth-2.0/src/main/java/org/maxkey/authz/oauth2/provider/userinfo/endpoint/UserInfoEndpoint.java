package org.maxkey.authz.oauth2.provider.userinfo.endpoint;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.authz.oauth2.common.exceptions.OAuth2Exception;
import org.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.maxkey.authz.oauth2.provider.token.DefaultTokenServices;
import org.maxkey.constants.BOOLEAN;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.crypto.jwt.encryption.service.JwtEncryptionAndDecryptionService;
import org.maxkey.crypto.jwt.encryption.service.impl.RecipientJwtEncryptionAndDecryptionServiceBuilder;
import org.maxkey.crypto.jwt.signer.service.JwtSigningAndValidationService;
import org.maxkey.crypto.jwt.signer.service.impl.SymmetricSigningAndValidationServiceBuilder;
import org.maxkey.dao.service.ApplicationsService;
import org.maxkey.dao.service.UserInfoService;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.Applications;
import org.maxkey.domain.apps.oauth2.provider.ClientDetails;
import org.maxkey.util.Instance;
import org.maxkey.util.JsonUtils;
import org.maxkey.util.StringGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Controller
@RequestMapping(value = { "/api" })
public class UserInfoEndpoint {
	final static Logger _logger = LoggerFactory.getLogger(UserInfoEndpoint.class);	
	@Autowired
	@Qualifier("oauth20JdbcClientDetailsService")
	private ClientDetailsService clientDetailsService;
	
	@Autowired
	@Qualifier("oauth20TokenServices")
	private DefaultTokenServices oauth20tokenServices;
	
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("applicationsService")
	protected ApplicationsService applicationsService;
	
	@Autowired
	@Qualifier("jwtSignerValidationService")
	private JwtSigningAndValidationService jwtSignerValidationService;
	
	@Autowired
	@Qualifier("jwtEncryptionService")
	private JwtEncryptionAndDecryptionService jwtEnDecryptionService; 
	
	private SymmetricSigningAndValidationServiceBuilder symmetricJwtSignerServiceBuilder
					=new SymmetricSigningAndValidationServiceBuilder();

	private RecipientJwtEncryptionAndDecryptionServiceBuilder recipientJwtEnDecryptionServiceBuilder
					=new RecipientJwtEncryptionAndDecryptionServiceBuilder();

	
	OAuthDefaultUserInfoAdapter defaultOAuthUserInfoAdapter=new OAuthDefaultUserInfoAdapter();
	
	@RequestMapping(value="/oauth/v20/me",produces="text/plain;charset=UTF-8") 
	@ResponseBody
	public String apiV20UserInfo(
			@RequestParam(value = "access_token", required = true) String access_token) {
			String principal="";
			if (!StringGenerator.uuidMatches(access_token)) {
				return accessTokenFormatError(access_token);
			}
			OAuth2Authentication oAuth2Authentication =null;
			try{
				 oAuth2Authentication = oauth20tokenServices.loadAuthentication(access_token);
				 
				 principal=oAuth2Authentication.getPrincipal().toString();
				 
				 String client_id= oAuth2Authentication.getOAuth2Request().getClientId();
				 UserInfo userInfo=queryUserInfo(principal);
				 Applications app=applicationsService.get(client_id);
				 
				 String userJson="";
				 
				 AbstractAuthorizeAdapter adapter;
				 if(BOOLEAN.isTrue(app.getIsAdapter())){
					adapter =(AbstractAuthorizeAdapter)Instance.newInstance(app.getAdapter());
				 }else{
					adapter =(AbstractAuthorizeAdapter)defaultOAuthUserInfoAdapter;
				 }

				 String jsonData=adapter.generateInfo(userInfo, null);
				 userJson=adapter.sign(jsonData, app);
				 
				 return userJson;
				 
			}catch(OAuth2Exception e){
				HashMap<String,Object>authzException=new HashMap<String,Object>();
				authzException.put(OAuth2Exception.ERROR, e.getOAuth2ErrorCode());
				authzException.put(OAuth2Exception.DESCRIPTION,e.getMessage());
				return JsonUtils.object2Json(authzException);
			}
	}
	
	
	@RequestMapping(value="/connect/v10/userinfo",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String apiConnect10aUserInfo(
			@RequestHeader(value = "Authorization", required = true) String access_token) {
		String principal="";
		if (!StringGenerator.uuidMatches(access_token)) {
			return accessTokenFormatError(access_token);
		}
		OAuth2Authentication oAuth2Authentication =null;
		try{
			 oAuth2Authentication = oauth20tokenServices.loadAuthentication(access_token);
			 
			 principal=oAuth2Authentication.getPrincipal().toString();
			 
			 Set<String >scopes=oAuth2Authentication.getOAuth2Request().getScope();
			 ClientDetails clientDetails = clientDetailsService.loadClientByClientId(oAuth2Authentication.getOAuth2Request().getClientId());
			 
			 UserInfo userInfo=queryUserInfo(principal);
			 String userJson="";
			 HashMap<String, Object> claimsFields = new HashMap<String, Object>();
			 
		 	claimsFields.put("sub", userInfo.getId());
		 	
		 	if(scopes.contains("profile")){
				claimsFields.put("name", userInfo.getUsername());
				claimsFields.put("preferred_username", userInfo.getDisplayName());
				claimsFields.put("given_name", userInfo.getGivenName());
				claimsFields.put("family_name", userInfo.getFamilyName());
				claimsFields.put("middle_name", userInfo.getMiddleName());
				claimsFields.put("nickname", userInfo.getNickName());
				claimsFields.put("profile", "profile");
				claimsFields.put("picture", "picture");
				claimsFields.put("website", userInfo.getWebSite());
				
				String gender;
				 switch(userInfo.getGender()){
				 	case UserInfo.GENDER.MALE  :
				 		gender="male";break;
				 	case UserInfo.GENDER.FEMALE  :
				 		gender="female";break;
				 	default:
				 		gender="unknown";
				 }
				claimsFields.put("gender", gender);
				claimsFields.put("zoneinfo", userInfo.getTimeZone());
				claimsFields.put("locale", userInfo.getLocale());
				claimsFields.put("updated_time", userInfo.getModifiedDate());
				claimsFields.put("birthdate", userInfo.getBirthDate());
		 	}
		 	
		 	if(scopes.contains("email")){
		 		claimsFields.put("email", userInfo.getWorkEmail());
		 		claimsFields.put("email_verified", false);
		 	}
		 	
			if(scopes.contains("phone")){
				claimsFields.put("phone_number", userInfo.getWorkPhoneNumber());
				claimsFields.put("phone_number_verified", false);
			}
			
			if(scopes.contains("address")){
				HashMap<String, String> addressFields = new HashMap<String, String>();
				addressFields.put("country", userInfo.getWorkCountry());
				addressFields.put("region", userInfo.getWorkRegion());
				addressFields.put("locality", userInfo.getWorkLocality());
				addressFields.put("street_address", userInfo.getWorkStreetAddress());
				addressFields.put("formatted", userInfo.getWorkAddressFormatted());
				addressFields.put("postal_code", userInfo.getWorkPostalCode());
				
				claimsFields.put("address", addressFields);
			}
			
			JWTClaimsSet userInfoJWTClaims = new JWTClaimsSet.Builder()
					.jwtID(UUID.randomUUID().toString())// set a random NONCE in the middle of it
					.audience(Arrays.asList(clientDetails.getClientId()))
					.issueTime(new Date())
					.expirationTime(new Date(new Date().getTime()+clientDetails.getAccessTokenValiditySeconds()*1000))
					.claim(claimsFields)
					.build();
	
			
			JWT userInfoJWT=null;
			JWSAlgorithm signingAlg = jwtSignerValidationService.getDefaultSigningAlgorithm();
			if (clientDetails.getUserInfoEncryptedAlgorithm() != null && !clientDetails.getUserInfoEncryptedAlgorithm().equals("none")
					&& clientDetails.getUserInfoEncryptionMethod() != null && !clientDetails.getUserInfoEncryptionMethod().equals("none")
					&&clientDetails.getJwksUri()!=null&&clientDetails.getJwksUri().length()>4
					) {
				JwtEncryptionAndDecryptionService recipientJwtEnDecryptionService =
						recipientJwtEnDecryptionServiceBuilder.serviceBuilder(clientDetails.getJwksUri());
				
				if (recipientJwtEnDecryptionService != null) {
					JWEAlgorithm jweAlgorithm=new JWEAlgorithm(clientDetails.getUserInfoEncryptedAlgorithm());
					EncryptionMethod encryptionMethod=new EncryptionMethod(clientDetails.getUserInfoEncryptionMethod());
					EncryptedJWT encryptedJWT = new EncryptedJWT(new JWEHeader(jweAlgorithm, encryptionMethod), userInfoJWTClaims);
					recipientJwtEnDecryptionService.encryptJwt(encryptedJWT);
					userJson=encryptedJWT.serialize();
				}else{
					_logger.error("Couldn't find encrypter for client: " + clientDetails.getClientId());
					HashMap<String,Object>authzException=new HashMap<String,Object>();
					authzException.put(OAuth2Exception.ERROR, "error");
					authzException.put(OAuth2Exception.DESCRIPTION,"Couldn't find encrypter for client: " + clientDetails.getClientId());
					return JsonUtils.gson2Json(authzException);
				}	
			} else {
				if (clientDetails.getUserInfoSigningAlgorithm()==null||clientDetails.getUserInfoSigningAlgorithm().equals("none")) {
					// unsigned ID token
					//userInfoJWT = new PlainJWT(userInfoJWTClaims);
					userJson=JsonUtils.gson2Json(claimsFields);
				} else {
					// signed ID token
					if (signingAlg.equals(JWSAlgorithm.HS256)
							|| signingAlg.equals(JWSAlgorithm.HS384)
							|| signingAlg.equals(JWSAlgorithm.HS512)) {
						// sign it with the client's secret
						String client_secret=ReciprocalUtils.decoder(clientDetails.getClientSecret());
						
						JwtSigningAndValidationService symmetricJwtSignerService =symmetricJwtSignerServiceBuilder.serviceBuilder(client_secret);
						if(symmetricJwtSignerService!=null){
							userInfoJWTClaims = new JWTClaimsSet.Builder(userInfoJWTClaims).claim("kid", "SYMMETRIC-KEY").build();
							userInfoJWT = new SignedJWT(new JWSHeader(signingAlg), userInfoJWTClaims);
							symmetricJwtSignerService.signJwt((SignedJWT) userInfoJWT);
						}else{
							_logger.error("Couldn't create symmetric validator for client " + clientDetails.getClientId() + " without a client secret");
						}
					} else {
						userInfoJWTClaims = new JWTClaimsSet.Builder(userInfoJWTClaims).claim("kid", jwtSignerValidationService.getDefaultSignerKeyId()).build();
						userInfoJWT = new SignedJWT(new JWSHeader(signingAlg), userInfoJWTClaims);
						// sign it with the server's key
						jwtSignerValidationService.signJwt((SignedJWT) userInfoJWT);
					}
					userJson=userInfoJWT.serialize();
				}
			}
			 
			 return userJson;
			 
		}catch(OAuth2Exception e){
			HashMap<String,Object>authzException=new HashMap<String,Object>();
			authzException.put(OAuth2Exception.ERROR, e.getOAuth2ErrorCode());
			authzException.put(OAuth2Exception.DESCRIPTION,e.getMessage());
			return JsonUtils.object2Json(authzException);
		}
	}


	public String accessTokenFormatError(String access_token){
		HashMap<String,Object>atfe=new HashMap<String,Object>();
		atfe.put(OAuth2Exception.ERROR, "token Format Invalid");
		atfe.put(OAuth2Exception.DESCRIPTION, "access Token Format Invalid , access_token : "+access_token);
		
		return JsonUtils.object2Json(atfe);
	}

	
	public  UserInfo queryUserInfo(String uid){
		_logger.debug("uid : "+uid);
		UserInfo queryUserInfo=new UserInfo();
		queryUserInfo.setUsername(uid);
		UserInfo userInfo = (UserInfo) userInfoService.load(queryUserInfo);
		return userInfo;
	}


	public void setOauth20tokenServices(DefaultTokenServices oauth20tokenServices) {
		this.oauth20tokenServices = oauth20tokenServices;
	}
	


	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

//
//
//	public void setJwtSignerValidationService(
//			JwtSigningAndValidationService jwtSignerValidationService) {
//		this.jwtSignerValidationService = jwtSignerValidationService;
//	}
//
//	public void setJwtEnDecryptionService(
//			JwtEncryptionAndDecryptionService jwtEnDecryptionService) {
//		this.jwtEnDecryptionService = jwtEnDecryptionService;
//	}
}
