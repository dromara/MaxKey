package org.maxkey.authz.saml20.provider.endpoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authz.saml20.binding.ExtractBindingAdapter;
import org.maxkey.authz.saml20.xml.SAML2ValidatorSuite;
import org.maxkey.web.WebContext;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.saml.saml2.core.LogoutRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutSamlEndpoint {
    private final static Logger logger = LoggerFactory.getLogger(LogoutSamlEndpoint.class);
    
    @Autowired
    @Qualifier("extractRedirectBindingAdapter")
    private ExtractBindingAdapter extractRedirectBindingAdapter;
    
    @Autowired
    @Qualifier("samlValidaotrSuite")
    private SAML2ValidatorSuite validatorSuite;
    
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/logout/saml", method=RequestMethod.GET)
    public ModelAndView samlRedirectLogout(
                HttpServletRequest request,
                HttpServletResponse response)throws Exception {
             MessageContext messageContext;
             logger.debug("extract SAML Message .");
             try {
                 
                 messageContext = extractRedirectBindingAdapter.extractSAMLMessageContext(request);
                 logger.debug("validate SAML LogoutRequest .");
                 LogoutRequest logoutRequest = (LogoutRequest) messageContext.getMessage();
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
             }catch (Exception ve) {
                 logger.warn("logoutRequest Message failed Validation", ve);
             }
             
             return WebContext.redirect("/login");
        }
       
}
