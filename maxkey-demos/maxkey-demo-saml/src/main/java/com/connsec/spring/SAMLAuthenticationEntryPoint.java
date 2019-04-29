/*
*   Copyright 2010 James Cox <james.s.cox@gmail.com>
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/

package com.connsec.spring;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.connsec.saml.AuthnRequestGenerator;
import com.connsec.saml.binding.BindingAdapter;
import com.connsec.saml.util.IDService;
import com.connsec.saml.util.TimeService;
import com.connsec.saml.xml.EndpointGenerator;

/**
 * Sample AuthenticationEntryPoint that a service provider might use.
 * 
 * A more sophisticated implementation might resolve the service end points using a SAML META service.
 * 
 * 
 * 
 * @author jcox
 *
 */
public class SAMLAuthenticationEntryPoint implements AuthenticationEntryPoint, InitializingBean {

	private final static Logger logger = LoggerFactory.getLogger(SAMLAuthenticationEntryPoint.class);
	
	private final TimeService timeService;
	private final IDService idService;
	private final String issuer;
	
	 private String singleSignOnServiceURL;
	 private String assertionConsumerServiceURL;
	 private BindingAdapter bindingAdapter;
	 EndpointGenerator endpointGenerator;
	 AuthnRequestGenerator authnRequestGenerator;
	 CredentialResolver credentialResolver;
	 
	 Credential signingCredential;

	public SAMLAuthenticationEntryPoint(TimeService timeService,
			IDService idService, String issuer) {
		super();
		this.timeService = timeService;
		this.idService = idService;
		this.issuer = issuer;
	}

	@Required
	public void setSingleSignOnServiceURL(String singleSignOnServiceURL) {
		this.singleSignOnServiceURL = singleSignOnServiceURL;
	}

	@Required
	public void setAssertionConsumerServiceURL(String assertionConsumerServiceURL) {
		this.assertionConsumerServiceURL = assertionConsumerServiceURL;
	}

	@Required
	public void setBindingAdapter(BindingAdapter bindingAdapter) {
		this.bindingAdapter = bindingAdapter;
	}

	
	@Required
	public void setCredentialResolver(CredentialResolver credentialResolver) {
		this.credentialResolver = credentialResolver;
	}

	@Override
	public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
		
		Endpoint endpoint = endpointGenerator.generateEndpoint(SingleSignOnService.DEFAULT_ELEMENT_NAME, singleSignOnServiceURL, assertionConsumerServiceURL);
		
		AuthnRequest authnReqeust =  authnRequestGenerator.generateAuthnRequest(singleSignOnServiceURL,assertionConsumerServiceURL);
		
		logger.debug("Sending authnRequest to {}", singleSignOnServiceURL );
		
		try {
			bindingAdapter.sendSAMLMessage(authnReqeust, endpoint, signingCredential,response);
		} catch (MessageEncodingException mee) {
			logger.error("Could not send authnRequest to Identity Provider.", mee);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		authnRequestGenerator = new AuthnRequestGenerator(issuer,timeService,idService);
		endpointGenerator = new EndpointGenerator();
		

		CriteriaSet criteriaSet = new CriteriaSet();
		criteriaSet.add(new EntityIDCriteria(issuer));
		criteriaSet.add(new UsageCriteria(UsageType.SIGNING));

		signingCredential = credentialResolver.resolveSingle(criteriaSet);
		Validate.notNull(signingCredential);
		
	}

}
