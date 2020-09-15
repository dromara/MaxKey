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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.maxkey.authz.cas.endpoint.ticket.ServiceTicketImpl;
import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.domain.apps.AppsCasDetails;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Crystal.Sea
 * https://apereo.github.io/cas/6.2.x/protocol/CAS-Protocol.html
 */
@Controller
public class CasAuthorizeEndpoint  extends CasBaseAuthorizeEndpoint{

	final static Logger _logger = LoggerFactory.getLogger(CasAuthorizeEndpoint.class);
	
	@RequestMapping("/authz/cas/login")
	public ModelAndView casLogin(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value=CasConstants.PARAMETER.SERVICE,required=false) String casService){
	    
		AppsCasDetails  casDetails=casDetailsService.getAppDetails(casService);
		
		return buildCasModelAndView(request,response,casDetails);
		
	}
	
	@RequestMapping("/authz/cas/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("id") String id){
		
		AppsCasDetails casDetails=casDetailsService.getAppDetails(id);
		
		return buildCasModelAndView(request,response,casDetails);
	}
	
	private  ModelAndView buildCasModelAndView(
	                HttpServletRequest request,
	                HttpServletResponse response,
	                AppsCasDetails casDetails){
		
		_logger.debug(""+casDetails);

		WebContext.setAttribute(
    		        CasConstants.PARAMETER.PARAMETER_MAP, 
    		        WebContext.getRequestParameterMap(request)
		        );
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
		
		//append ticket
		callbackUrl.append(CasConstants.PARAMETER.TICKET).append("=").append(ticket);
		
		callbackUrl.append("&");
		//append service
		callbackUrl.append(CasConstants.PARAMETER.SERVICE).append("=").append(casDetails.getService());
		
		//增加可自定义的参数
		if(WebContext.getAttribute(CasConstants.PARAMETER.PARAMETER_MAP)!=null) {
    		@SuppressWarnings("unchecked")
            Map <String, String> parameterMap = (Map <String, String>)WebContext.getAttribute(CasConstants.PARAMETER.PARAMETER_MAP);
    		parameterMap.remove(CasConstants.PARAMETER.TICKET);
    		parameterMap.remove(CasConstants.PARAMETER.SERVICE);
    		for (String key : parameterMap.keySet()) {
    		    callbackUrl.append("&").append(key).append(parameterMap.get(key));
    		}
		}
		
		_logger.debug("redirect to CAS Client URL " + callbackUrl);
		
		return WebContext.redirect(callbackUrl.toString());
	}
}
