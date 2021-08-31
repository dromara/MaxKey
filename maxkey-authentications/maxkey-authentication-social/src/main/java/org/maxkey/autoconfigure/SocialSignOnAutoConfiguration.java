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
 

package org.maxkey.autoconfigure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.maxkey.authn.support.socialsignon.service.JdbcSocialsAssociateService;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProvider;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan(basePackages = {
        "org.maxkey.authn.support.socialsignon"
})
public class SocialSignOnAutoConfiguration implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(SocialSignOnAutoConfiguration.class);
    
    @Bean(name = "socialSignOnProviderService")
    @ConditionalOnClass(SocialSignOnProvider.class)
    public SocialSignOnProviderService socialSignOnProviderService(
    		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer) throws IOException {
        SocialSignOnProviderService socialSignOnProviderService = new SocialSignOnProviderService();

        StandardEnvironment properties = (StandardEnvironment) propertySourcesPlaceholderConfigurer
                .getAppliedPropertySources()
                .get(PropertySourcesPlaceholderConfigurer.ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME)
                .getSource();
        
        List<SocialSignOnProvider> socialSignOnProviderList = new ArrayList<SocialSignOnProvider>();
       
        String [] providerList =properties.getProperty("maxkey.login.socialsignon.providers").toString().split(",");
        
        for(String provider : providerList) {
            String providerName = properties.getProperty("maxkey.socialsignon."+provider+".provider.name");
            String icon=properties.getProperty("maxkey.socialsignon."+provider+".icon");
            String clientId=properties.getProperty("maxkey.socialsignon."+provider+".client.id");
            String clientSecret=properties.getProperty("maxkey.socialsignon."+provider+".client.secret");
            String sortOrder = properties.getProperty("maxkey.socialsignon."+provider+".sortorder");
            String agentId = properties.getProperty("maxkey.socialsignon."+provider+".agent.id");
            String hidden = properties.getProperty("maxkey.socialsignon."+provider+".hidden");
            
            SocialSignOnProvider socialSignOnProvider = new SocialSignOnProvider();
            socialSignOnProvider.setProvider(provider);
            socialSignOnProvider.setProviderName(providerName);
            socialSignOnProvider.setIcon(icon);
            socialSignOnProvider.setClientId(clientId);
            socialSignOnProvider.setClientSecret(clientSecret);
            socialSignOnProvider.setSortOrder(Integer.valueOf(sortOrder));
            socialSignOnProvider.setAgentId(agentId);
            
            if(hidden == null || hidden.equalsIgnoreCase("false")) {
                socialSignOnProvider.setHidden(false);
            }else if(hidden.equalsIgnoreCase("true")){
                socialSignOnProvider.setHidden(true);
            }
            
            _logger.debug("socialSignOnProvider " + socialSignOnProvider.getProvider() 
            								+ "(" + socialSignOnProvider.getProviderName()+")");
            _logger.trace("socialSignOnProvider " + socialSignOnProvider);
            socialSignOnProviderList.add(socialSignOnProvider);            
        }
        socialSignOnProviderService.setSocialSignOnProviders(socialSignOnProviderList);
        _logger.debug("SocialSignOnProviderService inited.");
        return socialSignOnProviderService;
    }
    
    @Bean(name = "socialsAssociateService")
    public JdbcSocialsAssociateService socialsAssociateService(
                JdbcTemplate jdbcTemplate) {
        JdbcSocialsAssociateService socialsAssociateService = new JdbcSocialsAssociateService(jdbcTemplate);
        _logger.debug("JdbcSocialsAssociateService inited.");
        return socialsAssociateService;
    }
   

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
