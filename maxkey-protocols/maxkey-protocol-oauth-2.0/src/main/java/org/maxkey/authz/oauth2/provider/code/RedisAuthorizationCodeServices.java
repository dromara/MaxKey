package org.maxkey.authz.oauth2.provider.code;

import org.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.maxkey.persistence.redis.RedisConnection;
import org.maxkey.persistence.redis.RedisConnectionFactory;

/**
 * Implementation of authorization code services that stores the codes and authentication in Redis.
 * 
 * @author Crystal.Sea
 */
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

	RedisConnectionFactory connectionFactory;
	
	public static String PREFIX="MAXKEY_REDIS_OAUTH_V20_CODE_";
	
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
		return auth;
	}

}
