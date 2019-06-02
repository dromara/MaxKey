/**
 * 
 */
package org.maxkey.authz.cas.endpoint;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.maxkey.authz.cas.endpoint.ticket.ServiceTicketImpl;
import org.maxkey.authz.cas.endpoint.ticket.service.TicketServices;
import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.config.ApplicationConfig;
import org.maxkey.dao.service.CasDetailsService;
import org.maxkey.domain.apps.CasDetails;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Crystal.Sea
 * https://apereo.github.io/cas/5.0.x/protocol/CAS-Protocol-V2-Specification.html
 */
@Controller
public class CasAuthorizeEndpoint  extends AuthorizeBaseEndpoint{

	final static Logger _logger = LoggerFactory.getLogger(CasAuthorizeEndpoint.class);

	@Autowired
	CasDetailsService casDetailsService;
	
	@Autowired
	ApplicationConfig applicationConfig;
	
	@Autowired
	@Qualifier("casTicketServices")
	TicketServices ticketServices;
	
	@RequestMapping("/authz/cas/login")
	public ModelAndView casLogin(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value=CasConstants.PARAMETER.SERVICE,required=false) String casService){
		
		CasDetails casDetails=new CasDetails();
		casDetails.setService(casService);
		
		List<CasDetails> casDetailsList=casDetailsService.query(casDetails);
		
		casDetails=(casDetailsList!=null && casDetailsList.size()==1)?casDetailsList.get(0):null;
		
		return buildCasModelAndView(casDetails);
		
	}
	
	@RequestMapping("/authz/cas/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("id") String id){
		
		CasDetails casDetails=casDetailsService.get(id);
		
		return buildCasModelAndView(casDetails);
	}
	
	private  ModelAndView buildCasModelAndView(CasDetails casDetails){
		
		_logger.debug(""+casDetails);

		WebContext.setAttribute(CasConstants.PARAMETER.ENDPOINT_CAS_DETAILS, casDetails);
		WebContext.setAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID, casDetails.getId());
		return WebContext.redirect("/authz/cas/granting");
	}
	
	@RequestMapping("/authz/cas/granting")
	public ModelAndView grantingTicket(
			HttpServletRequest request,
			HttpServletResponse response){
		
		CasDetails casDetails=(CasDetails)WebContext.getAttribute(CasConstants.PARAMETER.ENDPOINT_CAS_DETAILS);
		ServiceTicketImpl serviceTicket=new ServiceTicketImpl(WebContext.getAuthentication(),casDetails);
		
		String ticket=ticketServices.createTicket(serviceTicket);
		
		return WebContext.redirect(casDetails.getService()+"?"+CasConstants.PARAMETER.TICKET+"="+ticket);
	}
}
