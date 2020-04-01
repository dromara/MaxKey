package org.maxkey.authn;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public class BasicAuthentication implements Authentication {
    private static final long serialVersionUID = -110742975439268030L;
    String username;
    String password;
    String sessionId;
    String captcha;
    String otpCaptcha;
    String remeberMe;
    String authType;
    String jwtToken;
    ArrayList<GrantedAuthority> grantedAuthority;
    boolean authenticated;

    /**
     * BasicAuthentication.
     */
    public BasicAuthentication() {
        grantedAuthority = new ArrayList<GrantedAuthority>();
        grantedAuthority.add(new SimpleGrantedAuthority("ROLE_USER"));
        grantedAuthority.add(new SimpleGrantedAuthority("ORDINARY_USER"));
    }

    @Override
    public String getName() {
        return "Basic Authentication";
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BasicAuthentication [username=").append(username)
                .append(", password=").append(password)
                .append(", sessionId=").append(sessionId)
                .append(", captcha=").append(captcha)
                .append(", otpCaptcha=").append(otpCaptcha)
                .append(", remeberMe=").append(remeberMe)
                .append(", authType=").append(authType)
                .append(", jwtToken=").append(jwtToken)
                .append(", grantedAuthority=").append(grantedAuthority)
                .append(", authenticated=").append(authenticated)
                .append("]");
        return builder.toString();
    }
}
