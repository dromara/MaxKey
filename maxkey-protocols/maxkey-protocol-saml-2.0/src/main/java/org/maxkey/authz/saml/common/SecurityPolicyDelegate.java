
package org.maxkey.authz.saml.common;

import java.util.List;

import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.security.SecurityPolicy;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.ws.security.provider.BasicSecurityPolicy;

public class SecurityPolicyDelegate implements SecurityPolicy  {

	private final BasicSecurityPolicy basicSecurityPolicy;
	
	
	public SecurityPolicyDelegate() {
		super();
		basicSecurityPolicy = new BasicSecurityPolicy();
	}
	
	public SecurityPolicyDelegate(List<SecurityPolicyRule> securityPolicyRules) {
		super();
		basicSecurityPolicy = new BasicSecurityPolicy();
		basicSecurityPolicy.getPolicyRules().addAll(securityPolicyRules);
	}
	
	public void addSecurityPolicy(SecurityPolicyRule securityPolicyRule){
		basicSecurityPolicy.getPolicyRules().add(securityPolicyRule);
	}

	@Override
	public void evaluate(MessageContext messageContext) throws SecurityPolicyException {
		basicSecurityPolicy.evaluate(messageContext);
	}

	@Override
	public List<SecurityPolicyRule> getPolicyRules() {
		return basicSecurityPolicy.getPolicyRules();
	}

}
