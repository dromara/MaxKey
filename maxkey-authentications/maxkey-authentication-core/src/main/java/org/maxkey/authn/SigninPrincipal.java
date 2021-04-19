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
 

package org.maxkey.authn;

import java.util.ArrayList;
import java.util.Collection;

import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class SigninPrincipal implements  UserDetails {
    private static final long serialVersionUID = -110742975439268030L;
    UserInfo userInfo;
    
    UserDetails userDetails;
    
    OnlineTicket onlineTicket;
    ArrayList<GrantedAuthority> grantedAuthority;
    ArrayList<GrantedAuthority> grantedAuthorityApps;
    boolean authenticated;
    boolean roleAdministrators;
    
	private  boolean accountNonExpired;

	private  boolean accountNonLocked;

	private  boolean credentialsNonExpired;

	private  boolean enabled;

    /**
     * SigninPrincipal.
     */
    public SigninPrincipal() {
    }
    
    /**
     * SigninPrincipal.
     */
    public SigninPrincipal(UserInfo userInfo) {
        this.userInfo = userInfo;
        this.authenticated = true;
        this.accountNonExpired = true;
        this.accountNonLocked  = true;
        this.credentialsNonExpired =true;
        this.enabled = true;
    }
    
    /**
     * SigninPrincipal.
     */
    public SigninPrincipal(UserDetails userDetails) {
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

    public ArrayList<GrantedAuthority> getGrantedAuthority() {
        return grantedAuthority;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public void setGrantedAuthority(ArrayList<GrantedAuthority> grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    public OnlineTicket getOnlineTicket() {
        return onlineTicket;
    }

    public void setOnlineTicket(OnlineTicket onlineTicket) {
        this.onlineTicket = onlineTicket;
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

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public ArrayList<GrantedAuthority> getGrantedAuthorityApps() {
        return grantedAuthorityApps;
    }

    public void setGrantedAuthorityApps(ArrayList<GrantedAuthority> grantedAuthorityApps) {
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
        builder.append("SigninPrincipal [userInfo=");
        builder.append(userInfo);
        builder.append(", onlineTicket=");
        builder.append(onlineTicket);
        builder.append(", grantedAuthority=");
        builder.append(grantedAuthority);
        builder.append(", authenticated=");
        builder.append(authenticated);
        builder.append(", roleAdministrators=");
        builder.append(roleAdministrators);
        builder.append("]");
        return builder.toString();
    }



}
