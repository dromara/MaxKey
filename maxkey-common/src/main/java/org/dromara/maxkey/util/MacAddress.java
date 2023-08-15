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
 

/**
 * 
 */
package org.dromara.maxkey.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.LoggerFactory;

/**
 * @author Crystal.Sea
 *
 */
public class MacAddress {
	public static String os;
	
	static{
		Properties prop = System.getProperties();
		os = prop.getProperty("os.name");
		LoggerFactory.getLogger(MacAddress.class).info("OS : "+os); 
	}
	
	public static  String  getAllHostMacAddress(){  
		String hostIpAddress="";
	    try {  
	        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();  
	        InetAddress inetAddress = null;  
	        while (netInterfaces.hasMoreElements()) {  
	            NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement(); 
	            if(ni.getInetAddresses().hasMoreElements()){
	            	inetAddress = (InetAddress) ni.getInetAddresses().nextElement(); 
	            	if(!inetAddress.isLoopbackAddress()){
		            	hostIpAddress += getMac(inetAddress)+",";
		            	LoggerFactory.getLogger(MacAddress.class).info("host MAC : "+getMac(inetAddress)); 
	            	}
	            }
	        }  
	    } catch (SocketException e) {  
	        e.printStackTrace();  
	    }  
	     return hostIpAddress;  
	   }  
	
	
	public static String getMac(InetAddress ia) throws SocketException {
		//获取网卡，获取地址
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		//LoggerFactory.getLogger(MacAddress.class).info("mac数组长度："+mac.length);
		
		
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				if(os.startsWith("win") || os.startsWith("Win") ){
					sb.append("-");//win
				}else{
					sb.append(":");//linux
				}
			}
			//字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		return sb.toString().toUpperCase();
	}
}
