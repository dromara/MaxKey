package org.maxkey.authz.token.endpoint.adapter;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.joda.time.DateTime;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.config.oidc.OIDCProviderMetadata;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.crypto.jwt.signer.service.JwtSigningAndValidationService;
import org.maxkey.crypto.jwt.signer.service.impl.SymmetricSigningAndValidationServiceBuilder;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.Applications;
import org.maxkey.domain.apps.TokenBasedDetails;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import com.nimbusds.jwt.SignedJWT;

public class TokenBasedJWTHS256Adapter extends AbstractAuthorizeAdapter {
	final static Logger _logger = LoggerFactory.getLogger(TokenBasedJWTHS256Adapter.class);
	private SymmetricSigningAndValidationServiceBuilder symmetricJwtSignerServiceBuilder=new SymmetricSigningAndValidationServiceBuilder();

	@Override
	public String generateInfo(UserInfo userInfo,Object app) {
		TokenBasedDetails details=(TokenBasedDetails)app;
		
		OIDCProviderMetadata providerMetadata= (OIDCProviderMetadata)WebContext.getBean("oidcProviderMetadata");
	
		DateTime currentDateTime=DateTime.now();
		
		Date expirationTime=currentDateTime.plusMinutes(Integer.parseInt(details.getExpires())).toDate();
		_logger.debug("expiration Time : "+expirationTime);
		
		JWTClaimsSet jwtClaims =new  JWTClaimsSet.Builder()
				.issuer(providerMetadata.getIssuer())
				.subject(userInfo.getUsername())
				.audience(Arrays.asList(details.getId()))
				.jwtID(UUID.randomUUID().toString())
				.issueTime(currentDateTime.toDate())
				.expirationTime(expirationTime)
				.claim("email", userInfo.getWorkEmail())
				.claim("name", userInfo.getUsername())
				.claim("user_id", userInfo.getId())
				.claim("external_id", userInfo.getId())
				.claim("locale", userInfo.getLocale())
				.claim("kid", "SYMMETRIC-KEY")
				.build();
		
		_logger.debug("jwt Claims : "+jwtClaims);
		
		JWT jwtToken = new PlainJWT(jwtClaims);
		
		String sharedSecret=ReciprocalUtils.decoder(details.getAlgorithmKey());
		
		_logger.debug("jwt sharedSecret : "+sharedSecret);
		
		JwtSigningAndValidationService symmetricJwtSignerService =symmetricJwtSignerServiceBuilder.serviceBuilder(sharedSecret);
		if(symmetricJwtSignerService!=null){
			jwtToken = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtClaims);
			symmetricJwtSignerService.signJwt((SignedJWT) jwtToken);
		}
		
		String tokenString=jwtToken.serialize();
		_logger.debug("jwt Token : "+tokenString);
		
		return tokenString;
	}

	@Override
	public String encrypt(String data, String algorithmKey, String algorithm) {
		return data;
	}

	@Override
	public String sign(String data, Applications app) {
		
		return data;
	}

	@Override
	public ModelAndView authorize(UserInfo userInfo, Object app, String data,ModelAndView modelAndView) {
		modelAndView.setViewName("authorize/tokenbased_jwt_sso_submint");
		TokenBasedDetails details=(TokenBasedDetails)app;
		modelAndView.addObject("action", details.getRedirectUri());
		_logger.debug("jwt Token data : "+data);
		
		modelAndView.addObject("token",data);
		
		//return_to
		
		return modelAndView;
	}

}
