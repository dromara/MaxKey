package org.dromara.maxkey.crypto.jose.keystore;
/*******************************************************************************
 * Copyright 2014 The MITRE Corporation
 *   and the MIT Kerberos and Internet Trust Consortium
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import com.google.common.io.CharStreams;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.crypto.RSAUtils;
import org.dromara.maxkey.pretty.PrettyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * .
 * @author jricher
 *
 */
public class JWKSetKeyStore {
    private static final  Logger _logger = LoggerFactory.getLogger(JWKSetKeyStore.class);
    private JWKSet jwkSet;

    private Resource location;

    public JWKSetKeyStore() {

    }

    public JWKSetKeyStore(JWKSet jwkSet) {
        this.jwkSet = jwkSet;
        initializeJwkSet();
    }
    
    public JWKSetKeyStore(String  jwkSetJsonString) {
        try {
			this.jwkSet = JWKSet.parse(jwkSetJsonString);
		} catch (ParseException e) {
			_logger.error("ParseException", e);
		}
        initializeJwkSet();
    }
    

    private void initializeJwkSet() {

        if (jwkSet == null) {
            if (location != null) {

                if (location.exists() && location.isReadable()) {

                    try {
                        _logger.debug("JWK location " + location.getURL());
                        // read in the file from disk
                        String s = CharStreams
                                .toString(new InputStreamReader(location.getInputStream(), StandardCharsets.UTF_8));

                        // parse it into a jwkSet object
                        jwkSet = JWKSet.parse(s);
                    } catch (IOException e) {
                        throw new IllegalArgumentException("Key Set resource could not be read: " + location);
                    } catch (ParseException e) {
                        throw new IllegalArgumentException("Key Set resource could not be parsed: " + location);
                    }

                } else {
                    throw new IllegalArgumentException("Key Set resource could not be read: " + location);
                }

            } else {
                throw new IllegalArgumentException(
                        "Key store must be initialized with at least one of a jwkSet or a location.");
            }
        }
    }

    /**
     * @return the jwkSet
     */
    public JWKSet getJwkSet() {
        return jwkSet;
    }

    /**
     * @param jwkSet the jwkSet to set
     */
    public void setJwkSet(JWKSet jwkSet) {
        this.jwkSet = jwkSet;
        initializeJwkSet();
    }

    /**
     * @return the location
     */
    public Resource getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Resource location) {
        this.location = location;
        
        initializeJwkSet();
    }

    /**
     * Get the list of keys in this keystore. This is a passthrough to the
     * underlying JWK Set
     */
    public List<JWK> getKeys() {
        if (jwkSet == null) {
            initializeJwkSet();
        }
        return jwkSet.getKeys();
    }
    
    public String toString(String mediaType){
    	StringBuffer metaDataString = new StringBuffer("");
    	//RSA Only
		if(jwkSet.getKeys().get(0).getKeyType().getValue().equalsIgnoreCase("RSA")) {
			
			if(StringUtils.isNotBlank(mediaType) && mediaType.equalsIgnoreCase("XML")) {
			
				metaDataString.append("<RSAKeyValue>").append("\n");
				for(JWK jwk : jwkSet.getKeys()) {
					RSAKey  rsaKey  = jwk.toRSAKey();
					PublicKey publicKey;
					try {
						publicKey = rsaKey.toPublicKey();
						metaDataString.append("<Modulus>").append("\n");
						metaDataString.append(RSAUtils.getPublicKeyPEM(publicKey.getEncoded()));
						metaDataString.append("</Modulus>").append("\n");
						//keyID
						metaDataString.append("<Algorithm>");
						metaDataString.append(rsaKey.getAlgorithm());
						metaDataString.append("</Algorithm>").append("\n");
						
						metaDataString.append("<KeyID>");
						metaDataString.append(rsaKey.getKeyID());
						metaDataString.append("</KeyID>").append("\n");
						
						metaDataString.append("<KeyType>");
						metaDataString.append(rsaKey.getKeyType());
						metaDataString.append("</KeyType>").append("\n");
						
						metaDataString.append("<Format>");
						metaDataString.append(publicKey.getFormat());
						metaDataString.append("</Format>");
						
						metaDataString.append("<PublicExponent>");
						metaDataString.append(rsaKey.getPublicExponent());
						metaDataString.append("</PublicExponent>").append("\n");
					} catch (JOSEException e) {
						_logger.error("JOSEException ", mediaType);
					}
				}
				metaDataString.append("</RSAKeyValue>");
			
			}else {
				//RSA Only
				metaDataString.append(PrettyFactory.getJsonPretty().format(
						jwkSet.toPublicJWKSet().toString()));
			}
		}else {
			metaDataString.append("RSA Only");
		}
		return metaDataString.toString();
	}
}
