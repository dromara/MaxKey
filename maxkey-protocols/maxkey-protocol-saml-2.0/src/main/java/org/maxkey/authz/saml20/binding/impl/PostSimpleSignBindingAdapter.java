
package org.maxkey.authz.saml20.binding.impl;

import org.opensaml.saml2.binding.encoding.HTTPPostSimpleSignEncoder;
import org.opensaml.ws.security.SecurityPolicyResolver;

public class PostSimpleSignBindingAdapter extends PostBindingAdapter{

	public PostSimpleSignBindingAdapter() {
		super();
	}
	
	public PostSimpleSignBindingAdapter(String issuerEntityName) {
		super();
		this.issuerEntityName = issuerEntityName;
	}
	
	public PostSimpleSignBindingAdapter(String issuerEntityName, SecurityPolicyResolver securityPolicyResolver) {
		super();
		this.issuerEntityName = issuerEntityName;
		
		this.securityPolicyResolver = securityPolicyResolver;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		encoder = new HTTPPostSimpleSignEncoder(velocityEngine,"/templates/saml2-post-simplesign-binding.vm", true); 
	}




}
