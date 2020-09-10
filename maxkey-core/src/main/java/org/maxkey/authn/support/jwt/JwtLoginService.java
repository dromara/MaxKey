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
 

package org.maxkey.authn.support.jwt;

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
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.configuration.oidc.OIDCProviderMetadataDetails;
import org.maxkey.constants.ConstantsLoginType;
import org.maxkey.crypto.jwt.signer.service.impl.DefaultJwtSigningAndValidationService;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JwtLoginService {
    private static final Logger _logger = LoggerFactory.getLogger(JwtLoginService.class);


    OIDCProviderMetadataDetails jwtProviderMetadata;

    DefaultJwtSigningAndValidationService jwtSignerValidationService;
    
    AbstractAuthenticationProvider authenticationProvider ;

    
    public JwtLoginService(AbstractAuthenticationProvider authenticationProvider,
            OIDCProviderMetadataDetails jwtProviderMetadata,
            DefaultJwtSigningAndValidationService jwtSignerValidationService
            ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtProviderMetadata = jwtProviderMetadata;
        this.jwtSignerValidationService = jwtSignerValidationService;
        
    }
    public boolean login(String jwt, HttpServletResponse response) {
        _logger.debug("jwt : " + jwt);

        String username = null;
        SignedJWT signedJWT = null;

        boolean loginResult = false;
        JWTClaimsSet jwtClaimsSet = null;
        try {

            RSASSAVerifier rsaSSAVerifier = new RSASSAVerifier(((RSAKey) jwtSignerValidationService.getAllPublicKeys()
                    .get(jwtSignerValidationService.getDefaultSignerKeyId())).toRSAPublicKey());

            signedJWT = SignedJWT.parse(jwt);
            if (signedJWT.verify(rsaSSAVerifier)) {
                loginResult = true;
            } else {
                _logger.debug("verify false ");
                return false;
            }
            jwtClaimsSet = signedJWT.getJWTClaimsSet();

            _logger.debug("" + signedJWT.getPayload());
            _logger.debug("jwtClaimsSet Issuer " + jwtClaimsSet.getIssuer());
            _logger.debug("Metadata Issuer " + jwtProviderMetadata.getIssuer());

            if (loginResult && jwtClaimsSet.getIssuer().equals(jwtProviderMetadata.getIssuer())) {
                loginResult = true;
                _logger.debug("Issuer equals ");
            } else {
                _logger.debug("Issuer not equals ");
                return false;
            }

            _logger.debug("username " + jwtClaimsSet.getSubject());

            if (loginResult && jwtClaimsSet.getSubject() != null) {
                username = jwtClaimsSet.getSubject();
            } else {
                return false;
            }

            DateTime now = new DateTime();

            if (loginResult && now.isBefore(jwtClaimsSet.getExpirationTime().getTime())) {
                authenticationProvider.trustAuthentication(username, ConstantsLoginType.JWT, "", "", "success");
                return true;
            }
        } catch (java.text.ParseException e) {
            // Invalid signed JWT encoding
            _logger.error("Invalid signed JWT encoding ");
        } catch (JOSEException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            _logger.error("JOSEException ");
        }

        return false;
    }

    public String buildLoginJwt() {
        _logger.debug("buildLoginJwt .");

        DateTime currentDateTime = DateTime.now();
        Date expirationTime = currentDateTime.plusMinutes(5).toDate();
        _logger.debug("expiration Time : " + expirationTime);
        JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder().subject(WebContext.getSession().getId())
                .expirationTime(expirationTime).issuer(jwtProviderMetadata.getIssuer())
                .issueTime(currentDateTime.toDate()).jwtID(UUID.randomUUID().toString()).build();

        JWT jwtToken = new PlainJWT(jwtClaims);

        _logger.info("jwt Claims : " + jwtClaims.toString());

        JWSAlgorithm signingAlg = jwtSignerValidationService.getDefaultSigningAlgorithm();

        jwtToken = new SignedJWT(new JWSHeader(signingAlg), jwtClaims);
        // sign it with the server's key
        jwtSignerValidationService.signJwt((SignedJWT) jwtToken);

        String tokenString = jwtToken.serialize();
        _logger.debug("jwt Token : " + tokenString);
        return tokenString;
    }

    public boolean jwtTokenValidation(String jwt) {
        SignedJWT signedJWT = null;

        boolean loginResult = false;
        JWTClaimsSet jwtClaimsSet = null;
        try {

            RSASSAVerifier rsaSSAVerifier = new RSASSAVerifier(((RSAKey) jwtSignerValidationService.getAllPublicKeys()
                    .get(jwtSignerValidationService.getDefaultSignerKeyId())).toRSAPublicKey());

            signedJWT = SignedJWT.parse(jwt);
            if (signedJWT.verify(rsaSSAVerifier)) {
                loginResult = true;
            } else {
                _logger.debug("verify false ");
            }
            jwtClaimsSet = signedJWT.getJWTClaimsSet();

            _logger.debug("" + signedJWT.getPayload());

            _logger.debug("username " + jwtClaimsSet.getSubject());

            _logger.debug("jwtClaimsSet Issuer " + jwtClaimsSet.getIssuer());
            _logger.debug("Metadata Issuer " + jwtProviderMetadata.getIssuer());

            if (loginResult && jwtClaimsSet.getIssuer().equals(jwtProviderMetadata.getIssuer())) {
                loginResult = true;
                _logger.debug("Issuer equals ");
            } else {
                _logger.debug("Issuer not equals ");
                return false;
            }

            DateTime now = new DateTime();

            if (loginResult && now.isBefore(jwtClaimsSet.getExpirationTime().getTime())) {
                _logger.debug("ExpirationTime  Validation " + now.isBefore(jwtClaimsSet.getExpirationTime().getTime()));
                loginResult = true;
            } else {
                return false;
            }
        } catch (java.text.ParseException e) {
            // Invalid signed JWT encoding
            _logger.debug("Invalid signed JWT encoding ");
        } catch (JOSEException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            _logger.debug("JOSEException ");
        }
        return loginResult;
    }


    public void setJwtProviderMetadata(OIDCProviderMetadataDetails jwtProviderMetadata) {
        this.jwtProviderMetadata = jwtProviderMetadata;
    }

    public void setJwtSignerValidationService(DefaultJwtSigningAndValidationService jwtSignerValidationService) {
        this.jwtSignerValidationService = jwtSignerValidationService;
    }

    public void setAuthenticationProvider(AbstractAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

}
