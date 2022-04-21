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
 

package org.maxkey.persistence;

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
    public  void put(String ticket , String name, Object value){
    	momentaryStore.put(getKey(ticket,name), value);
	}

	@Override
	public Object remove(String ticket , String name) {
		Object value = momentaryStore.getIfPresent(getKey(ticket,name));	
		momentaryStore.invalidate(getKey(ticket,name));
		return value;
	}

    @Override
    public Object get(String ticket , String name) {
    	return momentaryStore.getIfPresent(getKey(ticket,name));
    }


    private String getKey(String ticket , String name) {
    	return ticket +"_"+ name;
    }
}
