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
 

package org.dromara.maxkey.authn.support.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import com.nimbusds.jwt.SignedJWT;
import java.util.Date;
import java.util.UUID;

import org.dromara.maxkey.crypto.jwt.signer.service.impl.DefaultJwtSigningAndValidationService;
import org.dromara.maxkey.web.WebContext;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JwtLoginService {
    private static final Logger _logger = LoggerFactory.getLogger(JwtLoginService.class);

    String issuer;
    
    DefaultJwtSigningAndValidationService jwtSignerValidationService;
    
    public JwtLoginService(
            DefaultJwtSigningAndValidationService jwtSignerValidationService,
            String issuer
            ) {
        this.jwtSignerValidationService = jwtSignerValidationService;
        this.issuer = issuer;
    }

    public String buildLoginJwt() {
        _logger.debug("build Login JWT .");

        DateTime currentDateTime = DateTime.now();
        Date expirationTime = currentDateTime.plusMinutes(5).toDate();
        _logger.debug("Expiration Time : " + expirationTime);
        JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder().subject(WebContext.getSession().getId())
                .expirationTime(expirationTime).issuer(getIssuer())
                .issueTime(currentDateTime.toDate()).jwtID(UUID.randomUUID().toString()).build();

        JWT jwtToken = new PlainJWT(jwtClaims);

        _logger.info("JWT Claims : " + jwtClaims.toString());

        JWSAlgorithm signingAlg = jwtSignerValidationService.getDefaultSigningAlgorithm();

        jwtToken = new SignedJWT(new JWSHeader(signingAlg), jwtClaims);
        // sign it with the server's key
        jwtSignerValidationService.signJwt((SignedJWT) jwtToken);

        String tokenString = jwtToken.serialize();
        _logger.debug("JWT Token : " + tokenString);
        return tokenString;
    }

    public SignedJWT jwtTokenValidation(String jwt) {
        SignedJWT signedJWT = null;
        JWTClaimsSet jwtClaimsSet = null;
        try {
            RSASSAVerifier rsaSSAVerifier = 
                    new RSASSAVerifier(((RSAKey) jwtSignerValidationService.getAllPublicKeys()
                            .get(jwtSignerValidationService.getDefaultSignerKeyId())).toRSAPublicKey());

            signedJWT = SignedJWT.parse(jwt);
            
            if (signedJWT.verify(rsaSSAVerifier)) {
            	 jwtClaimsSet = signedJWT.getJWTClaimsSet();
            	 boolean isIssuerMatches = jwtClaimsSet.getIssuer().equals(getIssuer());
            	 boolean isExpiration = (new DateTime()).isBefore(
            	             jwtClaimsSet.getExpirationTime().getTime());
            	 
                 _logger.debug("Signed JWT {}" , signedJWT.getPayload());
                 _logger.debug("Subject is {}" , jwtClaimsSet.getSubject());
                 _logger.debug("ExpirationTime  Validation {}" ,isExpiration);
                 _logger.debug("JWT ClaimsSet Issuer {}, Metadata Issuer {}, Issuer is matches {}" ,
                                jwtClaimsSet.getIssuer(), getIssuer(), isIssuerMatches
                        );

                 if ( isIssuerMatches && isExpiration ) {
                    return signedJWT;
                 }
            }else { 
                _logger.debug("JWT Signer Verify false.");
            }
        } catch (java.text.ParseException e) {
            // Invalid signed JWT encoding
            _logger.error("Invalid signed JWT encoding ",e);
        } catch (JOSEException e) {
            _logger.error("JOSEException ",e);
        }
        return null;
    }

    public void setJwtSignerValidationService(DefaultJwtSigningAndValidationService jwtSignerValidationService) {
        this.jwtSignerValidationService = jwtSignerValidationService;
    }

	public DefaultJwtSigningAndValidationService getJwtSignerValidationService() {
		return jwtSignerValidationService;
	}

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
    
    

}
