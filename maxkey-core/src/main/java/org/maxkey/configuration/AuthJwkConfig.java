package org.maxkey.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class AuthJwkConfig {

	@Value("${maxkey.auth.jwt.issuer:https://sso.maxkey.top/}")
	String 	issuer;
	
	@Value("${maxkey.auth.jwt.expires:86400}")
	int 	expires;
	
	@Value("${maxkey.auth.jwt.secret}")
	String 	secret;

	public AuthJwkConfig() {
		super();
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	

	public int getExpires() {
		return expires;
	}

	public void setExpires(int expires) {
		this.expires = expires;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AuthJwkConfig [issuer=");
		builder.append(issuer);
		builder.append(", expires=");
		builder.append(expires);
		builder.append(", secret=");
		builder.append(secret);
		builder.append("]");
		return builder.toString();
	}
	
}
