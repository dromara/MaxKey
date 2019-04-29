package org.maxkey.web;

public class KerberosConfig {
	String keyTabLocation;
	String krbConfLocation;
	String servicePrincipal;
	boolean debug;
	
	
	/**
	 * 
	 */
	public KerberosConfig() {
		// TODO Auto-generated constructor stub
	}
	
	public String getKeyTabLocation() {
		return keyTabLocation;
	}
	public void setKeyTabLocation(String keyTabLocation) {
		this.keyTabLocation = keyTabLocation;
	}
	public String getKrbConfLocation() {
		return krbConfLocation;
	}
	public void setKrbConfLocation(String krbConfLocation) {
		this.krbConfLocation = krbConfLocation;
	}
	public String getServicePrincipal() {
		return servicePrincipal;
	}
	public void setServicePrincipal(String servicePrincipal) {
		this.servicePrincipal = servicePrincipal;
	}
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	@Override
	public String toString() {
		return "KerberosConfig [keyTabLocation=" + keyTabLocation
				+ ", krbConfLocation=" + krbConfLocation
				+ ", servicePrincipal=" + servicePrincipal + ", debug=" + debug
				+ "]";
	}
	
}

