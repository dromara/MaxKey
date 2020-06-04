package org.maxkey.config;

import org.maxkey.constants.ConstantsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 全局应用程序配置 包含 1、数据源配置 dataSoruceConfig 2、字符集转换配置 characterEncodingConfig
 * 3、webseal认证集成配置 webSealConfig 4、系统的配置 sysConfig 5、所有用户可访问地址配置 allAccessUrl
 * 
 * 其中1、2、3项在applicationContext.xml中配置，配置文件applicationConfig.properties
 * 4项根据dynamic的属性判断是否动态从sysConfigService动态读取
 * 
 * @author Crystal.Sea
 * 
 */
@Component
@PropertySource(ConstantsProperties.maxKeyPropertySource)
@PropertySource(ConstantsProperties.applicationPropertySource)
public class ApplicationConfig {
    private static final Logger _logger = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    EmailConfig emailConfig;
    @Autowired
    CharacterEncodingConfig characterEncodingConfig;
    @Autowired
    LoginConfig loginConfig;

    @Value("${config.server.domain}")
    String domainName;

    @Value("${config.server.domain.sub}")
    String subDomainName;

    @Value("${config.server.name}")
    String serverName;

    @Value("${config.server.prefix.uri}")
    String serverPrefix;

    @Value("${config.server.default.uri}")
    String defaultUri;

    @Value("${config.server.management.uri}")
    String managementUri;

    @Value("${server.port:8080}")
    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    /*
     * //is enable whiteList for ipAddress filter boolean whiteList;
     * 
     * //All user have permission Access URL public ConcurrentHashMap<String,String>
     * anonymousAccessUrls;
     */

    public ApplicationConfig() {
        super();

        /*
         * anonymousAccessUrls=new ConcurrentHashMap<String,String>();
         * anonymousAccessUrls.put("/index/", "/index/");
         * anonymousAccessUrls.put("/index/top","/index/top/");
         * anonymousAccessUrls.put("/index/left/","/index/left/");
         * anonymousAccessUrls.put("/index/main/","/index/main/");
         * anonymousAccessUrls.put("/index/bottom/","/index/bottom/");
         * 
         * anonymousAccessUrls.put("/menus/onelevelchild/","/menus/onelevelchild/");
         * anonymousAccessUrls.put("/menus/leftchild/","/menus/leftchild/");
         * anonymousAccessUrls.put("/menus/loadMenu/","/menus/loadMenu/");
         * 
         * anonymousAccessUrls.put("/enterprises/select/","/enterprises/select/");
         * anonymousAccessUrls.put("/employees/selectAppRoles/",
         * "/employees/selectAppRoles/");
         * anonymousAccessUrls.put("/approles/appRolesGrid/","/approles/appRolesGrid/");
         * 
         * _logger.debug("Anonymous Access Urls : \n"+anonymousAccessUrls);
         */

    }

    /**
     * @return the characterEncodingConfig
     */
    public CharacterEncodingConfig getCharacterEncodingConfig() {
        return characterEncodingConfig;
    }

    /**
     * @param characterEncodingConfig the characterEncodingConfig to set
     */
    public void setCharacterEncodingConfig(CharacterEncodingConfig characterEncodingConfig) {
        this.characterEncodingConfig = characterEncodingConfig;
    }

    public LoginConfig getLoginConfig() {
        return loginConfig;
    }

    public void setLoginConfig(LoginConfig loginConfig) {
        this.loginConfig = loginConfig;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerPrefix() {
        return serverPrefix;
    }

    public void setServerPrefix(String serverPrefix) {
        this.serverPrefix = serverPrefix;
    }

    /**
     * @return the domainName
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * @param domainName the domainName to set
     */
    public void setDomainName(String domainName) {
        this.domainName = domainName;
        String[] domainSubStrings = domainName.split("\\.");
        if (domainSubStrings.length >= 3) {
            this.subDomainName = domainSubStrings[domainSubStrings.length - 2] + "."
                    + domainSubStrings[domainSubStrings.length - 1];
            _logger.debug("subDomainName " + subDomainName);
        } else {
            this.subDomainName = domainName;
        }
    }

    public String getSubDomainName() {
        return subDomainName;
    }

    public void setSubDomainName(String subDomainName) {
        this.subDomainName = subDomainName;
    }

    /*
     * public ConcurrentHashMap<String, String> getAnonymousAccessUrls() { return
     * anonymousAccessUrls; }
     * 
     * public void setAnonymousAccessUrls(ArrayList<String> anonymousAccessUrls) {
     * //this.anonymousAccessUrls = anonymousAccessUrls; for (String
     * anonymousAccessUrl: anonymousAccessUrls){
     * this.anonymousAccessUrls.put(anonymousAccessUrl,anonymousAccessUrl); } }
     */
    /**
     * @return the emailConfig
     */
    public EmailConfig getEmailConfig() {
        return emailConfig;
    }

    /**
     * @param emailConfig the emailConfig to set
     */
    public void setEmailConfig(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    public String getManagementUri() {
        return managementUri;
    }

    public void setManagementUri(String managementUri) {
        this.managementUri = managementUri;
    }

    public String getDefaultUri() {
        return defaultUri;
    }

    public void setDefaultUri(String defaultUri) {
        this.defaultUri = defaultUri;
    }

    /*
     * public boolean isWhiteList() { return whiteList; }
     * 
     * public void setWhiteList(boolean whiteList) { this.whiteList = whiteList; }
     */

}
