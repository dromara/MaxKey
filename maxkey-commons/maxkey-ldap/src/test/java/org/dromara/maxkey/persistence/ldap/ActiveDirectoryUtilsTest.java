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
 

package org.dromara.maxkey.persistence.ldap;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.dromara.maxkey.ldap.activedirectory.ActiveDirectoryUtils;

public class ActiveDirectoryUtilsTest {
	public static void main(String[] args) throws Exception {
		String trustStore="D:/JavaIDE/jdk1.6.0_30/jre/lib/security/cacerts";
		String trustStorePassword="changeit"; 
		//ActiveDirectoryUtils activeDirectoryUtils=new ActiveDirectoryUtils("ldap://192.168.0.171:389","administrator","p@ssw0rdp@ssw0rd","DC=kygfcrmtest,DC=com","kygfcrmtest");
		ActiveDirectoryUtils activeDirectoryUtils=new ActiveDirectoryUtils("ldaps://msad.connsec.com:636","administrator","1qaz@WSX","DC=CONNSEC,DC=com","CONNSEC");
		//ActiveDirectoryUtils activeDirectoryUtils=new ActiveDirectoryUtils("ldap://msad.connsec.com:389","administrator","1qaz@WSX","DC=CONNSEC,DC=com","CONNSEC");
		activeDirectoryUtils.setTrustStore(trustStore);
		activeDirectoryUtils.setTrustStorePassword(trustStorePassword);
		activeDirectoryUtils.setSsl(true);
		//activeDirectoryUtils.setSsl(false);
		DirContext dirContext=activeDirectoryUtils.openConnection();
		try {
			dirContext.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
