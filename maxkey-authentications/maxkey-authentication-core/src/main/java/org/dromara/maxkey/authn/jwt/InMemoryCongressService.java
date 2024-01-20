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
 

package org.dromara.maxkey.authn.jwt;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;


public class InMemoryCongressService implements CongressService{
    private static final Logger logger = LoggerFactory.getLogger(InMemoryCongressService.class);

	protected  static  Cache<String, AuthJwt> congressStore = 
        	        Caffeine.newBuilder()
        	            .expireAfterWrite(3, TimeUnit.MINUTES)
        	            .maximumSize(200000)
        	            .build();
	
	public InMemoryCongressService() {
        super();
    }

    @Override
	public void store(String congress, AuthJwt authJwt) {
    	congressStore.put(congress, authJwt);
	}

	@Override
	public AuthJwt remove(String congress) {
		AuthJwt authJwt = congressStore.getIfPresent(congress);	
		congressStore.invalidate(congress);
		return authJwt;
	}

    @Override
    public AuthJwt get(String congress) {
    	return congressStore.getIfPresent(congress); 
    }

	@Override
	public AuthJwt consume(String congress) {
		AuthJwt authJwt = congressStore.getIfPresent(congress);	
		congressStore.invalidate(congress);
		return authJwt;
	}

}
