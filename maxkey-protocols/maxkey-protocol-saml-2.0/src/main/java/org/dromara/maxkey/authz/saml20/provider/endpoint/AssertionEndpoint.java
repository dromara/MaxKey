/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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

import java.util.HashMap;


import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.authz.saml.common.AuthnRequestInfo;
import org.dromara.maxkey.authz.saml.common.EndpointGenerator;
import org.dromara.maxkey.authz.saml20.binding.BindingAdapter;
import org.dromara.maxkey.authz.saml20.provider.xml.AuthnResponseGenerator;
import org.dromara.maxkey.entity.apps.AppsSAML20Details;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.web.WebConstants;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class AssertionEndpoint {
	private static final  Logger logger = LoggerFactory.getLogger(AssertionEndpoint.class);
	
	private BindingAdapter bindingAdapter;

	@Autowired
	@Qualifier("endpointGenerator")
	EndpointGenerator endpointGenerator;
	
	@Autowired
	@Qualifier("authnResponseGenerator")
	AuthnResponseGenerator authnResponseGenerator;

	@RequestMapping(value = "/authz/saml20/assertion")
	public ModelAndView assertion(
			HttpServletRequest request,
			HttpServletResponse response,
			@CurrentUser UserInfo currentUser) throws Exception {
		logger.debug("saml20 assertion start.");
		bindingAdapter = (BindingAdapter) request.getSession().getAttribute(
		        WebConstants.AUTHORIZE_SIGN_ON_APP_SAMLV20_ADAPTER);
		logger.debug("saml20 assertion get session samlv20Adapter {}",bindingAdapter);
		AppsSAML20Details saml20Details = bindingAdapter.getSaml20Details();
		logger.debug("saml20Details {}",saml20Details.getExtendAttr());
		AuthnRequestInfo authnRequestInfo = bindingAdapter.getAuthnRequestInfo();
		
		if (authnRequestInfo == null) {
			logger.warn("Could not find AuthnRequest on the request.  Responding with SC_FORBIDDEN.");
			throw new Exception();
		}

		logger.debug("AuthnRequestInfo: {}", authnRequestInfo);
		HashMap <String,String>attributeMap=new HashMap<>();
		attributeMap.put(WebConstants.ONLINE_TICKET_NAME, AuthorizationUtils.getPrincipal().getSessionId());
		
		//saml20Details
		Response authResponse = authnResponseGenerator.generateAuthnResponse(
				saml20Details,
				authnRequestInfo,
				attributeMap,
				bindingAdapter,
				currentUser);
		
		Endpoint endpoint = endpointGenerator.generateEndpoint(saml20Details.getSpAcsUrl());

		request.getSession().removeAttribute(AuthnRequestInfo.class.getName());

		// we could use a different adapter to send the response based on
		// request issuer...
		try {
			bindingAdapter.sendSAMLMessage(authResponse, endpoint, request,response);
		} catch (MessageEncodingException mee) {
			logger.error("Exception encoding SAML message", mee);
			throw new Exception(mee);
		}
		return null;
	}
	
	

}
