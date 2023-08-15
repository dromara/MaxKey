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
/**
 * 
 */
package org.dromara.maxkey.crypto.jwt.signer.service.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.dromara.maxkey.crypto.jose.keystore.JWKSetKeyStore;
import org.dromara.maxkey.crypto.jwt.encryption.service.JwtEncryptionAndDecryptionService;
import org.dromara.maxkey.crypto.jwt.encryption.service.impl.DefaultJwtEncryptionAndDecryptionService;
import org.dromara.maxkey.crypto.jwt.signer.service.JwtSigningAndValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.nimbusds.jose.jwk.JWKSet;

/**
 * 
 * Creates a caching map of JOSE signers/validators and encrypters/decryptors
 * keyed on the JWK Set URI. Dynamically loads JWK Sets to create the services.
 * 
 * @author jricher
 * 
 */
@Service
public class JWKSetCacheService {

	private static Logger logger = LoggerFactory.getLogger(JWKSetCacheService.class);

	// map of jwk set uri -> signing/validation service built on the keys found in that jwk set
	private LoadingCache<String, JwtSigningAndValidationService> validators;

	// map of jwk set uri -> encryption/decryption service built on the keys found in that jwk set
	private LoadingCache<String, JwtEncryptionAndDecryptionService> encrypters;

	public JWKSetCacheService() {
		this.validators = CacheBuilder.newBuilder()
				.expireAfterWrite(1, TimeUnit.HOURS) // expires 1 hour after fetch
				.maximumSize(100)
				.build(new JWKSetVerifierFetcher());
		this.encrypters = CacheBuilder.newBuilder()
				.expireAfterWrite(1, TimeUnit.HOURS) // expires 1 hour after fetch
				.maximumSize(100)
				.build(new JWKSetEncryptorFetcher());
	}

	/**
	 * @param jwksUri
	 * @return
	 * @throws ExecutionException
	 * @see com.google.common.cache.Cache#get(java.lang.Object)
	 */
	public JwtSigningAndValidationService getValidator(String jwksUri) {
		try {
			return validators.get(jwksUri);
		} catch (UncheckedExecutionException ue) {
			logger.warn("Couldn't load JWK Set from " + jwksUri, ue);
			return null;
		} catch (ExecutionException e) {
			logger.warn("Couldn't load JWK Set from " + jwksUri, e);
			return null;
		}
	}

	public JwtEncryptionAndDecryptionService getEncrypter(String jwksUri) {
		try {
			return encrypters.get(jwksUri);
		} catch (UncheckedExecutionException ue) {
			logger.warn("Couldn't load JWK Set from " + jwksUri, ue);
			return null;
		} catch (ExecutionException e) {
			logger.warn("Couldn't load JWK Set from " + jwksUri, e);
			return null;
		}
	}

	/**
	 * @author jricher
	 *
	 */
	private class JWKSetVerifierFetcher extends CacheLoader<String, JwtSigningAndValidationService> {
		//private HttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build();
		//private HttpComponentsClientHttpRequestFactory httpFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		private HttpComponentsClientHttpRequestFactory httpFactory = new HttpComponentsClientHttpRequestFactory();
		private RestTemplate restTemplate = new RestTemplate(httpFactory);

		/**
		 * Load the JWK Set and build the appropriate signing service.
		 */
		@Override
		public JwtSigningAndValidationService load(String key) throws Exception {

			String jsonString = restTemplate.getForObject(key, String.class);
			JWKSet jwkSet = JWKSet.parse(jsonString);

			JWKSetKeyStore keyStore = new JWKSetKeyStore(jwkSet);

			JwtSigningAndValidationService service = new DefaultJwtSigningAndValidationService(keyStore);

			return service;

		}

	}

	/**
	 * @author jricher
	 *
	 */
	private class JWKSetEncryptorFetcher extends CacheLoader<String, JwtEncryptionAndDecryptionService> {
		//private HttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build();
		//private HttpComponentsClientHttpRequestFactory httpFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		private HttpComponentsClientHttpRequestFactory httpFactory = new HttpComponentsClientHttpRequestFactory();
		private RestTemplate restTemplate = new RestTemplate(httpFactory);
		/* (non-Javadoc)
		 * @see com.google.common.cache.CacheLoader#load(java.lang.Object)
		 */
		@Override
		public JwtEncryptionAndDecryptionService load(String key) throws Exception {
			String jsonString = restTemplate.getForObject(key, String.class);
			JWKSet jwkSet = JWKSet.parse(jsonString);

			JWKSetKeyStore keyStore = new JWKSetKeyStore(jwkSet);

			JwtEncryptionAndDecryptionService service = new DefaultJwtEncryptionAndDecryptionService(keyStore);

			return service;
		}
	}

}
