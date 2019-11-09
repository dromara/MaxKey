/**
 * 
 */
package org.maxkey.authz.cas.endpoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authn.BasicAuthentication;
import org.maxkey.authz.cas.endpoint.response.ServiceResponseBuilder;
import org.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.maxkey.authz.cas.endpoint.ticket.Ticket;
import org.maxkey.authz.cas.endpoint.ticket.service.TicketServices;
import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.config.ApplicationConfig;
import org.maxkey.constants.BOOLEAN;
import org.maxkey.dao.service.AppsCasDetailsService;
import org.maxkey.dao.service.UserInfoService;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Crystal.Sea
 * https://apereo.github.io/cas/5.0.x/protocol/CAS-Protocol.html
 */
@Controller
public class Cas30AuthorizeEndpoint  extends AuthorizeBaseEndpoint{

	final static Logger _logger = LoggerFactory.getLogger(Cas30AuthorizeEndpoint.class);
	@Autowired
	AppsCasDetailsService casDetailsService;
	
	@Autowired
	ApplicationConfig applicationConfig;
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("casTicketServices")
	TicketServices ticketServices;

	@RequestMapping("/authz/cas/p3/serviceValidate")
	@ResponseBody
	public String serviceValidate(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = CasConstants.PARAMETER.TICKET) String ticket,
			@RequestParam(value = CasConstants.PARAMETER.SERVICE) String service,
			@RequestParam(value = CasConstants.PARAMETER.PROXY_CALLBACK_URL,required=false) String pgtUrl,
			@RequestParam(value = CasConstants.PARAMETER.RENEW,required=false) String renew,
			@RequestParam(value = CasConstants.PARAMETER.FORMAT,required=false,defaultValue=CasConstants.FORMAT_TYPE.XML) String format){
		
		
		Ticket storedTicket=null;
		try {
			storedTicket = ticketServices.consumeTicket(ticket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServiceResponseBuilder serviceResponseBuilder=new ServiceResponseBuilder();
		
		if(storedTicket!=null){
			String principal=((BasicAuthentication)storedTicket.getAuthentication().getPrincipal()).getJ_username();
			serviceResponseBuilder.success().setUser(principal);
			
			if(BOOLEAN.isTrue(storedTicket.getCasDetails().getIsAdapter())){
				AbstractAuthorizeAdapter adapter =(AbstractAuthorizeAdapter)Instance.newInstance(storedTicket.getCasDetails().getAdapter());
				UserInfo userInfo = (UserInfo) userInfoService.loadByUsername(principal);
				adapter.generateInfo(userInfo, serviceResponseBuilder);
			}
		}else{
			serviceResponseBuilder.failure()
				.setCode(CasConstants.ERROR_CODE.INVALID_TICKET)
				.setDescription("Ticket "+ticket+" not recognized");
		}
	
		
		
		return serviceResponseBuilder.serviceResponseBuilder();
	}
	
	@RequestMapping("/authz/cas/p3/proxyValidate")
	@ResponseBody
	public String proxy(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = CasConstants.PARAMETER.TICKET) String ticket,
			@RequestParam(value = CasConstants.PARAMETER.SERVICE) String service,
			@RequestParam(value = CasConstants.PARAMETER.PROXY_CALLBACK_URL,required=false) String pgtUrl,
			@RequestParam(value = CasConstants.PARAMETER.RENEW,required=false) String renew,
			@RequestParam(value = CasConstants.PARAMETER.FORMAT,required=false,defaultValue=CasConstants.FORMAT_TYPE.XML) String format){
		
		
		Ticket storedTicket=null;
		try {
			storedTicket = ticketServices.consumeTicket(ticket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServiceResponseBuilder serviceResponseBuilder=new ServiceResponseBuilder();
		
		if(storedTicket!=null){
			String principal=((BasicAuthentication)storedTicket.getAuthentication().getPrincipal()).getJ_username();
			serviceResponseBuilder.success().setUser(principal);
			
			if(BOOLEAN.isTrue(storedTicket.getCasDetails().getIsAdapter())){
				AbstractAuthorizeAdapter adapter =(AbstractAuthorizeAdapter)Instance.newInstance(storedTicket.getCasDetails().getAdapter());
				UserInfo userInfo = (UserInfo) userInfoService.loadByUsername(principal);
				adapter.generateInfo(userInfo, serviceResponseBuilder);
			}
		}else{
			serviceResponseBuilder.failure()
				.setCode(CasConstants.ERROR_CODE.INVALID_TICKET)
				.setDescription("Ticket "+ticket+" not recognized");
		}
	
		
		
		return serviceResponseBuilder.serviceResponseBuilder();
	}
}
