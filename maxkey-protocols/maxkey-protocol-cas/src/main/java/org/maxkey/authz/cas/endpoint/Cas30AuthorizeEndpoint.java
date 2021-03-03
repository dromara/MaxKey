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

import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authz.cas.endpoint.response.ProxyServiceResponseBuilder;
import org.maxkey.authz.cas.endpoint.response.ServiceResponseBuilder;
import org.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.maxkey.authz.cas.endpoint.ticket.ProxyGrantingTicketIOUImpl;
import org.maxkey.authz.cas.endpoint.ticket.ProxyGrantingTicketImpl;
import org.maxkey.authz.cas.endpoint.ticket.ProxyTicketImpl;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Crystal.Sea
 * https://apereo.github.io/cas/6.2.x/protocol/CAS-Protocol-Specification.html
 */
@Api(tags = "CAS API文档模块")
@Controller
public class Cas30AuthorizeEndpoint  extends CasBaseAuthorizeEndpoint{

	final static Logger _logger = LoggerFactory.getLogger(Cas30AuthorizeEndpoint.class);

	@ApiOperation(value = "CAS 3.0 ticket验证接口", notes = "通过ticket获取当前登录用户信息",httpMethod="POST")
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
	    _logger.debug("serviceValidate " 
	                    + " ticket " + ticket 
	                    +" , service " + service 
	                    +" , pgtUrl " + pgtUrl
	                    +" , renew " + renew
	                    +" , format " + format
	            );
	    
setContentType(request,response,format);
	    
		Ticket storedTicket=null;
		if(ticket.startsWith(CasConstants.PREFIX.SERVICE_TICKET_PREFIX)) {
			try {
				storedTicket = ticketServices.consumeTicket(ticket);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ServiceResponseBuilder serviceResponseBuilder=new ServiceResponseBuilder();
		
		if(storedTicket!=null){
		    SigninPrincipal authentication = ((SigninPrincipal)storedTicket.getAuthentication().getPrincipal());
			String principal=authentication.getUsername();
			_logger.debug("principal "+principal);
			serviceResponseBuilder.success().setUser(principal);
			if(pgtUrl != null && !pgtUrl.equalsIgnoreCase("")) {
				ProxyGrantingTicketIOUImpl proxyGrantingTicketIOUImpl =new ProxyGrantingTicketIOUImpl();
				String proxyGrantingTicketIOU=casProxyGrantingTicketServices.createTicket(proxyGrantingTicketIOUImpl);
				
				ProxyGrantingTicketImpl proxyGrantingTicketImpl=new ProxyGrantingTicketImpl(storedTicket.getAuthentication(),storedTicket.getCasDetails());
				String proxyGrantingTicket=casProxyGrantingTicketServices.createTicket(proxyGrantingTicketImpl);
				
				serviceResponseBuilder.success().setTicket(proxyGrantingTicketIOU);
				serviceResponseBuilder.success().setProxy(pgtUrl);
			
				postMessage(pgtUrl+"?pgtId="+proxyGrantingTicket+"&pgtIou="+proxyGrantingTicketIOU,null);		
			}
			
			if(Boolean.isTrue(storedTicket.getCasDetails().getIsAdapter())){
				AbstractAuthorizeAdapter adapter =(AbstractAuthorizeAdapter)Instance.newInstance(storedTicket.getCasDetails().getAdapter());
				UserInfo userInfo = (UserInfo) userInfoService.loadByUsername(principal);
				adapter.generateInfo(authentication,userInfo, serviceResponseBuilder);
			}
		}else{
			serviceResponseBuilder.failure()
				.setCode(CasConstants.ERROR_CODE.INVALID_TICKET)
				.setDescription("Ticket "+ticket+" not recognized");
		}
	
		return serviceResponseBuilder.serviceResponseBuilder();
	}
	
	@ApiOperation(value = "CAS 3.0 ProxyTicket代理验证接口", notes = "通过ProxyGrantingTicket获取ProxyTicket",httpMethod="POST")
	@RequestMapping("/authz/cas/p3/proxy")
	@ResponseBody
	public String proxy(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = CasConstants.PARAMETER.PROXY_GRANTING_TICKET) String pgt,
			@RequestParam(value = CasConstants.PARAMETER.TARGET_SERVICE) String targetService,
			@RequestParam(value = CasConstants.PARAMETER.FORMAT,required=false,defaultValue=CasConstants.FORMAT_TYPE.XML) String format){
	    _logger.debug("proxy " 
                + " pgt " + pgt 
                +" , targetService " + targetService 
                +" , format " + format
        );
	    setContentType(request,response,format);
	    ProxyGrantingTicketImpl proxyGrantingTicketImpl = (ProxyGrantingTicketImpl)casProxyGrantingTicketServices.get(pgt);
	    if(proxyGrantingTicketImpl != null) {
	    	ProxyTicketImpl ProxyTicketImpl = new ProxyTicketImpl(proxyGrantingTicketImpl.getAuthentication(),proxyGrantingTicketImpl.getCasDetails());
	    	String proxyTicket =ticketServices.createTicket(ProxyTicketImpl);
	    	ProxyServiceResponseBuilder proxyServiceResponseBuilder=new ProxyServiceResponseBuilder();
	 		return proxyServiceResponseBuilder.success().setTicket(proxyTicket).setFormat(format).serviceResponseBuilder();
	    }
	    ProxyServiceResponseBuilder proxyServiceResponseBuilder=new ProxyServiceResponseBuilder();
		return proxyServiceResponseBuilder.success().setTicket("").setFormat(format).serviceResponseBuilder();
	}
	
	@ApiOperation(value = "CAS 3.0 ticket代理验证接口", notes = "通过ProxyTicket获取当前登录用户信息",httpMethod="POST")
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
	    _logger.debug("proxyValidate " 
                + " ticket " + ticket 
                +" , service " + service 
                +" , pgtUrl " + pgtUrl
                +" , renew " + renew
                +" , format " + format
        );
	    setContentType(request,response,format);
		
		Ticket storedTicket=null;
		if(ticket.startsWith(CasConstants.PREFIX.PROXY_TICKET_PREFIX)) {
			try {
					storedTicket = ticketServices.consumeTicket(ticket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ServiceResponseBuilder serviceResponseBuilder=new ServiceResponseBuilder();
		
		if(storedTicket!=null){
		    SigninPrincipal authentication = ((SigninPrincipal)storedTicket.getAuthentication().getPrincipal());
			String principal=authentication.getUsername();
			_logger.debug("principal "+principal);
			serviceResponseBuilder.success().setUser(principal);
			
			if(Boolean.isTrue(storedTicket.getCasDetails().getIsAdapter())){
				AbstractAuthorizeAdapter adapter =(AbstractAuthorizeAdapter)Instance.newInstance(storedTicket.getCasDetails().getAdapter());
				UserInfo userInfo = (UserInfo) userInfoService.loadByUsername(principal);
				adapter.generateInfo(authentication,userInfo, serviceResponseBuilder);
			}
		}else{
			serviceResponseBuilder.failure()
				.setCode(CasConstants.ERROR_CODE.INVALID_TICKET)
				.setDescription("Ticket "+ticket+" not recognized");
		}
	
		return serviceResponseBuilder.serviceResponseBuilder();
	}
}
