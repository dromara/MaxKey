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
package org.dromara.maxkey.authz.cas.endpoint;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authz.cas.endpoint.response.ProxyServiceResponseBuilder;
import org.dromara.maxkey.authz.cas.endpoint.response.ServiceResponseBuilder;
import org.dromara.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.dromara.maxkey.authz.cas.endpoint.ticket.ProxyGrantingTicketIOUImpl;
import org.dromara.maxkey.authz.cas.endpoint.ticket.ProxyGrantingTicketImpl;
import org.dromara.maxkey.authz.cas.endpoint.ticket.ProxyTicketImpl;
import org.dromara.maxkey.authz.cas.endpoint.ticket.Ticket;
import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.constants.ConstsBoolean;
import org.dromara.maxkey.util.Instance;
import org.dromara.maxkey.web.HttpResponseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Crystal.Sea
 * https://apereo.github.io/cas/6.2.x/protocol/CAS-Protocol-Specification.html
 */
@Tag(name = "2-3-CAS API文档模块")
@Controller
public class Cas30AuthorizeEndpoint  extends CasBaseAuthorizeEndpoint{

	static final  Logger _logger = LoggerFactory.getLogger(Cas30AuthorizeEndpoint.class);

	@Operation(summary = "CAS 3.0 ticket验证接口", description = "通过ticket获取当前登录用户信息")
	@RequestMapping(value=CasConstants.ENDPOINT.ENDPOINT_SERVICE_VALIDATE_V3,method={RequestMethod.GET,RequestMethod.POST})
	public void serviceValidate(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = CasConstants.PARAMETER.TICKET) String ticket,
			@RequestParam(value = CasConstants.PARAMETER.SERVICE) String service,
			@RequestParam(value = CasConstants.PARAMETER.PROXY_CALLBACK_URL,required=false) String pgtUrl,
			@RequestParam(value = CasConstants.PARAMETER.RENEW,required=false) String renew,
			@RequestParam(value = CasConstants.PARAMETER.FORMAT,required=false,defaultValue=HttpResponseConstants.FORMAT_TYPE.XML) String format){
	    _logger.debug("serviceValidate  ticket {} , service {} , pgtUrl {} , renew {} , format {}", ticket,service,pgtUrl,renew,format);
	    
		Ticket storedTicket=null;
		if(ticket.startsWith(CasConstants.PREFIX.SERVICE_TICKET_PREFIX)) {
			try {
				storedTicket = ticketServices.consumeTicket(ticket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ServiceResponseBuilder serviceResponseBuilder=new ServiceResponseBuilder(format);
		
		if(storedTicket!=null){
		    SignPrincipal authentication = ((SignPrincipal)storedTicket.getAuthentication().getPrincipal());
			if(StringUtils.isNotBlank(pgtUrl)) {
				ProxyGrantingTicketIOUImpl proxyGrantingTicketIOUImpl =new ProxyGrantingTicketIOUImpl();
				String proxyGrantingTicketIOU=casProxyGrantingTicketServices.createTicket(proxyGrantingTicketIOUImpl);
				
				ProxyGrantingTicketImpl proxyGrantingTicketImpl=new ProxyGrantingTicketImpl(storedTicket.getAuthentication(),storedTicket.getCasDetails());
				String proxyGrantingTicket=casProxyGrantingTicketServices.createTicket(proxyGrantingTicketImpl);
				
				serviceResponseBuilder.success().setTicket(proxyGrantingTicketIOU);
				serviceResponseBuilder.success().setProxy(pgtUrl);
			
				httpRequestAdapter.post(pgtUrl+"?pgtId="+proxyGrantingTicket+"&pgtIou="+proxyGrantingTicketIOU,null);		
			}
			
			if(ConstsBoolean.isTrue(storedTicket.getCasDetails().getIsAdapter())){
				Object casAdapter = Instance.newInstance(storedTicket.getCasDetails().getAdapter());
				try {
					BeanUtils.setProperty(casAdapter, "serviceResponseBuilder", serviceResponseBuilder);
				} catch (IllegalAccessException | InvocationTargetException e) {
					_logger.error("setProperty error . ", e);
				}
				
				AbstractAuthorizeAdapter adapter =(AbstractAuthorizeAdapter)casAdapter;
				adapter.setPrincipal(authentication);
				adapter.setApp(storedTicket.getCasDetails());
				adapter.generateInfo();
			}else {
				_logger.error("Cas Adapter is not Set . ");
			}
		}else{
			serviceResponseBuilder.failure()
				.setCode(CasConstants.ERROR_CODE.INVALID_TICKET)
				.setDescription("Ticket "+ticket+" not recognized");
		}
	
		httpResponseAdapter.write(response,serviceResponseBuilder.serviceResponseBuilder(),format);
	}
	
	@Operation(summary = "CAS 3.0 ProxyTicket代理验证接口", description = "通过ProxyGrantingTicket获取ProxyTicket",method="POST")
	@RequestMapping(value=CasConstants.ENDPOINT.ENDPOINT_PROXY_V3,method={RequestMethod.GET,RequestMethod.POST})
	public void proxy(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = CasConstants.PARAMETER.PROXY_GRANTING_TICKET) String pgt,
			@RequestParam(value = CasConstants.PARAMETER.TARGET_SERVICE) String targetService,
			@RequestParam(value = CasConstants.PARAMETER.FORMAT,required=false,defaultValue=HttpResponseConstants.FORMAT_TYPE.XML) String format){
	    _logger.debug("proxy pgt {} , targetService {} , format {}" , pgt,targetService,format);
	    ProxyServiceResponseBuilder proxyServiceResponseBuilder=new ProxyServiceResponseBuilder(format);
	    ProxyGrantingTicketImpl proxyGrantingTicketImpl = (ProxyGrantingTicketImpl)casProxyGrantingTicketServices.get(pgt);
	    if(proxyGrantingTicketImpl != null) {
	    	ProxyTicketImpl proxyTicketImpl = new ProxyTicketImpl(proxyGrantingTicketImpl.getAuthentication(),proxyGrantingTicketImpl.getCasDetails());
	    	String proxyTicket =ticketServices.createTicket(proxyTicketImpl);
	 		proxyServiceResponseBuilder.success().setTicket(proxyTicket).setFormat(format);
	    }else {
	    	proxyServiceResponseBuilder.success().setTicket("").setFormat(format);
	    }
	    
	    httpResponseAdapter.write(response,proxyServiceResponseBuilder.serviceResponseBuilder(),format);
	}
	
	@Operation(summary = "CAS 3.0 ticket代理验证接口", description = "通过ProxyTicket获取当前登录用户信息",method="POST")
	@RequestMapping(value=CasConstants.ENDPOINT.ENDPOINT_PROXY_VALIDATE_V3,method={RequestMethod.GET,RequestMethod.POST})
	public void proxy(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = CasConstants.PARAMETER.TICKET) String ticket,
			@RequestParam(value = CasConstants.PARAMETER.SERVICE) String service,
			@RequestParam(value = CasConstants.PARAMETER.PROXY_CALLBACK_URL,required=false) String pgtUrl,
			@RequestParam(value = CasConstants.PARAMETER.RENEW,required=false) String renew,
			@RequestParam(value = CasConstants.PARAMETER.FORMAT,required=false,defaultValue=HttpResponseConstants.FORMAT_TYPE.XML) String format){
	    _logger.debug("proxyValidate ticket {} , service {} , pgtUrl {} , renew {} , format {}" , ticket,service,pgtUrl,renew,format);
		
		Ticket storedTicket=null;
		if(ticket.startsWith(CasConstants.PREFIX.PROXY_TICKET_PREFIX)) {
			try {
					storedTicket = ticketServices.consumeTicket(ticket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ServiceResponseBuilder serviceResponseBuilder=new ServiceResponseBuilder(format);
		
		if(storedTicket!=null){
		    SignPrincipal authentication = ((SignPrincipal)storedTicket.getAuthentication().getPrincipal());
			if(ConstsBoolean.isTrue(storedTicket.getCasDetails().getIsAdapter())){
				Object casAdapter = Instance.newInstance(storedTicket.getCasDetails().getAdapter());
				try {
					BeanUtils.setProperty(casAdapter, "serviceResponseBuilder", serviceResponseBuilder);
				} catch (IllegalAccessException | InvocationTargetException e) {
					_logger.error("setProperty error . ", e);
				}
				
				AbstractAuthorizeAdapter adapter =(AbstractAuthorizeAdapter)casAdapter;
				adapter.setPrincipal(authentication);
				adapter.setApp(storedTicket.getCasDetails());
				adapter.generateInfo();
			}else {
				_logger.error("Cas Adapter is not Set . ");
			}
		}else{
			serviceResponseBuilder.failure()
				.setCode(CasConstants.ERROR_CODE.INVALID_TICKET)
				.setDescription("Ticket "+ticket+" not recognized");
		}
		httpResponseAdapter.write(response,serviceResponseBuilder.serviceResponseBuilder(),format);
	}
}
