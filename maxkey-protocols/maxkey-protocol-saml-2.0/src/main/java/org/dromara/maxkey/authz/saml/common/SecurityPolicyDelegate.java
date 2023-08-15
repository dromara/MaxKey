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
 


package org.dromara.maxkey.authz.saml.common;

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
