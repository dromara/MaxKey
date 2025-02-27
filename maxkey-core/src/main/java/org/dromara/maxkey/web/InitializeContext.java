/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.web;

import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.mybatis.jpa.spring.MybatisJpaContext;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import jakarta.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * InitApplicationContext .
 * @author Crystal.Sea
 *
 */
public class InitializeContext extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(InitializeContext.class);
    private static final long serialVersionUID = -797399138268601444L;
    private static final String LOCALE_RESOLVER_BEAN= "localeResolver";
    private static final String COOKIE_LOCALE_RESOLVER_BEAN = "cookieLocaleResolver";
    
    final ApplicationContext applicationContext;

    @Override
    public void init() {
        
        WebContext.init(applicationContext);
        
        MybatisJpaContext.init(applicationContext);

        listProperties();

        // List DatabaseMetaData Variables
        listDataBaseVariables();

        // Show License
        showLicense();
    }

    /**
    * InitApplicationContext.
    */
    public InitializeContext(ConfigurableApplicationContext applicationContext) {
        if(applicationContext.containsBean(LOCALE_RESOLVER_BEAN) &&
                applicationContext.containsBean(COOKIE_LOCALE_RESOLVER_BEAN)) {
            BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry)applicationContext.getBeanFactory();
            beanFactory.removeBeanDefinition(LOCALE_RESOLVER_BEAN);
            beanFactory.registerBeanDefinition(LOCALE_RESOLVER_BEAN, 
                    beanFactory.getBeanDefinition(COOKIE_LOCALE_RESOLVER_BEAN));
            logger.debug("cookieLocaleResolver replaced localeResolver.");
        }
        this.applicationContext = applicationContext;
    }

    /**
     * listDataBaseVariables.
     */
    public void listDataBaseVariables() {
        if (!applicationContext.containsBean("dataSource")) {return;}
        try {
            logger.info(WebConstants.DELIMITER);
            logger.info("List DatabaseMetaData Variables ");
            Connection connection = ((javax.sql.DataSource) applicationContext.getBean("dataSource")).getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ApplicationConfig.setDatabaseProduct(databaseMetaData.getDatabaseProductName());
            
            logger.info("DatabaseProductName    :   {}", databaseMetaData.getDatabaseProductName());
            logger.info("DatabaseProductVersion :   {}" ,databaseMetaData.getDatabaseProductVersion());
            logger.trace("DatabaseMajorVersion  :   {}" , databaseMetaData.getDatabaseMajorVersion());
            logger.trace("DatabaseMinorVersion  :   {}" ,databaseMetaData.getDatabaseMinorVersion());
            logger.trace("supportsTransactions  :   {}" , databaseMetaData.supportsTransactions());
            logger.trace("DefaultTransaction    :   {}" ,databaseMetaData.getDefaultTransactionIsolation());
            logger.trace("MaxConnections        :   {}" ,databaseMetaData.getMaxConnections());
            logger.trace("");
            logger.trace("JDBCMajorVersion      :   {}" ,databaseMetaData.getJDBCMajorVersion());
            logger.trace("JDBCMinorVersion      :   {}" ,databaseMetaData.getJDBCMinorVersion());
            logger.trace("DriverName            :   {}" ,databaseMetaData.getDriverName());
            logger.trace("DriverVersion         :   {}" ,databaseMetaData.getDriverVersion());
            logger.info("");
            logger.info("DBMS  URL              :   {}" ,databaseMetaData.getURL());
            logger.info("UserName               :   {}" ,databaseMetaData.getUserName());
            logger.info(WebConstants.DELIMITER);
            
        } catch (SQLException e) {
            logger.error("DatabaseMetaData Variables Error .",e);
        }
    }

    /**
     * propertySourcesPlaceholderConfigurer.
     */
    public void listProperties() {
        if (!applicationContext.containsBean("propertySourcesPlaceholderConfigurer")) {return ;}
        logger.trace(WebConstants.DELIMITER);
        logger.trace("List Properties Variables ");
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = 
                ((PropertySourcesPlaceholderConfigurer) applicationContext
                .getBean("propertySourcesPlaceholderConfigurer"));
        
        WebContext.initProperties((StandardEnvironment) propertySourcesPlaceholderConfigurer
                .getAppliedPropertySources()
                .get(PropertySourcesPlaceholderConfigurer.ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME)
                .getSource());
        
        Iterator<PropertySource<?>> it =WebContext.properties.getPropertySources().iterator();
        while(it.hasNext()) {
        	 logger.debug("propertySource {}" , it.next());
        }
        
        logger.trace(WebConstants.DELIMITER);
    }

    /**
     * showLicense.
     */
    public void showLicense() {
        logger.info(WebConstants.DELIMITER);
        logger.info("                      MaxKey Community  Edition ");
        logger.info("                      Single   Sign  On ( SSO ) ");
        logger.info("                           Version {}", 
                        WebContext.properties.getProperty("application.formatted-version"));
        logger.info("");
        logger.info("                 {}Copyright 2018 - {} https://www.maxkey.top/",
        			    (char)0xA9 , new DateTime().getYear()
        			);
        logger.info("+                 Licensed under the Apache License, Version 2.0 ");
        logger.info(WebConstants.DELIMITER);
    }

}
