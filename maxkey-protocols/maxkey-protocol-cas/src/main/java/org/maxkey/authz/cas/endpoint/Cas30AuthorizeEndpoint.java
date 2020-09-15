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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authn.BasicAuthentication;
import org.maxkey.authz.cas.endpoint.response.ServiceResponseBuilder;
import org.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.maxkey.authz.cas.endpoint.ticket.Ticket;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.constants.Boolean;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Crystal.Sea
 * https://apereo.github.io/cas/6.2.x/protocol/CAS-Protocol-Specification.html
 */
@Controller
public class Cas30AuthorizeEndpoint  extends CasBaseAuthorizeEndpoint{

	final static Logger _logger = LoggerFactory.getLogger(Cas30AuthorizeEndpoint.class);

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
		
	    setContentType(request,response,format);
	    
		Ticket storedTicket=null;
		try {
			storedTicket = ticketServices.consumeTicket(ticket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServiceResponseBuilder serviceResponseBuilder=new ServiceResponseBuilder();
		
		if(storedTicket!=null){
			String principal=((BasicAuthentication)storedTicket.getAuthentication().getPrincipal()).getUsername();
			serviceResponseBuilder.success().setUser(principal);
			
			if(Boolean.isTrue(storedTicket.getCasDetails().getIsAdapter())){
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
		
	    setContentType(request,response,format);
	    		
		Ticket storedTicket=null;
		try {
			storedTicket = ticketServices.consumeTicket(ticket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServiceResponseBuilder serviceResponseBuilder=new ServiceResponseBuilder();
		
		if(storedTicket!=null){
			String principal=((BasicAuthentication)storedTicket.getAuthentication().getPrincipal()).getUsername();
			serviceResponseBuilder.success().setUser(principal);
			
			if(Boolean.isTrue(storedTicket.getCasDetails().getIsAdapter())){
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
