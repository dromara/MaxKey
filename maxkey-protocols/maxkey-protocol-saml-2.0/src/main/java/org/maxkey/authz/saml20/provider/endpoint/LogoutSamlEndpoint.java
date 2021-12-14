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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authz.saml20.binding.ExtractBindingAdapter;
import org.maxkey.authz.saml20.xml.SAML2ValidatorSuite;
import org.maxkey.web.WebContext;
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

@Tag(name = "2-2-SAML v2.0 API文档模块")
@Controller
public class LogoutSamlEndpoint {
    private final static Logger logger = LoggerFactory.getLogger(LogoutSamlEndpoint.class);
    
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
                 return WebContext.redirect("/logout");
                 
             } catch (MessageDecodingException e1) {
                 logger.error("Exception decoding SAML MessageDecodingException", e1);
             } catch (SecurityException e1) {
                 logger.error("Exception decoding SAML SecurityException", e1);
             }catch (ValidationException ve) {
                 logger.warn("logoutRequest Message failed Validation", ve);
             }
             
             return WebContext.redirect("/login");
        }
       
}
