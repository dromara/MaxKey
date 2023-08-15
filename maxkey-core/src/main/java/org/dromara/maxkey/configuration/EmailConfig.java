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
 

package org.dromara.maxkey.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String username;
    
    @Value("${spring.mail.password}")
    private String password;
    
    @Value("${spring.mail.host}")
    private String smtpHost;
    
    @Value("${spring.mail.port:465}")
    private Integer port;
    
    @Value("${spring.mail.properties.ssl:false}")
    private boolean ssl;
    
    @Value("${spring.mail.properties.sender}")
    private String sender;

    public EmailConfig() {
    }

    public EmailConfig(String username, String password, String smtpHost, Integer port, boolean ssl, String sender) {
		super();
		this.username = username;
		this.password = password;
		this.smtpHost = smtpHost;
		this.port = port;
		this.ssl = ssl;
		this.sender = sender;
	}

	/*
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /*
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /*
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /*
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /*
     * @return the smtpHost
     */
    public String getSmtpHost() {
        return smtpHost;
    }

    /*
     * @param smtpHost the smtpHost to set
     */
    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }



    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    /*
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /*
     * @param port the port to set
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /*
     * @return the ssl
     */
    public boolean isSsl() {
        return ssl;
    }

    /*
     * @param ssl the ssl to set
     */
    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EmailConfig [username=");
        builder.append(username);
        builder.append(", password=");
        builder.append(password);
        builder.append(", smtpHost=");
        builder.append(smtpHost);
        builder.append(", port=");
        builder.append(port);
        builder.append(", ssl=");
        builder.append(ssl);
        builder.append(", sender=");
        builder.append(sender);
        builder.append("]");
        return builder.toString();
    }

}
