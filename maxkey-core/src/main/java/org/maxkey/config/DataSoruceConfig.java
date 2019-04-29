package org.maxkey.config;

import org.apache.commons.logging.LogFactory;
import org.apache.mybatis.jpa.dialect.Dialect;
import org.maxkey.crypto.Base64Utils;
import org.maxkey.crypto.ReciprocalUtils;

/**
 * 数据源配置
 *
 * @author Crystal.Sea
 * dataSource.driverClassName=com.mysql.jdbc.Driver
 * dataSource.url=jdbc:mysql://192.168.1.49/parasecdb?autoReconnect=true&characterEncoding=UTF-8
 * dataSource.username=root
 * dataSource.password=connsec
 * dataSource.type=mysql
 *
 */
/**
 * @author Crystal.Sea
 *
 */
public class DataSoruceConfig {
	
	/**
	 * 数据库类型
	 */
	String database;
	/**
	 * jdbc驱动类
	 */
	String driverClass;
	/**
	 * jdbc连接地址
	 */
	String url;
	/**
	 * 数据库用户名
	 */
	String username;
	/**
	 * 数据库密码
	 */
	String password;
	
	/**
	 * 数据库密码是否加密
	 */
	boolean encrypt=false;
	
	/**
	 * 数据库dialect for mybatis
	 */
	String dialect;
	

	
	public DataSoruceConfig() {
		super();
	}
	

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * 取得数据库密码
	 * 如果是加密密码(encrypt==true)，则进行解密
	 * @return decodePassword
	 */
	public String getPassword() {
		String decodePassword="";
		LogFactory.getLog(DataSoruceConfig.class).debug("password is "+password);
		if(encrypt){
			decodePassword = ReciprocalUtils.decoder(password);
		}else{
			decodePassword= password;
		}
		LogFactory.getLog(DataSoruceConfig.class).debug("password is "+password+" , decodePassword is "+Base64Utils.encode(decodePassword));
		return decodePassword;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}


	/**
	 * @param database the database to set
	 */
	public void setDatabase(String database) {
		this.dialect=Dialect.getDialectMap().get(database);
		this.database = database;

	}


	/**
	 * @return the driverClass
	 */
	public String getDriverClass() {
		return driverClass;
	}


	/**
	 * @param driverClass the driverClass to set
	 */
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}


	public boolean isEncrypt() {
		return encrypt;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}

	

	/**
	 * @return the dialect
	 */
	public String getDialect() {
		return dialect;
	}


	/**
	 * @param dialect the dialect to set
	 */
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataSoruceConfig [database=" + database + ", driverClass="
				+ driverClass + ", url=" + url + ", username=" + username
				+ ", password=" + password + ", encrypt=" + encrypt + "]";
	}


	
	
}
