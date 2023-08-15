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
 

package org.dromara.maxkey.authz.oauth2.provider.code;

import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.dromara.maxkey.persistence.redis.RedisConnection;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;

/**
 * Implementation of authorization code services that stores the codes and authentication in Redis.
 * 
 * @author Crystal.Sea
 */
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

	RedisConnectionFactory connectionFactory;
	
	public static String PREFIX="MXK_OAUTH_V20_CODE_";
	
	protected int codeValiditySeconds = 60 * 10; //default 10 minutes.
	 
	/**
	 * @param connectionFactory
	 */
	public RedisAuthorizationCodeServices(RedisConnectionFactory connectionFactory) {
		super();
		this.connectionFactory = connectionFactory;
	}

	public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	@Override
	protected void store(String code, OAuth2Authentication authentication) {
		RedisConnection  conn=connectionFactory.getConnection();
		conn.setexObject(PREFIX+code,codeValiditySeconds, authentication);
		conn.close();
	}

	@Override
	public OAuth2Authentication remove(String code) {
		RedisConnection  conn=connectionFactory.getConnection();
		OAuth2Authentication auth = conn.getObject(PREFIX+code);
		conn.delete(PREFIX+code);
        conn.close();
		return auth;
	}

}
