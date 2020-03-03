/**
 * 
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
 * @author Crystal.Sea
 *
 */
public class InitApplicationContext extends HttpServlet {
    private static final Logger _logger = LoggerFactory.getLogger(InitApplicationContext.class);
    
    ApplicationContext   applicationContext;
    
   /**
    * 
    */
   private static final long serialVersionUID = -797399138268601444L;

   @Override
   public String getServletInfo() {
      return super.getServletInfo();
   }

   @Override
   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      
      //List Environment Variables
      listEnvVars();
      
      listProperties();
      
      //List DatabaseMetaData Variables
      listDataBaseVariables();
            
      //load caches
      loadCaches();
      
      //Show   License
      showLicense();
   }

   /**
    * 
    */
   public InitApplicationContext() {
	   this.applicationContext=WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
   }
   
   public InitApplicationContext(ConfigurableApplicationContext  applicationContext) {
	   this.applicationContext=applicationContext;
   }
   
   public void  loadCaches(){
	   _logger.info("----------------------------------------------------------------------------------------------------");
       _logger.info("Load Caches ");
       
	   try {
			if(applicationContext.containsBean("cacheFactory")){
				CacheFactory cacheFactory=applicationContext.getBean("cacheFactory", CacheFactory.class);
				cacheFactory.start();
			}
		} catch (BeansException e) {
			e.printStackTrace();
		}
	    _logger.info("----------------------------------------------------------------------------------------------------");
	   
   }
   public void listDataBaseVariables(){
	   if(applicationContext.containsBean("dataSource")){
	      try {
	         _logger.debug("----------------------------------------------------------------------------------------------------");
	         _logger.debug("List DatabaseMetaData Variables ");
	         Connection connection = ((javax.sql.DataSource)applicationContext.getBean("dataSource")).getConnection();
	      
	         java.sql.DatabaseMetaData databaseMetaData = connection.getMetaData();
	         _logger.debug("DatabaseProductName   :   "   +   databaseMetaData.getDatabaseProductName());  
	         _logger.debug("DatabaseProductVersion:   "   +   databaseMetaData.getDatabaseProductVersion()); 
	         _logger.debug("DatabaseMajorVersion  :   "   +   databaseMetaData.getDatabaseMajorVersion());  
	         _logger.debug("DatabaseMinorVersion  :   "   +   databaseMetaData.getDatabaseMinorVersion()); 
	         _logger.debug("supportsTransactions  :   "   +   databaseMetaData.supportsTransactions());  
	         _logger.debug("DefaultTransaction    :   "   +   databaseMetaData.getDefaultTransactionIsolation());
	         _logger.debug("MaxConnections        :   "   +   databaseMetaData.getMaxConnections()); 
	         _logger.debug("");
	         _logger.debug("JDBCMajorVersion      :   "   +   databaseMetaData.getJDBCMajorVersion());  
	         _logger.debug("JDBCMinorVersion      :   "   +   databaseMetaData.getJDBCMinorVersion());  
	         _logger.debug("DriverName            :   "   +   databaseMetaData.getDriverName());  
	         _logger.debug("DriverVersion         :   "   +   databaseMetaData.getDriverVersion());  
	         _logger.debug("");
	         _logger.debug("DBMS  URL             :   "   +   databaseMetaData.getURL());  
	         _logger.debug("UserName              :   "   +   databaseMetaData.getUserName());  
	        _logger.debug("----------------------------------------------------------------------------------------------------");
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }  
	   }
   }
   
   //propertySourcesPlaceholderConfigurer
   public void listProperties(){
	   if(applicationContext.containsBean("propertySourcesPlaceholderConfigurer")){
	         _logger.debug("----------------------------------------------------------------------------------------------------");
	         _logger.debug("List Properties Variables ");
	         PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = ((PropertySourcesPlaceholderConfigurer)applicationContext.getBean("propertySourcesPlaceholderConfigurer"));
	         Properties properties=(Properties)propertySourcesPlaceholderConfigurer.getAppliedPropertySources().get(PropertySourcesPlaceholderConfigurer.LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME).getSource();
	         Set<Object> keyValue = properties.keySet();
	         SortedSet<String> keyValueSet=new TreeSet<String>();
	         //sort key
	         for (Iterator<Object> it = keyValue.iterator(); it.hasNext();){
		         String key = (String) it.next();
		         keyValueSet.add(key);
	         }
	         //out
	         for (Iterator<String> it = keyValueSet.iterator(); it.hasNext();){
		         String key = (String) it.next();
		         _logger.debug(key + "   =   " + properties.get(key));
	         }
	        _logger.debug("----------------------------------------------------------------------------------------------------");
	   }
   }
   
	public void listEnvVars() {
		_logger.debug("----------------------------------------------------------------------------------------------------");
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
			_logger.debug(key + "   =   " + map.get(key));
		}
		_logger.debug("APP_HOME" + "   =   " + PathUtils.getInstance().getAppPath());
		_logger.debug("----------------------------------------------------------------------------------------------------");
	}
   
   public void showLicense(){
      _logger.info("----------------------------------------------------------------------------------------------------");
      _logger.info("+                      Single   Sign   On ( SSO ) ");
      _logger.info("+                        MaxKey Version 1.2.1 GA");
      _logger.info("");
      _logger.info("+                      Apache License 2.0");
      _logger.info("+                      https://github.com/shimingxy/MaxKey");
      _logger.info("+                      email:shimingxy@163.com");
      _logger.info("----------------------------------------------------------------------------------------------------");
   }

}
