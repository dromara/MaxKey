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
 

package org.maxkey.password.onetimepwd.impl;

import java.text.MessageFormat;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.maxkey.configuration.EmailConfig;
import org.maxkey.entity.UserInfo;
import org.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailOtpAuthn extends AbstractOtpAuthn {
    private static final Logger _logger = LoggerFactory.getLogger(MailOtpAuthn.class);
    
    EmailConfig emailConfig;
    String subject = "One Time PassWord";
    
    String messageTemplate = "{0} You Token is {1} , it validity in {2}  minutes.";
    
    public MailOtpAuthn() {
        otpType = OtpTypes.EMAIL;
    }
    
    public MailOtpAuthn(EmailConfig emailConfig) {
    	otpType = OtpTypes.EMAIL;
		this.emailConfig = emailConfig;
	}

	public MailOtpAuthn(EmailConfig emailConfig, String subject, String messageTemplate) {
		otpType = OtpTypes.EMAIL;
		this.emailConfig = emailConfig;
		this.subject = subject;
		this.messageTemplate = messageTemplate;
	}



	@Override
    public boolean produce(UserInfo userInfo) {
        try {
            String token = this.genToken(userInfo);
            Email email = new HtmlEmail();
            email.setCharset(this.defaultEncoding);
            email.setHostName(emailConfig.getSmtpHost());
            email.setSmtpPort(emailConfig.getPort());
            email.setSSLOnConnect(emailConfig.isSsl());
            email.setAuthenticator(
                    new DefaultAuthenticator(emailConfig.getUsername(), emailConfig.getPassword()));
            
            email.setFrom(emailConfig.getSender());
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
                    OtpTypes.EMAIL);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean validate(UserInfo userInfo, String token) {
        return this.optTokenStore.validate(userInfo, token, OtpTypes.EMAIL, interval);
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
