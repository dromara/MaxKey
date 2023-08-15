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
 

package org.dromara.maxkey.persistence.cache;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;


public class InMemoryMomentaryService implements MomentaryService{
    private static final Logger _logger = LoggerFactory.getLogger(InMemoryMomentaryService.class);

	protected  static  Cache<String, Object> momentaryStore = 
        	        Caffeine.newBuilder()
        	            .expireAfterWrite(5, TimeUnit.MINUTES)
        	            .maximumSize(200000)
        	            .build();
	
	public InMemoryMomentaryService() {
        super();
    }

    @Override
    public  void put(String sessionId , String name, Object value){
    	 _logger.trace("key {}, value {}",getSessionKey(sessionId , name),value);
    	momentaryStore.put(getSessionKey(sessionId,name), value);
	}

	@Override
	public Object remove(String sessionId , String name) {
		Object value = momentaryStore.getIfPresent(getSessionKey(sessionId,name));	
		momentaryStore.invalidate(getSessionKey(sessionId,name));
		 _logger.trace("key {}, value {}",getSessionKey(sessionId , name),value);
		return value;
	}

    @Override
    public Object get(String sessionId , String name) {
    	 _logger.trace("key {}",getSessionKey(sessionId , name));
    	return momentaryStore.getIfPresent(getSessionKey(sessionId,name));
    }


    private String getSessionKey(String sessionId , String name) {
    	return sessionId + "_" + name;
    }
}
