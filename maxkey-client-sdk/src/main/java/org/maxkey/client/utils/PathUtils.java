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
 

package org.maxkey.client.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PathUtils {
	private  final  Log log = LogFactory.getLog(getClass());
	private static PathUtils instance = null;
	private String classPath;
	
	public static synchronized PathUtils getInstance() {
		if (instance == null) {
			instance = new PathUtils();
			instance.log.debug("getInstance()" +" new ConfigFile instance");
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
		log.info("getWebRoot getProtocol:"+PathUtils.class.getResource("PathUtilsFile.properties").getProtocol());
		if(fileProtocol.equalsIgnoreCase("file")){
			//   /D:/SoftWare/apache-tomcat-5.5.30/webapps/app
			if(classPath.indexOf("file:")==0){
				classPath=classPath.substring(5, classPath.length());
			}
		}else if(fileProtocol.equalsIgnoreCase("jar")){
			//   file:/D:/SoftWare/apache-tomcat-5.5.30/webapps/app
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
		
		log.info("getWebRoot()  webApp root Path : "+classPath);
	}
	
	public  String getWebInf(){
		return classPath+"/WEB-INF/";
	}
	
	
	public  String getClassPath(){
		return classPath+"/WEB-INF/classes/";
	}
}
