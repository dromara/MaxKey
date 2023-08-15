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
 


package org.dromara.maxkey.authz.saml20.binding.impl;

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
