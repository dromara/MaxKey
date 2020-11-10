package org.maxkey.authz.singlelogout;

import java.util.HashMap;
import java.util.UUID;

import org.maxkey.authn.SigninPrincipal;
import org.maxkey.domain.apps.Apps;
import org.maxkey.util.DateUtils;
import org.springframework.security.core.Authentication;

public class DefaultSingleLogout extends SingleLogout{

    @Override
    public void sendRequest(Authentication authentication,Apps logoutApp) {
        HashMap<String,Object> logoutParameters  = new HashMap<String,Object>();
        logoutParameters.put("id",  UUID.randomUUID().toString());
        logoutParameters.put("principal", authentication.getName());
        logoutParameters.put("request",  "logoutRequest");
        logoutParameters.put("issueInstant", DateUtils.getCurrentDateAsString(DateUtils.FORMAT_DATE_ISO_TIMESTAMP));
        logoutParameters.put("ticket",  ((SigninPrincipal)authentication.getPrincipal()).getOnlineTicket().getTicketId());
        postMessage(logoutApp.getLogoutUrl(),logoutParameters);
        
    }
    
}
