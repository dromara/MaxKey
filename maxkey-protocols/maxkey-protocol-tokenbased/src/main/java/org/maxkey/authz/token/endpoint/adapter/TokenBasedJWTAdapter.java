/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.maxkey.authz.token.endpoint.adapter;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.joda.time.DateTime;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.configuration.oidc.OIDCProviderMetadata;
import org.maxkey.crypto.jwt.signer.service.JwtSigningAndValidationService;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.AppsTokenBasedDetails;
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

public class TokenBasedJWTAdapter extends AbstractAuthorizeAdapter {
	final static Logger _logger = LoggerFactory.getLogger(TokenBasedJWTAdapter.class);
	@Override
	public String generateInfo(UserInfo userInfo,Object app) {
		AppsTokenBasedDetails details=(AppsTokenBasedDetails)app;
	
		
		JwtSigningAndValidationService jwtSignerService= (JwtSigningAndValidationService)WebContext.getBean("jwtSignerValidationService");
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
				.claim("kid", jwtSignerService.getDefaultSignerKeyId())
				.build();
		
		_logger.debug("jwt Claims : "+jwtClaims);
		
		JWT jwtToken = new PlainJWT(jwtClaims);
		
		JWSAlgorithm signingAlg = jwtSignerService.getDefaultSigningAlgorithm();
		
		//get PublicKey
		/*Map<String, JWK>  jwkMap=jwtSignerService.getAllPublicKeys();
		
		JWK jwk=jwkMap.get("connsec_rsa1");
		
		_logger.debug("isPrivate "+jwk.isPrivate());*/
		
		_logger.debug(" signingAlg "+signingAlg);
		
		jwtToken = new SignedJWT(new JWSHeader(signingAlg), jwtClaims);
		// sign it with the server's key
		jwtSignerService.signJwt((SignedJWT) jwtToken);
		
		String tokenString=jwtToken.serialize();
		_logger.debug("jwt Token : "+tokenString);
		
		return tokenString;
	}

	@Override
	public String encrypt(String data, String algorithmKey, String algorithm) {
		return data;
	}

	@Override
	public String sign(String data, Apps app) {
		
		return data;
	}

	@Override
	public ModelAndView authorize(UserInfo userInfo, Object app, String data,ModelAndView modelAndView) {
		modelAndView.setViewName("authorize/tokenbased_jwt_sso_submint");
		AppsTokenBasedDetails details=(AppsTokenBasedDetails)app;
		modelAndView.addObject("action", details.getRedirectUri());
		_logger.debug("jwt Token data : "+data);
		
		modelAndView.addObject("token",data);
		
		//return_to
		
		return modelAndView;
	}

}
