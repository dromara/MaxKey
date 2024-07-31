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

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authz.saml20.binding.ExtractBindingAdapter;
import org.dromara.maxkey.authz.saml20.xml.SAML2ValidatorSuite;
import org.dromara.maxkey.web.WebContext;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.LogoutRequest;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "2-2-SAML v2.0 API文档模块")
@Controller
public class LogoutSamlEndpoint {
    private static final  Logger logger = LoggerFactory.getLogger(LogoutSamlEndpoint.class);
    
    @Autowired
    @Qualifier("extractRedirectBindingAdapter")
    private ExtractBindingAdapter extractRedirectBindingAdapter;
    
    @Autowired
    @Qualifier("samlValidaotrSuite")
    private SAML2ValidatorSuite validatorSuite;
    
    @Operation(summary = "SAML单点注销地址接口", description = "",method="GET")
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/logout/saml", method=RequestMethod.GET)
    public ModelAndView samlRedirectLogout(
                HttpServletRequest request,
                HttpServletResponse response)throws Exception {
             SAMLMessageContext messageContext;
             logger.debug("extract SAML Message .");
             StringBuffer logoutUrl = new StringBuffer("/force/logout");
             try {
                 messageContext = extractRedirectBindingAdapter.extractSAMLMessageContext(request);
                 logger.debug("validate SAML LogoutRequest .");
                 LogoutRequest logoutRequest = (LogoutRequest) messageContext.getInboundSAMLMessage();
                 validatorSuite.validate(logoutRequest);
                 logger.debug("LogoutRequest ID "+logoutRequest.getID());
                 logger.debug("LogoutRequest Issuer "+logoutRequest.getIssuer());
                 logger.debug("LogoutRequest IssueInstant "+logoutRequest.getIssueInstant());
                 logger.debug("LogoutRequest Destination "+logoutRequest.getDestination());
                 logger.debug("LogoutRequest NameID "+logoutRequest.getNameID().getValue());
                 //add Destination
                 if(StringUtils.isNotBlank(logoutRequest.getDestination())) {
                	 logoutUrl.append("?").append("redirect_uri=").append(logoutRequest.getDestination());
                 }
             } catch (MessageDecodingException e1) {
                 logger.error("Exception decoding SAML MessageDecodingException", e1);
             } catch (SecurityException e1) {
                 logger.error("Exception decoding SAML SecurityException", e1);
             }catch (ValidationException ve) {
                 logger.warn("logoutRequest Message failed Validation", ve);
             }
             return WebContext.forward(logoutUrl.toString());
        }
       
}
