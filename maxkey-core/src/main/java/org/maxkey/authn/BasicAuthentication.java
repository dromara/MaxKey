package org.maxkey.authn;

import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class BasicAuthentication implements Authentication{
	/**
	 * 
	 */
	private static final long serialVersionUID = -110742975439268030L;
	String j_username ;
    String j_password ;
    String j_sessionid;
    String j_captcha;
    String j_otp_captcha;
    String j_remeberme;
    String j_auth_type;
    String j_jwt_token;
    
    boolean authenticated;
    
	public BasicAuthentication() {
	}

	@Override
	public String getName() {
		return "Basic Authentication";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public Object getCredentials() {
		return this.getJ_password();
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.getJ_username();
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
		this.authenticated=authenticated;
		
	}

	public String getJ_username() {
		return j_username;
	}

	public void setJ_username(String j_username) {
		this.j_username = j_username;
	}

	public String getJ_password() {
		return j_password;
	}

	public void setJ_password(String j_password) {
		this.j_password = j_password;
	}

	public String getJ_sessionid() {
		return j_sessionid;
	}

	public void setJ_sessionid(String j_sessionid) {
		this.j_sessionid = j_sessionid;
	}

	public String getJ_captcha() {
		return j_captcha;
	}

	public void setJ_captcha(String j_captcha) {
		this.j_captcha = j_captcha;
	}

	public String getJ_otp_captcha() {
		return j_otp_captcha;
	}

	public void setJ_otp_captcha(String j_otp_captcha) {
		this.j_otp_captcha = j_otp_captcha;
	}

	public String getJ_remeberme() {
		return j_remeberme;
	}

	public void setJ_remeberme(String j_remeberme) {
		this.j_remeberme = j_remeberme;
	}

	public String getJ_auth_type() {
		return j_auth_type;
	}

	public void setJ_auth_type(String j_auth_type) {
		this.j_auth_type = j_auth_type;
	}

	public String getJ_jwt_token() {
		return j_jwt_token;
	}

	public void setJ_jwt_token(String j_jwt_token) {
		this.j_jwt_token = j_jwt_token;
	}

	@Override
	public String toString() {
		return "BasicAuthentication [j_username=" + j_username + ", j_sessionId=" + j_sessionid + ", j_captcha="
				+ j_captcha + ", j_otp_captcha=" + j_otp_captcha + ", j_remeberMe=" + j_remeberme + ", j_auth_type="
				+ j_auth_type + ", j_jwtToken=" + j_jwt_token + ", authenticated=" + authenticated + "]";
	}
	
	

}
