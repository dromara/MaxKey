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
 

package org.maxkey.authz.saml20.provider.endpoint;

import java.security.KeyStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authz.saml.common.AuthnRequestInfo;
import org.maxkey.authz.saml20.binding.BindingAdapter;
import org.maxkey.authz.saml20.binding.ExtractBindingAdapter;
import org.maxkey.authz.saml20.xml.SAML2ValidatorSuite;
import org.maxkey.crypto.keystore.KeyStoreUtil;
import org.maxkey.domain.apps.AppsSAML20Details;
import org.maxkey.persistence.service.AppsSaml20DetailsService;
import org.maxkey.web.WebContext;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SingleSignOnEndpoint {
	private final static Logger logger = LoggerFactory.getLogger(SingleSignOnEndpoint.class);

	private BindingAdapter bindingAdapter;
	
	@Autowired
	@Qualifier("postSimpleSignBindingAdapter")
	private BindingAdapter postSimpleSignBindingAdapter;
	
	@Autowired
	@Qualifier("postBindingAdapter")
	private BindingAdapter postBindingAdapter;
	
	@Autowired
	@Qualifier("extractPostBindingAdapter")
	private ExtractBindingAdapter extractPostBindingAdapter;
	
	@Autowired
	@Qualifier("extractRedirectBindingAdapter")
	private ExtractBindingAdapter extractRedirectBindingAdapter;

	@Autowired
	@Qualifier("samlValidaotrSuite")
	private SAML2ValidatorSuite validatorSuite;

	@Autowired
	private AppsSaml20DetailsService saml20DetailsService;

	@RequestMapping(value = "/authz/saml20/{appid}", method=RequestMethod.POST)
	public ModelAndView authorizePost(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("appid") String appId)throws Exception {
		logger.debug("SAML Authorize Redirect do POST , app id is  "+appId);
		return extractSAMLRequest(extractPostBindingAdapter,appId,request);
	}
	
	@RequestMapping(value = "/authz/saml20/{appid}", method=RequestMethod.GET)
	public ModelAndView authorizeRedirect(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("appid") String appId)throws Exception {
		logger.debug("SAML Authorize Redirect do GET , app id is  "+appId);
		return extractSAMLRequest(extractRedirectBindingAdapter,appId,request);
	}
	
	public ModelAndView extractSAMLRequest(ExtractBindingAdapter extractBindingAdapter,
											String appId,
											HttpServletRequest request) throws Exception{
		logger.debug("SAML Redirect Binding , app id is "+appId);
		
		extractSaml20Detail(extractBindingAdapter,appId);

		extractSAMLMessage(extractBindingAdapter,request);
		
		request.getSession().setAttribute("samlv20Adapter", bindingAdapter);
		
		return WebContext.forward("/authz/saml20/assertion");
	}

	public void extractSaml20Detail(ExtractBindingAdapter extractBindingAdapter,String samlId) throws Exception{
		AppsSAML20Details  saml20Details  = saml20DetailsService.getAppDetails(samlId);
		
		if (saml20Details == null) {
			logger.error("Request SAML APPID [" + samlId + "] is not exist .");
			throw new Exception();
		}

		KeyStore trustKeyStore = KeyStoreUtil.bytes2KeyStore(saml20Details.getKeyStore(),
				extractBindingAdapter.getKeyStoreLoader().getKeyStore().getType(),
				extractBindingAdapter.getKeyStoreLoader().getKeystorePassword());

		extractBindingAdapter.setSaml20Detail(saml20Details);
		extractBindingAdapter.buildSecurityPolicyResolver(trustKeyStore);
	}
	
	
	@SuppressWarnings("rawtypes")
	public void extractSAMLMessage(ExtractBindingAdapter extractBindingAdapter,HttpServletRequest request) throws Exception{
		SAMLMessageContext messageContext;
		logger.debug("extract SAML Message .");
		try {
			messageContext = extractBindingAdapter.extractSAMLMessageContext(request);
		} catch (MessageDecodingException e1) {
			logger.error("Exception decoding SAML MessageDecodingException", e1);
			throw new Exception(e1);
		} catch (SecurityException e1) {
			logger.error("Exception decoding SAML SecurityException", e1);
			throw new Exception(e1);
		}
		
		logger.debug("validate SAML AuthnRequest .");
		AuthnRequest authnRequest = (AuthnRequest) messageContext.getInboundSAMLMessage();

		try {
			validatorSuite.validate(authnRequest);
		} catch (ValidationException ve) {
			logger.warn("AuthnRequest Message failed Validation", ve);
			throw new Exception(ve);
		}

		logger.debug("Select Authz  Binding.");
		String binding=extractBindingAdapter.getSaml20Detail().getBinding();
		
		if(binding.endsWith("PostSimpleSign")){
			bindingAdapter=postSimpleSignBindingAdapter;
			logger.debug("Authz POST Binding is  use PostSimpleSign .");
		}else{
			bindingAdapter=postBindingAdapter;
			logger.debug("Authz POST Binding is  use Post .");
		}
		
		
		AuthnRequestInfo authnRequestInfo = new AuthnRequestInfo(
								authnRequest.getAssertionConsumerServiceURL(),
								authnRequest.getID());
		
		logger.debug("AuthnRequest vefified.  Forwarding to AuthnResponder",authnRequestInfo);
		
		bindingAdapter.setAuthnRequestInfo(authnRequestInfo);
		
		bindingAdapter.setExtractBindingAdapter(extractBindingAdapter);
		
		String relayState=request.getParameter("RelayState");
		if (relayState != null) {
			bindingAdapter.setRelayState(relayState);
			logger.debug("RelayState : ",relayState);
		}
		
	}


	/**
	 * @param validatorSuite
	 *            the validatorSuite to set
	 */
	public void setValidatorSuite(SAML2ValidatorSuite validatorSuite) {
		this.validatorSuite = validatorSuite;
	}




}
