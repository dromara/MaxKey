package org.maxkey.util;

public class AuthorizationHeaderCredential {
	
	public static class Credential{
		
	    public static final String BASIC = "Basic ";
	
	    public static final String BEARER = "Bearer ";
	}
    
	String credentialType = Credential.BASIC;
	String username;
	String credential;
	String authorization;
	
	public AuthorizationHeaderCredential(String bearer) {
		super();
		this.credential = bearer;
		this.credentialType = Credential.BEARER;
	}
	
	
	public AuthorizationHeaderCredential(String username, String credential) {
		super();
		this.username = username;
		this.credential = credential;
	}
	
	public String getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCredential() {
		return credential;
	}
	public void setCredential(String credential) {
		this.credential = credential;
	}

	public String transform() {
		if(credentialType.equalsIgnoreCase(Credential.BASIC)) {
			return AuthorizationHeaderUtils.createBasic(username, credential);
		}else {
			return AuthorizationHeaderUtils.createBearer(credential);
		}
	}
	
	
	
	@Override
	public String toString() {
		return "AuthorizationHeaderCredential [credentialType=" + credentialType + ", username=" + username
				+ ", credential=" + credential + "]";
	}
	
}
