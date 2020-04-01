package org.maxkey.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/config/applicationConfig.properties")
public class EmailConfig {

    @Value("${config.email.username}")
    private String username;
    @Value("${config.email.password}")
    private String password;
    @Value("${config.email.smtpHost}")
    private String smtpHost;
    @Value("${config.email.senderMail}")
    private String senderMail;
    @Value("${config.email.port}")
    private Integer port;
    @Value("${config.email.ssl}")
    private boolean ssl;

    public EmailConfig() {
        // TODO Auto-generated constructor stub
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

    /*
     * @return the senderMail
     */
    public String getSenderMail() {
        return senderMail;
    }

    /*
     * @param senderMail the senderMail to set
     */
    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
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
