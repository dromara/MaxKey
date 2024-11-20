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
 

package org.dromara.maxkey.authz.singlelogout;

import java.util.HashMap;
import java.util.UUID;

import org.dromara.maxkey.authn.session.VisitedDto;
import org.dromara.maxkey.util.DateUtils;
import org.springframework.security.core.Authentication;

/**
 * SamlSingleLogout
 * https://apereo.github.io/cas/6.5.x/installation/Logout-Single-Signout.html
 * @author Crystal.Sea
 *
 */
public class SamlSingleLogout extends SingleLogout{

    /**
     * The parameter name that contains the logout request.
     */
    public static final String LOGOUT_REQUEST_PARAMETER = "logoutRequest";
    
    public static final String logoutRequestMessage=
            "<samlp:LogoutRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" ID=\"%s\" Version=\"2.0\" "
            + "IssueInstant=\"%s\"><saml:NameID xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">%s"
            + "</saml:NameID><samlp:SessionIndex>%s</samlp:SessionIndex></samlp:LogoutRequest>";

    @Override
    public void sendRequest(Authentication authentication,VisitedDto visited) {
        String requestMessage = String.format(logoutRequestMessage, 
                UUID.randomUUID().toString(),
                DateUtils.getCurrentDateAsString(DateUtils.FORMAT_DATE_ISO_TIMESTAMP),
                authentication.getName(),
                visited.getTicket()
                );
        
        HashMap<String,Object> logoutParameters  = new HashMap<String,Object>();
        logoutParameters.put(LOGOUT_REQUEST_PARAMETER, requestMessage);
        postMessage(visited.getLogoutUrl(),logoutParameters);
    }

    public SamlSingleLogout() {
        super();
    }
    
}
