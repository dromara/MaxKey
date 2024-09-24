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
 

package org.dromara.maxkey.password.onetimepwd.impl;

import java.text.MessageFormat;
import java.util.Properties;

import org.dromara.maxkey.configuration.EmailConfig;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;


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
            
            //Sender
            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
            javaMailSender.setUsername(emailConfig.getUsername());
            javaMailSender.setPassword(emailConfig.getPassword());
            Properties properties = new Properties();
            properties.put("mail.smtp.auth","true");
            javaMailSender.setJavaMailProperties(properties);
            javaMailSender.setHost(emailConfig.getSmtpHost());
            javaMailSender.setPort(emailConfig.getPort());
            
            //MailMessage
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(emailConfig.getSender());
            mailMessage.setTo(userInfo.getEmail());
            mailMessage.setSubject(subject);
            mailMessage.setText(
            		MessageFormat.format(
                    messageTemplate,userInfo.getUsername(),token,(interval / 60)));
            
            javaMailSender.send(mailMessage);
            
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

