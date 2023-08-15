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
 *******************************************************************************/
package org.dromara.maxkey.crypto.jwt.signer.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.dromara.maxkey.crypto.jwt.signer.service.JwtSigningAndValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.nimbusds.jose.jwk.JWK;

/**
 * Creates and caches symmetrical validators for clients based on client secrets.
 * 
 * @author jricher
 *
 */
@Service
public class SymmetricCacheService {

	private static Logger logger = LoggerFactory.getLogger(SymmetricCacheService.class);

	private LoadingCache<String, JwtSigningAndValidationService> validators;


	public SymmetricCacheService() {
		validators = CacheBuilder.newBuilder()
				.expireAfterAccess(24, TimeUnit.HOURS)
				.maximumSize(100)
				.build(new SymmetricValidatorBuilder());
	}


	/**
	 * Create a symmetric signing and validation service for the given client
	 * 
	 * @param client
	 * @return
	 */
	public JwtSigningAndValidationService getSymmetricValidtor(String clientSecret) {

		if (clientSecret == null) {
			logger.error("Couldn't create symmetric validator for null client");
			return null;
		}

		if (Strings.isNullOrEmpty(clientSecret)) {
			logger.error("Couldn't create symmetric validator for client  without a client secret");
			return null;
		}

		try {
			return validators.get(clientSecret);
		} catch (UncheckedExecutionException ue) {
			logger.error("Problem loading client validator", ue);
			return null;
		} catch (ExecutionException e) {
			logger.error("Problem loading client validator", e);
			return null;
		}

	}

	public class SymmetricValidatorBuilder extends CacheLoader<String, JwtSigningAndValidationService> {
		@Override
		public JwtSigningAndValidationService load(String key) throws Exception {
			try {

				String id = "SYMMETRIC-KEY";

				JWK jwk =null;
				//JWK jwk = new OctetSequenceKey(Base64URL.encode(key), KeyUse.SIGNATURE, null, null, id, null, null, null);
				Map<String, JWK> keys = ImmutableMap.of(id, jwk);
				JwtSigningAndValidationService service = new DefaultJwtSigningAndValidationService(keys);

				return service;

			} catch (NoSuchAlgorithmException e) {
				logger.error("Couldn't create symmetric validator for client", e);
			} catch (InvalidKeySpecException e) {
				logger.error("Couldn't create symmetric validator for client", e);
			}

			throw new IllegalArgumentException("Couldn't create symmetric validator for client");
		}

	}

}
