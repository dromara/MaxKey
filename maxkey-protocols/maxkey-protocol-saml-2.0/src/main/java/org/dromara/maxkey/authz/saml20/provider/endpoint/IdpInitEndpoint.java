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
import org.dromara.maxkey.crypto.keystore.KeyStoreLoader;
import org.dromara.maxkey.crypto.keystore.KeyStoreUtil;
import org.dromara.maxkey.entity.apps.AppsSAML20Details;
import org.dromara.maxkey.persistence.service.AppsSaml20DetailsService;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
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

/**
 * idp init  not need extract SAML request message
 * AuthnRequestInfo use default init
 * @author Crystal.Sea
 *
 */
@Tag(name = "2-2-SAML v2.0 API文档模块")
@Controller
public class IdpInitEndpoint {
	private static final  Logger logger = LoggerFactory.getLogger(IdpInitEndpoint.class);

	private BindingAdapter bindingAdapter;
	
	@Autowired
	@Qualifier("postSimpleSignBindingAdapter")
	private BindingAdapter postSimpleSignBindingAdapter;
	
	@Autowired
	@Qualifier("postBindingAdapter")
	private BindingAdapter postBindingAdapter;
	
	@Autowired
	@Qualifier("extractRedirectBindingAdapter")
	private ExtractBindingAdapter extractRedirectBindingAdapter;

	@Autowired
	@Qualifier("keyStoreLoader")
	private KeyStoreLoader keyStoreLoader;

	@Autowired
	private AppsSaml20DetailsService saml20DetailsService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param appId
	 * @return
	 * @throws Exception
	 * 
	 *
	 */
	@Operation(summary = "SAML 2.0 IDP Init接口", description = "传递参数应用ID",method="GET")
	@RequestMapping(value = "/authz/saml20/idpinit/{appid}", method=RequestMethod.GET)
	public ModelAndView authorizeIdpInit(
				HttpServletRequest request,
				HttpServletResponse response,
				@PathVariable("appid") String appId)throws Exception {
		logger.debug("SAML IDP init , app id is "+appId);
		AppsSAML20Details saml20Details = saml20DetailsService.getAppDetails(appId , true);
		WebContext.setAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP, saml20Details);
		if (saml20Details == null) {
			logger.error("samlId[" + appId + "] Error .");
			throw new Exception();
		}

		KeyStore trustKeyStore = KeyStoreUtil.bytes2KeyStore(saml20Details.getKeyStore(),
				keyStoreLoader.getKeyStore().getType(),
				keyStoreLoader.getKeystorePassword());

		extractRedirectBindingAdapter.setSaml20Detail(saml20Details);
		extractRedirectBindingAdapter.buildSecurityPolicyResolver(trustKeyStore);
		
		String binding=saml20Details.getBinding();
		
		if(binding.endsWith("PostSimpleSign")){
			bindingAdapter=postSimpleSignBindingAdapter;
		}else{
			bindingAdapter=postBindingAdapter;
		}
		
		//AuthnRequestInfo init authnRequestID to null
		bindingAdapter.setAuthnRequestInfo(new AuthnRequestInfo());

		bindingAdapter.setExtractBindingAdapter(extractRedirectBindingAdapter);
		
		request.getSession().setAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP_SAMLV20_ADAPTER, bindingAdapter);

		logger.debug("idp init forwarding to assertion :","/authz/saml20/assertion");

		return WebContext.forward("/authz/saml20/assertion");
	}


	/**
	 * @param keyStoreLoader
	 *            the keyStoreLoader to set
	 */
	public void setKeyStoreLoader(KeyStoreLoader keyStoreLoader) {
		this.keyStoreLoader = keyStoreLoader;
	}

}
