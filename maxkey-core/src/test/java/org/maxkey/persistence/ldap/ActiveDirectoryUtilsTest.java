package org.maxkey.persistence.ldap;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.maxkey.persistence.ldap.ActiveDirectoryUtils;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
