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

import org.dromara.maxkey.constants.ConstsDatabase;
import org.dromara.maxkey.constants.ConstsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
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
@Configuration
public class ApplicationConfig {
    
	@Value("${maxkey.server.basedomain}")
    String baseDomainName;

    @Value("${maxkey.server.domain}")
    String domainName;

    @Value("${maxkey.server.name}")
    String serverName;

    @Value("${maxkey.server.uri}")
    String serverPrefix;

    @Value("${maxkey.server.default.uri}")
    String defaultUri;

    @Value("${maxkey.server.mgt.uri}")
    String mgtUri;
    
    @Value("${maxkey.server.authz.uri}")
    private String authzUri;
    
    @Value("${maxkey.server.frontend.uri:http://sso.maxkey.top:4200}")
    private String frontendUri;

    @Value("${server.port:8080}")
    private int port;
    
    @Value("${maxkey.server.provision:false}")
    private boolean provision;
    
    @Value("${maxkey.server.persistence}") 
    int persistence;
    
    @Value("${maxkey.notices.visible:false}")
    private boolean noticesVisible;
    
    static String  databaseProduct = ConstsDatabase.MYSQL;
	    
	@Autowired
    EmailConfig emailConfig;
    
    @Autowired
    CharacterEncodingConfig characterEncodingConfig;
    
    @Autowired
    LoginConfig loginConfig;

   
    
    
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ApplicationConfig() {
        super();
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

    public String getFrontendUri() {
		return frontendUri;
	}

	public void setFrontendUri(String frontendUri) {
		this.frontendUri = frontendUri;
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
       
    }

    public String getBaseDomainName() {
        return baseDomainName;
    }

    public void setBaseDomainName(String baseDomainName) {
        this.baseDomainName = baseDomainName;
    }

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

 

    public String getDefaultUri() {
        return defaultUri;
    }

    public void setDefaultUri(String defaultUri) {
        this.defaultUri = defaultUri;
    }

    public boolean isProvision() {
		return provision;
	}

	public void setProvision(boolean provision) {
		this.provision = provision;
	}

	public boolean isProvisionSupport() {
    	return provision;
	}
    
	public int getPersistence() {
		return persistence;
	}

	public void setPersistence(int persistence) {
		this.persistence = persistence;
	}
    
	public boolean isPersistenceRedis() {
		return persistence == ConstsPersistence.REDIS;
	}
	
	public boolean isPersistenceInmemory() {
		return persistence == ConstsPersistence.INMEMORY;
	}
	
	public String getMgtUri() {
		return mgtUri;
	}

	public void setMgtUri(String mgtUri) {
		this.mgtUri = mgtUri;
	}

	public String getAuthzUri() {
		return authzUri;
	}

	public void setAuthzUri(String authzUri) {
		this.authzUri = authzUri;
	}

	public boolean isNoticesVisible() {
		return noticesVisible;
	}

	public void setNoticesVisible(boolean noticesVisible) {
		this.noticesVisible = noticesVisible;
	}

	public static String getDatabaseProduct() {
		return databaseProduct;
	}

	public static void setDatabaseProduct(String databaseProduct) {
		ApplicationConfig.databaseProduct = databaseProduct;
	}

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ApplicationConfig [emailConfig=");
        builder.append(emailConfig);
        builder.append(", characterEncodingConfig=");
        builder.append(characterEncodingConfig);
        builder.append(", loginConfig=");
        builder.append(loginConfig);
        builder.append(", baseDomainName=");
        builder.append(baseDomainName);
        builder.append(", domainName=");
        builder.append(domainName);
        builder.append(", serverName=");
        builder.append(serverName);
        builder.append(", serverPrefix=");
        builder.append(serverPrefix);
        builder.append(", defaultUri=");
        builder.append(defaultUri);
        builder.append(", managementUri=");
        builder.append(mgtUri);
        builder.append(", port=");
        builder.append(port);
        builder.append(", provision=");
        builder.append(provision);
        builder.append(", maxKeyUri=");
        builder.append(authzUri);
        builder.append("]");
        return builder.toString();
    }

}
