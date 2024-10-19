/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.authn;

import java.util.Collection;
import java.util.List;

import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class SignPrincipal implements  UserDetails {
    private static final long serialVersionUID = -110742975439268030L;
    UserInfo userInfo;
    
    UserDetails userDetails;
    
    String instId;
    String userId;
    
    String sessionId;
    
    List<GrantedAuthority> grantedAuthority;
    
    List<GrantedAuthority> grantedAuthorityApps;
    
    boolean authenticated;
    
    boolean roleAdministrators;
    
	private  boolean accountNonExpired;

	private  boolean accountNonLocked;

	private  boolean credentialsNonExpired;

	private  boolean enabled;

    /**
     * SigninPrincipal.
     */
    public SignPrincipal() {
    }
    
    /**
     * SignPrincipal.
     */
    public SignPrincipal(UserInfo userInfo) {
        this.userInfo = userInfo;
        this.userId =userInfo.getId();
        this.instId = userInfo.getInstId();
        this.authenticated = true;
        this.accountNonExpired = true;
        this.accountNonLocked  = true;
        this.credentialsNonExpired =true;
        this.enabled = true;
    }
    
    public SignPrincipal(UserInfo userInfo,Session session) {
        this.userInfo = userInfo;
        this.userId =userInfo.getId();
        this.instId = userInfo.getInstId();
        this.authenticated = true;
        this.accountNonExpired = true;
        this.accountNonLocked  = true;
        this.credentialsNonExpired =true;
        this.enabled = true;
        this.sessionId = session.getId();
        this.userInfo.setSessionId(session.getId());
    }
    
    /**
     * SigninPrincipal.
     */
    public SignPrincipal(UserDetails userDetails) {
        this.userDetails = userDetails;
        this.authenticated = true;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthority;
    }

    public List<GrantedAuthority> getGrantedAuthority() {
        return grantedAuthority;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public void setGrantedAuthority(List<GrantedAuthority> grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }


    public boolean isRoleAdministrators() {
        return roleAdministrators;
    }

    public void setRoleAdministrators(boolean roleAdministrators) {
        this.roleAdministrators = roleAdministrators;
    }
    

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    
    public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getInstId() {
		return instId;
	}

	public String getUserId() {
		return userId;
	}

	@Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public List<GrantedAuthority> getGrantedAuthorityApps() {
        return grantedAuthorityApps;
    }

    public void setGrantedAuthorityApps(List<GrantedAuthority> grantedAuthorityApps) {
        this.grantedAuthorityApps = grantedAuthorityApps;
    }

    @Override
    public String getUsername() {
        if(this.userInfo != null) {
            return this.userInfo.getUsername();
        }else {
            return this.userDetails.getUsername();
        }        
    }
    
    @Override
    public String getPassword() {
        if(this.userInfo != null) {
            return this.userInfo.getPassword();
        }else {
            return this.userDetails.getPassword();
        }  
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Principal [username=");
		builder.append(getUsername());
		builder.append(", userInfo=");
		builder.append(userInfo);
		builder.append(", userDetails=");
		builder.append(userDetails);
		builder.append(", grantedAuthority=");
		builder.append(grantedAuthority);
		builder.append(", grantedAuthorityApps=");
		builder.append(grantedAuthorityApps);
		builder.append(", authenticated=");
		builder.append(authenticated);
		builder.append(", roleAdministrators=");
		builder.append(roleAdministrators);
		builder.append(", accountNonExpired=");
		builder.append(accountNonExpired);
		builder.append(", accountNonLocked=");
		builder.append(accountNonLocked);
		builder.append(", credentialsNonExpired=");
		builder.append(credentialsNonExpired);
		builder.append(", enabled=");
		builder.append(enabled);
		builder.append("]");
		return builder.toString();
	}



}
