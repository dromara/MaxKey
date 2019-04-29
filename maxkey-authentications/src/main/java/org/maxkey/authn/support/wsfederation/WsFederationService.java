package org.maxkey.authn.support.wsfederation;

import javax.servlet.http.HttpServletRequest;

public interface WsFederationService {
	public boolean login(String wsFederationWA,String wsFederationWResult,HttpServletRequest request);
	
}
