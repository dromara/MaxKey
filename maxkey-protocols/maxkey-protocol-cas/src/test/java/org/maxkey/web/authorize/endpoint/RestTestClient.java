package org.maxkey.web.authorize.endpoint;

import org.pac4j.cas.profile.CasRestProfile;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.credentials.authenticator.CasRestAuthenticator;
import org.pac4j.cas.profile.CasProfile;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.exception.HttpAction;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Map;
import java.util.Set;
//https://apereo.github.io/cas/6.0.x/protocol/REST-Protocol.html

public class RestTestClient {

    public static void main(String[] args ) throws HttpAction {
        final String casUrlPrefix = "http://sso.maxkey.top/maxkey/authz/cas/";
        String username ="admin";
        String password ="maxkey";
        String serviceUrl = "http://cas.demo.maxkey.top:8080/demo-cas/";
        CasConfiguration casConfiguration = new CasConfiguration(casUrlPrefix);
        final CasRestAuthenticator authenticator = new CasRestAuthenticator(casConfiguration);
        final CasRestFormClient client = new CasRestFormClient(casConfiguration,"username","password");
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();

        final WebContext webContext = new J2EContext(request, response);
        casConfiguration.init();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username,password);
        CasRestAuthenticator restAuthenticator = new CasRestAuthenticator(casConfiguration);
        // authenticate with credentials (validate credentials)
        restAuthenticator.validate(credentials, webContext);
        final CasRestProfile profile = (CasRestProfile) credentials.getUserProfile();
        // get service ticket
        final TokenCredentials casCredentials = client.requestServiceTicket(serviceUrl, profile, webContext);
        // validate service ticket
        final CasProfile casProfile = client.validateServiceTicket(serviceUrl, casCredentials, webContext);
        
        Map<String,Object> attributes = casProfile.getAttributes();
        Set<Map.Entry<String,Object>> mapEntries = attributes.entrySet();
        for (Map.Entry entry : mapEntries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        //client.destroyTicketGrantingTicket(profile,webContext);
    }
}
