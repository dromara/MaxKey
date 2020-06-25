package org.maxkey.configuration;

import org.maxkey.constants.ConstantsProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ConstantsProperties.maxKeyPropertySource)
public class LoginConfig {
    @Value("${config.login.captcha}")
    boolean captcha;
    
    //验证码类型 text 文本 ， arithmetic算术验证码
    @Value("${config.login.captcha.type:text}")
    String captchaType;
    
    @Value("${config.login.mfa}")
    boolean mfa;
    
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

    public boolean isMfa() {
        return mfa;
    }

    public void setMfa(boolean mfa) {
        this.mfa = mfa;
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
    
    public String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        this.captchaType = captchaType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("LoginConfig [captcha=").append(captcha)
            .append(", mfa=").append(mfa)
            .append(", socialSignOn=").append(socialSignOn)
            .append(", kerberos=").append(kerberos)
            .append(", remeberMe=").append(remeberMe)
            .append(", wsFederation=").append(wsFederation)
            .append(", defaultUri=").append(defaultUri).append("]");
        return builder.toString();
    }

}
