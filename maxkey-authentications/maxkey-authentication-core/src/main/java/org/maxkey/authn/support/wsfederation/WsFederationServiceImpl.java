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
 

package org.maxkey.authn.support.wsfederation;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.constants.ConstantsLoginType;
import org.maxkey.util.StringUtils;
import org.opensaml.saml1.core.impl.AssertionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class WsFederationServiceImpl implements   WsFederationService{
	final static Logger _logger = LoggerFactory.getLogger(WsFederationServiceImpl.class);
	
	private WsFederationConfiguration wsFederationConfiguration;
	
	@Autowired
    @Qualifier("authenticationProvider")
	AbstractAuthenticationProvider authenticationProvider ;
	
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

                    authenticationProvider.trustAuthentication(
                    		wsFederationCredential.getAttributes().get("").toString(),
                    		ConstantsLoginType.WSFEDERATION,
                    		"","","success");
                    return true;
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
