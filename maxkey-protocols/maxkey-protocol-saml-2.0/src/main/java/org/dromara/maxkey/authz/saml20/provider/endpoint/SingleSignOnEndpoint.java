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
 

package org.dromara.maxkey.authz.saml20.provider.endpoint;

import java.security.KeyStore;

import org.dromara.maxkey.authz.saml.common.AuthnRequestInfo;
import org.dromara.maxkey.authz.saml20.binding.BindingAdapter;
import org.dromara.maxkey.authz.saml20.binding.ExtractBindingAdapter;
import org.dromara.maxkey.authz.saml20.xml.SAML2ValidatorSuite;
import org.dromara.maxkey.crypto.keystore.KeyStoreUtil;
import org.dromara.maxkey.entity.apps.AppsSAML20Details;
import org.dromara.maxkey.persistence.service.AppsSaml20DetailsService;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "2-2-SAML v2.0 API文档模块")
@Controller
public class SingleSignOnEndpoint {
	private static final  Logger logger = LoggerFactory.getLogger(SingleSignOnEndpoint.class);

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

	@Operation(summary = "SAML 2.0 SP Init接收接口", description = "传递参数应用ID",method="POST")
	@RequestMapping(value = "/authz/saml20/{appid}", method=RequestMethod.POST)
	public ModelAndView authorizePost(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("appid") String appId)throws Exception {
		logger.debug("SAML Authorize Redirect do POST , app id is  "+appId);
		return extractSAMLRequest(extractPostBindingAdapter,appId,request);
	}
	
	@Operation(summary = "SAML 2.0 SP Init接收接口", description = "传递参数应用ID",method="GET")
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
		
		request.getSession().setAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP_SAMLV20_ADAPTER, bindingAdapter);
		
		return WebContext.forward("/authz/saml20/assertion");
	}

	public void extractSaml20Detail(ExtractBindingAdapter extractBindingAdapter,String samlId) throws Exception{
		AppsSAML20Details  saml20Details  = saml20DetailsService.getAppDetails(samlId , true);
		WebContext.setAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP, saml20Details);
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
			logger.debug("validate SAML AuthnRequest .");
	        AuthnRequest authnRequest = (AuthnRequest) messageContext.getInboundSAMLMessage();
	        logger.debug("AuthnRequest ProtocolBinding "+authnRequest.getProtocolBinding());
	        logger.debug("InboundSAMLMessage Id "+messageContext.getInboundSAMLMessageId());
	        logger.debug("AuthnRequest AssertionConsumerServiceURL "+authnRequest.getAssertionConsumerServiceURL());
	        logger.debug("InboundMessage Issuer "+messageContext.getInboundMessageIssuer());
	        logger.debug("InboundSAMLMessage IssueInstant "+messageContext.getInboundSAMLMessageIssueInstant());
	        logger.debug("InboundSAMLMessage RelayState "+messageContext.getRelayState());      
	        logger.debug("AuthnRequest isPassive "+authnRequest.isPassive());
	        logger.debug("AuthnRequest ForceAuthn "+authnRequest.isForceAuthn());
	        
	        validatorSuite.validate(authnRequest);
	        

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
	        
		} catch (MessageDecodingException e1) {
			logger.error("Exception decoding SAML MessageDecodingException", e1);
			throw new Exception(e1);
		} catch (SecurityException e1) {
			logger.error("Exception decoding SAML SecurityException", e1);
			throw new Exception(e1);
		}catch (ValidationException ve) {
            logger.warn("AuthnRequest Message failed Validation", ve);
            throw new Exception(ve);
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
