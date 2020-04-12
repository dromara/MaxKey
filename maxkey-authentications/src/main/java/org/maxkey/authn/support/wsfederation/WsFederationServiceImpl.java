package org.maxkey.authn.support.wsfederation;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.constants.ConstantsLoginType;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.opensaml.saml1.core.impl.AssertionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WsFederationServiceImpl implements   WsFederationService{
	final static Logger _logger = LoggerFactory.getLogger(WsFederationServiceImpl.class);
	
	private WsFederationConfiguration wsFederationConfiguration;
	
	public boolean login(String wsFederationWA,String wsFederationWResult,HttpServletRequest request){
		// it's an authentication
        if (StringUtils.isNotEmpty(wsFederationWA) && wsFederationWA.equalsIgnoreCase(WsFederationConstants.WSIGNIN)) {
            _logger.debug("wresult : {}"+wsFederationWResult);

            final String wctx = request.getParameter(WsFederationConstants.WCTX);
            _logger.debug("wctx : {}"+ wctx);

            // create credentials
            final AssertionImpl assertion = WsFederationUtils.parseTokenFromString(wsFederationWResult);
            //Validate the signature
            if (assertion != null && WsFederationUtils.validateSignature(assertion, wsFederationConfiguration.getSigningCertificates())) {
                final WsFederationCredential wsFederationCredential = WsFederationUtils.createCredentialFromToken(assertion);

                if (wsFederationCredential != null && wsFederationCredential.isValid(wsFederationConfiguration.getRelyingParty(),
                		wsFederationConfiguration.getIdentifier(),
                		wsFederationConfiguration.getTolerance())) {

                    //Give the library user a chance to change the attributes as necessary
                    if (wsFederationConfiguration.getAttributeMutator() != null) {
                    	wsFederationConfiguration.getAttributeMutator().modifyAttributes(
                    			wsFederationCredential.getAttributes(),
                    			wsFederationConfiguration.getUpnSuffix());
                    }

                    return WebContext.setAuthentication(
                    		wsFederationCredential.getAttributes().get("").toString(),
                    		ConstantsLoginType.WSFEDERATION,
                    		"","","success");

                } else {
                    _logger.warn("SAML assertions are blank or no longer valid.");
                    return false;
                }
            } else {
                _logger.error("WS Requested Security Token is blank or the signature is not valid.");
                return false;
            }
        }
		return false;
	}

	public void setWsFederationConfiguration(
			WsFederationConfiguration wsFederationConfiguration) {
		this.wsFederationConfiguration = wsFederationConfiguration;
	}
	
}
