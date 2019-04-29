package org.maxkey.util;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathUtils {
	private  final  Logger _logger = LoggerFactory.getLogger(getClass());
	private static PathUtils instance = null;
	private String classPath;
	
	public static synchronized PathUtils getInstance() {
		if (instance == null) {
			instance = new PathUtils();
			instance._logger.debug("getInstance()" +" new ConfigFile instance");
		}
		return instance;
	}

	public PathUtils() {
		try {
			classPath = java.net.URLDecoder.decode(PathUtils.class.getResource("PathUtilsFile.properties").getFile(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String fileProtocol=PathUtils.class.getResource("PathUtilsFile.properties").getProtocol();
		_logger.info("getWebRoot getProtocol:"+PathUtils.class.getResource("PathUtilsFile.properties").getProtocol());
		if(fileProtocol.equalsIgnoreCase("file")){
			//   /D:/SoftWare/apache-tomcat-5.5.30/webapps/JqWeb
			if(classPath.indexOf("file:")==0){
				classPath=classPath.substring(5, classPath.length());
			}
		}else if(fileProtocol.equalsIgnoreCase("jar")){
			//   file:/D:/SoftWare/apache-tomcat-5.5.30/webapps/JqWeb
			if(classPath.indexOf("file:")==0){
				classPath=classPath.substring(5, classPath.length());
			}
		}else if(fileProtocol.equalsIgnoreCase("wsjar")){
			if(classPath.indexOf("file:")==0){
				classPath=classPath.substring(5, classPath.length());
			}			
		}else if(classPath.equalsIgnoreCase("file:")){
			classPath=classPath.substring(5, classPath.length());
		}
		
		///   /WEB-INF/
		if(classPath.indexOf("/WEB-INF/")!=-1){
			classPath=classPath.substring(0, classPath.indexOf("/WEB-INF/"));
		}
		
		_logger.info("getWebRoot()  webApp root Path : "+classPath);
	}
	
	public  String getClassPath(){
		return classPath+"/WEB-INF/classes/";
	}
}
