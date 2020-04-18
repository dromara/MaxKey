package org.maxkey.crypto.password.opt.impl;

import java.text.MessageFormat;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.maxkey.config.EmailConfig;
import org.maxkey.crypto.password.opt.AbstractOptAuthn;
import org.maxkey.domain.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MailOtpAuthn extends AbstractOptAuthn {
    private static final Logger _logger = LoggerFactory.getLogger(MailOtpAuthn.class);
    
    @Autowired
    EmailConfig emailConfig;
    String subject = "One Time PassWord";
    
    String messageTemplate = "{0} You Token is {1} , it validity in {2}  minutes.";
    
    public MailOtpAuthn() {
        optType = OptTypes.EMAIL;
    }

    @Override
    public boolean produce(UserInfo userInfo) {
        try {
            String token = this.genToken(userInfo);
            Email email = new SimpleEmail();
            email.setHostName(emailConfig.getSmtpHost());
            email.setSmtpPort(emailConfig.getPort());
            email.setSSLOnConnect(emailConfig.isSsl());
            email.setAuthenticator(
                    new DefaultAuthenticator(emailConfig.getUsername(), emailConfig.getPassword()));
            
            email.setFrom(emailConfig.getSenderMail());
            email.setSubject(subject);
            email.setMsg(
                    MessageFormat.format(
                            messageTemplate,userInfo.getUsername(),token,(interval / 60)));
            
            email.addTo(userInfo.getEmail());
            email.send();
            _logger.debug(
                    "token " + token + " send to user " + userInfo.getUsername() 
                    + ", email " + userInfo.getEmail());
            //成功返回
            this.optTokenStore.store(
                    userInfo, 
                    token, 
                    userInfo.getMobile(), 
                    OptTypes.EMAIL);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean validate(UserInfo userInfo, String token) {
        return this.optTokenStore.validate(userInfo, token, OptTypes.EMAIL, interval);
    }

    public void setEmailConfig(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }
    

}
