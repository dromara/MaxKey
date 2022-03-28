package org.maxkey.authn.jwt;

import java.util.ArrayList;
import java.util.List;

import org.maxkey.authn.SigninPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AuthJwt {
	  private String token;
	  private String type = "Bearer";
	  private String id;
	  private String name;
	  private String username;
	  private String displayName;
	  private String email;
	  private String instId;
	  private String instName;
	  private List<String> authorities;
	  
	  
	public AuthJwt(String token, String id, String username, String displayName, String email, String instId,
			String instName, List<String> authorities) {
		this.token = token;
		this.id = id;
		this.name = username;
		this.username = username;
		this.displayName = displayName;
		this.email = email;
		this.instId = instId;
		this.instName = instName;
		this.authorities = authorities;
	}
	
	public AuthJwt(String token, Authentication  authentication) {
		SigninPrincipal signinPrincipal = ((SigninPrincipal)authentication.getPrincipal());
		
		this.token = token;
		this.id = signinPrincipal.getUserInfo().getId();
		this.username = signinPrincipal.getUserInfo().getUsername();
		this.name = this.username;
		this.displayName = signinPrincipal.getUserInfo().getDisplayName();
		this.email = signinPrincipal.getUserInfo().getEmail();
		this.instId = signinPrincipal.getUserInfo().getInstId();
		this.instName = signinPrincipal.getUserInfo().getInstName();
		
		this.authorities = new ArrayList<String>();
		for(GrantedAuthority grantedAuthority :authentication.getAuthorities()) {
			this.authorities.add(grantedAuthority.getAuthority());
		}
	}
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getInstName() {
		return instName;
	}
	public void setInstName(String instName) {
		this.instName = instName;
	}
	public List<String> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AuthJwt [token=");
		builder.append(token);
		builder.append(", type=");
		builder.append(type);
		builder.append(", id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", instId=");
		builder.append(instId);
		builder.append(", instName=");
		builder.append(instName);
		builder.append(", authorities=");
		builder.append(authorities);
		builder.append("]");
		return builder.toString();
	}
	  
	  
	  
}
