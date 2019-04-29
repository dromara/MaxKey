package org.maxkey.authn.support.kerberos;

public interface KerberosService {
	
	public boolean login(String kerberosTokenString,String kerberosUserDomain);
	
	public  String buildKerberosProxys( );
	
}
