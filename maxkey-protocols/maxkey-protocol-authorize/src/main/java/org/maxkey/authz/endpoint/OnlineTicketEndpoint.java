package org.maxkey.authz.endpoint;

import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authn.online.OnlineTicketServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value={"/onlineticket"})
public class OnlineTicketEndpoint {

    @Autowired
    @Qualifier("onlineTicketServices")
    protected OnlineTicketServices onlineTicketServices;
    
    @ResponseBody
    @RequestMapping(value="/validate") 
    public String ticketValidate(
            @RequestParam(value ="ticket",required = true) String ticket) {
        OnlineTicket onlineTicket = onlineTicketServices.get(ticket);
        return onlineTicket == null ? "" :onlineTicket.getTicketId();
    }
}
