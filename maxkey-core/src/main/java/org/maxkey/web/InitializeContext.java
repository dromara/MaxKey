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
 

package org.maxkey.web;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.maxkey.cache.CacheFactory;
import org.maxkey.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * InitApplicationContext .
 * @author Crystal.Sea
 *
 */
public class InitializeContext extends HttpServlet {
    private static final Logger _logger = LoggerFactory.getLogger(InitializeContext.class);
    private static final long serialVersionUID = -797399138268601444L;
    ApplicationContext applicationContext;
    

    @Override
    public String getServletInfo() {
        return super.getServletInfo();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // List Environment Variables
        listEnvVars();

        listProperties();

        // List DatabaseMetaData Variables
        listDataBaseVariables();

        // load caches
        loadCaches();

        // Show License
        showLicense();
    }

    /**
    * InitApplicationContext.
    */
    public InitializeContext() {
        this.applicationContext = 
                WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
    }

    public InitializeContext(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * loadCaches.
     */
    public void loadCaches() {
        _logger.info("-----------------------------------------------------------");
        _logger.info("Load Caches ");

        try {
            if (applicationContext.containsBean("cacheFactory")) {
                CacheFactory cacheFactory = 
                        applicationContext.getBean("cacheFactory", CacheFactory.class);
                cacheFactory.start();
            }
        } catch (BeansException e) {
            e.printStackTrace();
        }
        _logger.info("-----------------------------------------------------------");

    }

    /**
     * listDataBaseVariables.
     */
    public void listDataBaseVariables() {
        if (applicationContext.containsBean("dataSource")) {
            try {
                _logger.debug("-----------------------------------------------------------");
                _logger.debug("List DatabaseMetaData Variables ");
                Connection connection = 
                        ((javax.sql.DataSource) applicationContext.getBean("dataSource"))
                        .getConnection();

                java.sql.DatabaseMetaData databaseMetaData = connection.getMetaData();
                _logger.debug("DatabaseProductName   :   " 
                        + databaseMetaData.getDatabaseProductName());
                _logger.debug("DatabaseProductVersion:   " 
                        + databaseMetaData.getDatabaseProductVersion());
                _logger.debug("DatabaseMajorVersion  :   " 
                        + databaseMetaData.getDatabaseMajorVersion());
                _logger.debug("DatabaseMinorVersion  :   " 
                        + databaseMetaData.getDatabaseMinorVersion());
                _logger.debug("supportsTransactions  :   " 
                        + databaseMetaData.supportsTransactions());
                _logger.debug("DefaultTransaction    :   " 
                        + databaseMetaData.getDefaultTransactionIsolation());
                _logger.debug("MaxConnections        :   " 
                        + databaseMetaData.getMaxConnections());
                _logger.debug("");
                _logger.debug("JDBCMajorVersion      :   " 
                        + databaseMetaData.getJDBCMajorVersion());
                _logger.debug("JDBCMinorVersion      :   " 
                        + databaseMetaData.getJDBCMinorVersion());
                _logger.debug("DriverName            :   " 
                        + databaseMetaData.getDriverName());
                _logger.debug("DriverVersion         :   " 
                        + databaseMetaData.getDriverVersion());
                _logger.debug("");
                _logger.debug("DBMS  URL             :   " 
                        + databaseMetaData.getURL());
                _logger.debug("UserName              :   " 
                        + databaseMetaData.getUserName());
                _logger.debug("-----------------------------------------------------------");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * propertySourcesPlaceholderConfigurer.
     */
    public void listProperties() {
        if (applicationContext.containsBean("propertySourcesPlaceholderConfigurer")) {
            _logger.trace("-----------------------------------------------------------");
            _logger.trace("List Properties Variables ");
            PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = 
                    ((PropertySourcesPlaceholderConfigurer) applicationContext
                    .getBean("propertySourcesPlaceholderConfigurer"));
            WebContext.properties = (Properties) propertySourcesPlaceholderConfigurer
                    .getAppliedPropertySources()
                    .get(PropertySourcesPlaceholderConfigurer.LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME)
                    .getSource();
            Set<Object> keyValue = WebContext.properties.keySet();
            SortedSet<String> keyValueSet = new TreeSet<String>();
            // sort key
            for (Iterator<Object> it = keyValue.iterator(); it.hasNext();) {
                String key = (String) it.next();
                keyValueSet.add(key);
            }
            // out
            for (Iterator<String> it = keyValueSet.iterator(); it.hasNext();) {
                String key = (String) it.next();
                _logger.trace(key + "   =   " + WebContext.properties.get(key));
            }
            _logger.trace("-----------------------------------------------------------");
        }
    }

    /**
     * listEnvVars.
     */
    public void listEnvVars() {
        _logger.debug("-----------------------------------------------------------");
        _logger.debug("List Environment Variables ");
        Map<String, String> map = System.getenv();
        SortedSet<String> keyValueSet = new TreeSet<String>();
        for (Iterator<String> itr = map.keySet().iterator(); itr.hasNext();) {
            String key = itr.next();
            keyValueSet.add(key);
        }
        // out
        for (Iterator<String> it = keyValueSet.iterator(); it.hasNext();) {
            String key = (String) it.next();
            _logger.trace(key + "   =   " + map.get(key));
        }
        _logger.debug("APP_HOME" + "   =   " + PathUtils.getInstance().getAppPath());
        _logger.debug("-----------------------------------------------------------");
    }

    /**
     * showLicense.
     */
    public void showLicense() {
        _logger.info("-----------------------------------------------------------");
        _logger.info("+                                MaxKey ");
        _logger.info("+                      Single   Sign   On ( SSO ) ");
        _logger.info("+                           Version "
                    + WebContext.properties.getProperty("application.formatted-version"));
        _logger.info("+");
        _logger.info("+                  ©Copyright 2018-2020 https://www.maxkey.top/");
        _logger.info("+                 Licensed under the Apache License, Version 2.0 ");
        _logger.info("-----------------------------------------------------------");
    }

}
