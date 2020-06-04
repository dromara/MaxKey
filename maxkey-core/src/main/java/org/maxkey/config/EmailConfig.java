package org.maxkey.config;

import org.maxkey.constants.ConstantsProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ConstantsProperties.applicationPropertySource)
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String username;
    
    @Value("${spring.mail.password}")
    private String password;
    
    @Value("${spring.mail.host}")
    private String smtpHost;
    
    @Value("${spring.mail.port}")
    private Integer port;
    
    @Value("${spring.mail.properties.ssl}")
    private boolean ssl;
    
    @Value("${spring.mail.properties.sender}")
    private String sender;

    public EmailConfig() {
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

}
