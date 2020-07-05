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
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.domain.apps.AppsCasDetails;
import org.maxkey.persistence.service.AppsCasDetailsService;
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
	AppsCasDetailsService casDetailsService;
	
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
		
		AppsCasDetails casDetails=new AppsCasDetails();
		casDetails.setService(casService);
		
		List<AppsCasDetails> casDetailsList=casDetailsService.query(casDetails);
		
		casDetails=(casDetailsList!=null && casDetailsList.size()==1)?casDetailsList.get(0):null;
		
		return buildCasModelAndView(casDetails);
		
	}
	
	@RequestMapping("/authz/cas/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("id") String id){
		
		AppsCasDetails casDetails=casDetailsService.getAppDetails(id);
		
		return buildCasModelAndView(casDetails);
	}
	
	private  ModelAndView buildCasModelAndView(AppsCasDetails casDetails){
		
		_logger.debug(""+casDetails);

		WebContext.setAttribute(CasConstants.PARAMETER.ENDPOINT_CAS_DETAILS, casDetails);
		WebContext.setAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID, casDetails.getId());
		WebContext.setAttribute(AuthorizeBaseEndpoint.class.getName(),casDetails);
		return WebContext.redirect("/authz/cas/granting");
	}
	
	@RequestMapping("/authz/cas/granting")
	public ModelAndView grantingTicket(
			HttpServletRequest request,
			HttpServletResponse response){
		
		AppsCasDetails casDetails=(AppsCasDetails)WebContext.getAttribute(CasConstants.PARAMETER.ENDPOINT_CAS_DETAILS);
		ServiceTicketImpl serviceTicket=new ServiceTicketImpl(WebContext.getAuthentication(),casDetails);
		
		String ticket=ticketServices.createTicket(serviceTicket);
		
		StringBuffer callbackUrl = new StringBuffer(casDetails.getCallbackUrl());
		if(casDetails.getCallbackUrl().indexOf("?")==-1) {
		    callbackUrl.append("?");
		}
		
		callbackUrl.append(CasConstants.PARAMETER.TICKET).append("=").append(ticket)
                .append("&")
                .append(CasConstants.PARAMETER.SERVICE).append("=").append(casDetails.getService());
		
		_logger.debug("redirect to CAS Client URL " + callbackUrl);
		
		return WebContext.redirect(callbackUrl.toString());
	}
}
