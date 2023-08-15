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


package org.dromara.maxkey.authn;

import java.util.ArrayList;
import java.util.Collection;

import org.dromara.maxkey.web.WebConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class LoginCredential  implements Authentication {

    /**
     *
     */
    private static final long serialVersionUID = 3125709257481600320L;
    String congress;
    String username;
    String password;
    String state;
    String captcha;
    String otpCaptcha;
    String remeberMe;
    String authType;
    String jwtToken;
    String onlineTicket;
    String provider;
    String code;
    String message = WebConstants.LOGIN_RESULT.SUCCESS;
    String instId;


    ArrayList<GrantedAuthority> grantedAuthority;
    boolean authenticated;
    boolean roleAdministrators;
    String mobile;
    /**
     * BasicAuthentication.
     */
    public LoginCredential() {
    }

    /**
     * BasicAuthentication.
     */
    public LoginCredential(String username,String password,String authType) {
        this.username = username;
        this.password = password;
        this.authType = authType;
    }

    public String getCongress() {
        return congress;
    }

    public void setCongress(String congress) {
        this.congress = congress;
    }

    @Override
    public String getName() {
        return "Login Credential";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthority;
    }

    @Override
    public Object getCredentials() {
        return this.getPassword();
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getOtpCaptcha() {
        return otpCaptcha;
    }

    public void setOtpCaptcha(String otpCaptcha) {
        this.otpCaptcha = otpCaptcha;
    }

    public String getRemeberMe() {
        return remeberMe;
    }

    public void setRemeberMe(String remeberMe) {
        this.remeberMe = remeberMe;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public ArrayList<GrantedAuthority> getGrantedAuthority() {
        return grantedAuthority;
    }

    public void setGrantedAuthority(ArrayList<GrantedAuthority> grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    public String getOnlineTicket() {
        return onlineTicket;
    }

    public void setOnlineTicket(String onlineTicket) {
        this.onlineTicket = onlineTicket;
    }

    public boolean isRoleAdministrators() {
        return roleAdministrators;
    }

    public void setRoleAdministrators(boolean roleAdministrators) {
        this.roleAdministrators = roleAdministrators;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LoginCredential [congress=");
        builder.append(congress);
        builder.append(", username=");
        builder.append(username);
        builder.append(", password=");
        builder.append(password);
        builder.append(", state=");
        builder.append(state);
        builder.append(", mobile=");
        builder.append(mobile);
        builder.append(", captcha=");
        builder.append(captcha);
        builder.append(", otpCaptcha=");
        builder.append(otpCaptcha);
        builder.append(", remeberMe=");
        builder.append(remeberMe);
        builder.append(", authType=");
        builder.append(authType);
        builder.append(", jwtToken=");
        builder.append(jwtToken);
        builder.append(", onlineTicket=");
        builder.append(onlineTicket);
        builder.append(", provider=");
        builder.append(provider);
        builder.append(", code=");
        builder.append(code);
        builder.append(", message=");
        builder.append(message);
        builder.append(", instId=");
        builder.append(instId);
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