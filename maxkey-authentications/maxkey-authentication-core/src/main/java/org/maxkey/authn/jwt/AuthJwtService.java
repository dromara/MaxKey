package org.maxkey.authn.jwt;

import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;
import org.maxkey.authn.SignPrincipal;
import org.maxkey.crypto.jwt.HMAC512Service;
import org.maxkey.entity.UserInfo;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class AuthJwtService {
	private static final  Logger _logger = LoggerFactory.getLogger(AuthJwtService.class);
	
	HMAC512Service hmac512Service;
	
	/**
	 * JWT with Authentication
	 * @param authentication
	 * @return
	 */
	public String genJwt(Authentication authentication,String issuer,int expires) {
		SignPrincipal principal = ((SignPrincipal)authentication.getPrincipal());
		UserInfo userInfo = principal.getUserInfo();
		DateTime currentDateTime = DateTime.now();
		String subject = principal.getUsername();
		Date expirationTime = currentDateTime.plusSeconds(expires).toDate();
		_logger.trace("jwt subject : {} , expiration Time : {}" , subject,expirationTime);
		
		 JWTClaimsSet jwtClaims =new  JWTClaimsSet.Builder()
				.issuer(issuer)
				.subject(subject)
				.jwtID(principal.getSession().getId())
				.issueTime(currentDateTime.toDate())
				.expirationTime(expirationTime)
				.claim("locale", userInfo.getLocale())
				.claim("kid", HMAC512Service.MXK_AUTH_JWK)
				.claim("institution", userInfo.getInstId())
				.build();
		
		return signedJWT(jwtClaims);
	}
	
	/**
	 * JWT with subject
	 * @param subject subject
	 * @return
	 */
	public String genJwt(String subject,String issuer,int expires) {
		DateTime currentDateTime = DateTime.now();
		Date expirationTime = currentDateTime.plusSeconds(expires).toDate();
		_logger.trace("jwt subject : {} , expiration Time : {}" , subject,expirationTime);
		
		 JWTClaimsSet jwtClaims =new  JWTClaimsSet.Builder()
				.issuer(issuer)
				.subject(subject)
				.jwtID(WebContext.genId())
				.issueTime(currentDateTime.toDate())
				.expirationTime(expirationTime)
				.build();
		 
		return signedJWT(jwtClaims);
	}
	
	/**
	 * Random JWT
	 * @return
	 */
	public String genRandomJwt(int expires) {
		Date expirationTime = DateTime.now().plusSeconds(expires).toDate();
		_logger.trace("expiration Time : {}" , expirationTime);
		
		 JWTClaimsSet jwtClaims =new  JWTClaimsSet.Builder()
				.jwtID(WebContext.genId())
				.expirationTime(expirationTime)
				.build();
		
		return signedJWT(jwtClaims);
	}
	
	public String signedJWT(JWTClaimsSet jwtClaims) {
		_logger.trace("jwt Claims : {}" , jwtClaims);
		SignedJWT  jwtToken = new SignedJWT(
				new JWSHeader(JWSAlgorithm.HS512), 
				jwtClaims);
		return hmac512Service.sign(jwtToken.getPayload());
	}
	
	/**
	 * Verify with HMAC512 and check ExpirationTime
	 * 
	 * @param authToken
	 * @return true or false
	 */
	public boolean validateJwtToken(String authToken) {
		try {
			if(StringUtils.isNotBlank(authToken)) {
				JWTClaimsSet claims = resolve(authToken);
				boolean isExpiration = claims.getExpirationTime().after(DateTime.now().toDate());
				boolean isVerify = hmac512Service.verify(authToken);
				_logger.trace("JWT Verify {} , now {} , ExpirationTime {} , isExpiration : {}" , 
								isVerify,DateTime.now().toDate(),claims.getExpirationTime(),isExpiration);
				return isVerify && isExpiration;
			}
		} catch (ParseException e) {
			_logger.error("authToken {}",authToken);
			_logger.error("ParseException ",e);
		}
		return false;
	}
	
	public  JWTClaimsSet resolve(String authToken) throws ParseException {
		SignedJWT signedJWT = SignedJWT.parse(authToken);
		_logger.trace("jwt Claims : {}" , signedJWT.getJWTClaimsSet());
		return signedJWT.getJWTClaimsSet();
	}
	
	public String resolveJWTID(String authToken) throws ParseException {
		JWTClaimsSet claims = resolve(authToken); 
		return claims.getJWTID();
	}
}
