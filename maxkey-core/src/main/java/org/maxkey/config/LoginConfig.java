package org.maxkey.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/config/applicationConfig.properties")
public class LoginConfig {
    @Value("${config.login.captcha}")
    boolean captcha;
    @Value("${config.login.onetimepwd}")
    boolean oneTimePwd;
    @Value("${config.login.socialsignon}")
    boolean socialSignOn;
    @Value("${config.login.kerberos}")
    boolean kerberos;
    @Value("${config.login.remeberme}")
    boolean remeberMe;
    @Value("${config.login.wsfederation}")
    boolean wsFederation;
    @Value("${config.login.default.uri}")
    String defaultUri;

    /**
     *  .
     */
    public LoginConfig() {
        // TODO Auto-generated constructor stub
    }

    public boolean isCaptcha() {
        return captcha;
    }

    public void setCaptcha(boolean captcha) {
        this.captcha = captcha;
    }

    public boolean isOneTimePwd() {
        return oneTimePwd;
    }

    public void setOneTimePwd(boolean oneTimePwd) {
        this.oneTimePwd = oneTimePwd;
    }

    public boolean isSocialSignOn() {
        return socialSignOn;
    }

    public void setSocialSignOn(boolean socialSignOn) {
        this.socialSignOn = socialSignOn;
    }

    public boolean isKerberos() {
        return kerberos;
    }

    public void setKerberos(boolean kerberos) {
        this.kerberos = kerberos;
    }

    public String getDefaultUri() {
        return defaultUri;
    }

    public void setDefaultUri(String defaultUri) {
        this.defaultUri = defaultUri;
    }

    public boolean isRemeberMe() {
        return remeberMe;
    }

    public void setRemeberMe(boolean remeberMe) {
        this.remeberMe = remeberMe;
    }

    public boolean isWsFederation() {
        return wsFederation;
    }

    public void setWsFederation(boolean wsFederation) {
        this.wsFederation = wsFederation;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("LoginConfig [captcha=").append(captcha)
            .append(", oneTimePwd=").append(oneTimePwd)
            .append(", socialSignOn=").append(socialSignOn)
            .append(", kerberos=").append(kerberos)
            .append(", remeberMe=").append(remeberMe)
            .append(", wsFederation=").append(wsFederation)
            .append(", defaultUri=").append(defaultUri).append("]");
        return builder.toString();
    }

}
