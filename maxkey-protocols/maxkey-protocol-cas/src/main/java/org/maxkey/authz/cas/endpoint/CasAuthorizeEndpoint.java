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

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.maxkey.authz.cas.endpoint.ticket.ServiceTicketImpl;
import org.maxkey.authz.singlelogout.LogoutType;
import org.maxkey.entity.apps.AppsCasDetails;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Crystal.Sea
 * https://apereo.github.io/cas/6.2.x/protocol/CAS-Protocol.html
 */
@Tag(name = "2-3-CAS API文档模块")
@Controller
public class CasAuthorizeEndpoint  extends CasBaseAuthorizeEndpoint{

	final static Logger _logger = LoggerFactory.getLogger(CasAuthorizeEndpoint.class);
	
	@Operation(summary = "CAS页面跳转service认证接口", description = "传递参数service",method="GET")
	@RequestMapping(CasConstants.ENDPOINT.ENDPOINT_LOGIN)
	public ModelAndView casLogin(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value=CasConstants.PARAMETER.SERVICE,required=false) String casService){
	    
		AppsCasDetails  casDetails=casDetailsService.getAppDetails(casService , true);
		
		return buildCasModelAndView(request,response,casDetails,casService);
	}
	
	@Operation(summary = "CAS页面跳转应用ID认证接口", description = "传递参数应用ID",method="GET")
	@RequestMapping(CasConstants.ENDPOINT.ENDPOINT_BASE + "/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("id") String id){
		
		AppsCasDetails casDetails=casDetailsService.getAppDetails(id , true);
		
		return buildCasModelAndView(request,response,casDetails,casDetails.getCallbackUrl());
	}
	
	private  ModelAndView buildCasModelAndView(
	                HttpServletRequest request,
	                HttpServletResponse response,
	                AppsCasDetails casDetails,
	                String casService){
		
		_logger.debug(""+casDetails);
		Map<String, String> parameterMap = WebContext.getRequestParameterMap(request);
		String service = casService;
		_logger.debug("CAS Parameter service = {}" , service);
		if(casService.indexOf("?") >-1 ) {
		    service = casService.substring(casService.indexOf("?") + 1);
		    if(service.indexOf("=") > -1) {
		        String [] parameterValues = service.split("=");
		        if(parameterValues.length == 2) {
		            parameterMap.put(parameterValues[0], parameterValues[1]);
		        }
		    }
		    _logger.debug("CAS service with Parameter : {}" , parameterMap);
		}
		WebContext.setAttribute(
    		        CasConstants.PARAMETER.PARAMETER_MAP, 
    		        parameterMap
		        );

		WebContext.setAttribute(CasConstants.PARAMETER.ENDPOINT_CAS_DETAILS, casDetails);
		WebContext.setAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID, casDetails.getId());
		WebContext.setAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP,casDetails);
		return WebContext.redirect(CasConstants.ENDPOINT.ENDPOINT_SERVICE_TICKET_GRANTING);
	}
	
	@RequestMapping(CasConstants.ENDPOINT.ENDPOINT_SERVICE_TICKET_GRANTING)
	public ModelAndView grantingTicket(Principal principal,
	        @AuthenticationPrincipal Object user,
			HttpServletRequest request,
			HttpServletResponse response){
		AppsCasDetails casDetails = (AppsCasDetails)WebContext.getAttribute(CasConstants.PARAMETER.ENDPOINT_CAS_DETAILS);
		ServiceTicketImpl serviceTicket = new ServiceTicketImpl(WebContext.getAuthentication(),casDetails);

		String ticket = ticketServices.createTicket(serviceTicket,casDetails.getExpires());
		
		StringBuffer callbackUrl = new StringBuffer(casDetails.getCallbackUrl());
		if(casDetails.getCallbackUrl().indexOf("?")==-1) {
		    callbackUrl.append("?");
		}
		
		if(callbackUrl.indexOf("&") != -1 ||callbackUrl.indexOf("=") != -1) {
		    callbackUrl.append("&");
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
    		    callbackUrl.append("&").append(key).append("=").append(parameterMap.get(key));
    		}
		}
		
		if(casDetails.getLogoutType()==LogoutType.BACK_CHANNEL) {
		    String onlineTicketId = ((SigninPrincipal)WebContext.getAuthentication().getPrincipal()).getOnlineTicket().getTicketId();
		    OnlineTicket onlineTicket  = onlineTicketServices.get(onlineTicketId);
		    //set cas ticket as OnlineTicketId
		    casDetails.setOnlineTicket(ticket);
		    onlineTicket.setAuthorizedApp(casDetails);
		    onlineTicketServices.store(onlineTicketId, onlineTicket);
		}
		
		_logger.debug("redirect to CAS Client URL {}" , callbackUrl);
		
		ModelAndView modelAndView=new ModelAndView("authorize/cas_sso_submint");
		modelAndView.addObject("callbackUrl", callbackUrl.toString());
		return modelAndView;
	}
	
	/**
	 * for cas logout then redirect to logout
	 * @param request
	 * @param response
	 * @param casService
	 * @return
	 */
	@Operation(summary = "CAS注销接口", description = "CAS注销接口",method="GET")
	@RequestMapping(CasConstants.ENDPOINT.ENDPOINT_LOGOUT)
	public ModelAndView logout(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value=CasConstants.PARAMETER.SERVICE,required=false) String casService){
		return WebContext.redirect("/logout?reLoginUrl=" + casService);
	}
}
