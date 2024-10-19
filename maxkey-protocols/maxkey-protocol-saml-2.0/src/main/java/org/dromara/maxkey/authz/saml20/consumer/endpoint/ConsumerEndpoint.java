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
 

package org.dromara.maxkey.authz.saml20.consumer.endpoint;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.authz.saml.common.EndpointGenerator;
import org.dromara.maxkey.authz.saml.common.TrustResolver;
import org.dromara.maxkey.authz.saml.service.IDService;
import org.dromara.maxkey.authz.saml.service.TimeService;
import org.dromara.maxkey.authz.saml20.binding.BindingAdapter;
import org.dromara.maxkey.authz.saml20.binding.ExtractBindingAdapter;
import org.dromara.maxkey.authz.saml20.consumer.AuthnRequestGenerator;
import org.dromara.maxkey.authz.saml20.consumer.spring.IdentityProviderAuthenticationException;
import org.dromara.maxkey.authz.saml20.consumer.spring.ServiceProviderAuthenticationException;
import org.dromara.maxkey.authz.saml20.provider.xml.AuthnResponseGenerator;
import org.dromara.maxkey.authz.saml20.xml.SAML2ValidatorSuite;
import org.dromara.maxkey.constants.ConstsLoginType;
import org.dromara.maxkey.crypto.keystore.KeyStoreLoader;
import org.dromara.maxkey.entity.apps.AppsSAML20Details;
import org.dromara.maxkey.persistence.service.AppsSaml20DetailsService;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.binding.security.IssueInstantRule;
import org.opensaml.common.binding.security.MessageReplayRule;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.credential.KeyStoreCredentialResolver;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.opensaml.xml.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class ConsumerEndpoint {

	private static final  Logger logger = LoggerFactory.getLogger(ConsumerEndpoint.class);

	private BindingAdapter bindingAdapter;
	
	@Autowired
	@Qualifier("serviceProviderKeyStoreLoader")
	private KeyStoreLoader keyStoreLoader;

	@Autowired
	@Qualifier("timeService")
	private TimeService timeService;

	@Autowired
	@Qualifier("idService")
	private IDService idService;
	
	@Autowired
    @Qualifier("authenticationProvider")
	AbstractAuthenticationProvider authenticationProvider ;

	private String singleSignOnServiceURL;
	private String assertionConsumerServiceURL;
	
	@Autowired
	@Qualifier("extractRedirectBindingAdapter")
	private ExtractBindingAdapter extractBindingAdapter;

	@Autowired
	private AppsSaml20DetailsService saml20DetailsService;

	@Autowired
	@Qualifier("issueInstantRule")
	private IssueInstantRule issueInstantRule;

	@Autowired
	@Qualifier("messageReplayRule")
	private MessageReplayRule messageReplayRule;
	
	@Autowired
	AuthTokenService authJwtService;

	EndpointGenerator endpointGenerator;
	AuthnRequestGenerator authnRequestGenerator;
	CredentialResolver credentialResolver;

	Credential signingCredential;
	
	SAML2ValidatorSuite validatorSuite = new SAML2ValidatorSuite();

	@RequestMapping(value = "/authz/saml20/consumer/{id}")
	public ModelAndView consumer(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("id") String appId)
			throws Exception {

		logger.debug("Attempting authentication.");
		// 初始化SP 证书
		initCredential(appId);

		SAMLMessageContext messageContext=null;
		/*
		try {
			messageContext = bindingAdapter.extractSAMLMessageContext(request);
		} catch (MessageDecodingException me) {
			logger.error("Could not decode SAML Response", me);
			throw new Exception(me);
		} catch (SecurityException se) {
			logger.error("Could not decode SAML Response", se);
			throw new Exception(se);
		}*/

		logger.debug("Message received from issuer: "
				+ messageContext.getInboundMessageIssuer());

		if (!(messageContext.getInboundSAMLMessage() instanceof Response)) {
			logger.error("SAML Message was not a Response");
			throw new Exception();
		}
		List<Assertion> assertionList = ((Response) messageContext
				.getInboundSAMLMessage()).getAssertions();



		String credentials = extractBindingAdapter.extractSAMLMessage(request);

		// 未认证token
		Response samlResponse=(Response) messageContext.getInboundSAMLMessage();
		
		AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	

		try {
			validatorSuite.validate(samlResponse);
		} catch (ValidationException ve) {
			logger.warn("Response Message failed Validation", ve);
			throw new ServiceProviderAuthenticationException("Invalid SAML REsponse Message", ve);
		}

		
		checkResponseStatus(samlResponse);

		Assertion assertion = samlResponse.getAssertions().get(0);
		
		logger.debug("authenticationResponseIssuingEntityName {}" ,samlResponse.getIssuer().getValue()); 
		
		String username=assertion.getSubject().getNameID().getValue();
		
		logger.debug("assertion.getID() " ,assertion.getID());
		logger.debug("assertion.getSubject().getNameID().getValue() ", username);
		
	
		logger.debug("assertion.getID() ", assertion.getAuthnStatements());
		LoginCredential loginCredential =new LoginCredential(
		        username,"",ConstsLoginType.SAMLTRUST);
		
		Authentication  authentication = authenticationProvider.authenticate(loginCredential,true);
		if(authentication == null) {
			String congress = authJwtService.createCongress(authentication);
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("username", username);
		mav.setViewName("redirect:/appList");
		return mav;
	}



	public void afterPropertiesSet() throws Exception {

		authnRequestGenerator = new AuthnRequestGenerator(keyStoreLoader.getEntityName(), timeService, idService);
		endpointGenerator = new EndpointGenerator();

		CriteriaSet criteriaSet = new CriteriaSet();
		criteriaSet.add(new EntityIDCriteria(keyStoreLoader.getEntityName()));
		criteriaSet.add(new UsageCriteria(UsageType.SIGNING));

		try {
			signingCredential = credentialResolver.resolveSingle(criteriaSet);
		} catch (SecurityException e) {
			logger.error("证书解析出错", e);
			throw new Exception(e);
		}
		Validate.notNull(signingCredential);

	}

	/**
	 * 初始化sp证书
	 * 
	 * @throws Exception
	 */
	private void initCredential(String appId) throws Exception {
		// 1. 获取 sp keyStore
		AppsSAML20Details saml20Details = saml20DetailsService.get(appId);
		if (saml20Details == null) {
			logger.error("appId[" + appId + "] not exists");
			throw new Exception();
		}
		byte[] keyStoreBytes = saml20Details.getKeyStore();
		InputStream keyStoreStream = new ByteArrayInputStream(keyStoreBytes);

		try {
			KeyStore keyStore = KeyStore.getInstance(keyStoreLoader.getKeystoreType());
			keyStore.load(keyStoreStream, keyStoreLoader.getKeystorePassword().toCharArray());

			Map<String, String> passwords = new HashMap<String, String>();
			for (Enumeration<String> en = keyStore.aliases(); en.hasMoreElements();) {
				String aliase = en.nextElement();
				if (aliase.equalsIgnoreCase(keyStoreLoader.getEntityName())) {
					passwords.put(aliase, keyStoreLoader.getKeystorePassword());
				}
			}
			// TrustResolver trustResolver = new
			// TrustResolver(keyStore,keyStoreLoader.getIdpIssuingEntityName(),keyStoreLoader.getKeystorePassword());

			AuthnResponseGenerator authnResponseGenerator = new AuthnResponseGenerator(
					keyStoreLoader.getEntityName(), timeService,
					idService);
			// endpointGenerator = new EndpointGenerator();

			CriteriaSet criteriaSet = new CriteriaSet();
			criteriaSet.add(new EntityIDCriteria(keyStoreLoader
					.getEntityName()));
			criteriaSet.add(new UsageCriteria(UsageType.SIGNING));

			KeyStoreCredentialResolver credentialResolver = new KeyStoreCredentialResolver(
					keyStore, passwords);
			signingCredential = credentialResolver.resolveSingle(criteriaSet);
			Validate.notNull(signingCredential);

			// adapter set resolver
			TrustResolver trustResolver = new TrustResolver(keyStore,
					keyStoreLoader.getEntityName(),
					keyStoreLoader.getKeystorePassword(), issueInstantRule,
					messageReplayRule,"POST");
			extractBindingAdapter.setSecurityPolicyResolver(trustResolver
					.getStaticSecurityPolicyResolver());
		} catch (Exception e) {
			logger.error("初始化sp证书出错");
			throw new Exception(e);
		}
	}
	
	
	private void checkResponseStatus(Response samlResponse) {

		
		if(StatusCode.SUCCESS_URI.equals( StringUtils.trim(samlResponse.getStatus().getStatusCode().getValue()))) {
			
			additionalValidationChecksOnSuccessfulResponse(samlResponse);
			
		}
		
		
		else {
			
			StringBuilder extraInformation = extractExtraInformation(samlResponse);
			
			if(extraInformation.length() > 0) {
				logger.warn("Extra information extracted from authentication failure was {}", extraInformation.toString());
				
				throw new IdentityProviderAuthenticationException("Identity Provider has failed the authentication.", extraInformation.toString());
			}
			
			else {
				throw new IdentityProviderAuthenticationException("Identity Provider has failed the authentication.");
			}
			
		}
	}
	
	
	private void additionalValidationChecksOnSuccessfulResponse(
			Response samlResponse) {
		//saml validator suite does not check for assertions on successful auths
		if(samlResponse.getAssertions().isEmpty()){
			throw new ServiceProviderAuthenticationException("Successful Response did not contain any assertions");
		}
		
		//nor authnStatements
		else if(samlResponse.getAssertions().get(0).getAuthnStatements().isEmpty()){
			throw new ServiceProviderAuthenticationException("Successful Response did not contain an assertions with an AuthnStatement");
		}

		//we require at attribute statements
		else if(samlResponse.getAssertions().get(0).getAttributeStatements().isEmpty()){
			throw new ServiceProviderAuthenticationException("Successful Response did not contain an assertions with an AttributeStatements");

		}
		//we will require an issuer
		else if(samlResponse.getIssuer() == null) {
			throw new ServiceProviderAuthenticationException("Successful Response did not contain any Issuer");

		}
	}

	private StringBuilder extractExtraInformation(Response samlResponse) {
		StringBuilder extraInformation = new StringBuilder();
		
		if( samlResponse.getStatus().getStatusCode().getStatusCode() !=null ) {
		
			extraInformation.append(samlResponse.getStatus().getStatusCode().getStatusCode().getValue());
		}
		
		if(samlResponse.getStatus().getStatusMessage() != null) {
			if(extraInformation.length() > 0) {
				extraInformation.append("  -  ");
			}
			extraInformation.append(samlResponse.getStatus().getStatusMessage());
		}
		return extraInformation;
	}
}
