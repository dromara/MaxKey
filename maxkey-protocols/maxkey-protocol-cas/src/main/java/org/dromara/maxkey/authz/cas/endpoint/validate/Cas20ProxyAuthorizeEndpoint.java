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
package org.dromara.maxkey.authz.cas.endpoint.validate;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authz.cas.endpoint.CasBaseAuthorizeEndpoint;
import org.dromara.maxkey.authz.cas.response.ProxyServiceResponseBuilder;
import org.dromara.maxkey.authz.cas.response.ServiceResponseBuilder;
import org.dromara.maxkey.authz.cas.ticket.CasConstants;
import org.dromara.maxkey.authz.cas.ticket.ProxyGrantingTicketImpl;
import org.dromara.maxkey.authz.cas.ticket.ProxyTicketImpl;
import org.dromara.maxkey.authz.cas.ticket.Ticket;
import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.constants.ConstsBoolean;
import org.dromara.maxkey.http.HttpResponseConstants;
import org.dromara.maxkey.util.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Crystal.Sea
 * https://apereo.github.io/cas/6.2.x/protocol/CAS-Protocol-V2-Specification.html
 */
@Tag(name = "2-3-CAS API文档模块")
@RestController
public class Cas20ProxyAuthorizeEndpoint  extends CasBaseAuthorizeEndpoint{
    static final  Logger _logger = LoggerFactory.getLogger(Cas20ProxyAuthorizeEndpoint.class);
   
    /**
     * @param request
     * @param response
     * @param pgt
     * @param targetService
     * @return
		2.7. /proxy [CAS 2.0]
		/proxy provides proxy tickets to services that have acquired proxy-granting tickets and will be proxying authentication to back-end services.
		
		
		2.7.1. parameters
		The following HTTP request parameters MUST be specified to /proxy. They are both case-sensitive.
		
		pgt [REQUIRED] - the proxy-granting ticket acquired by the service during service ticket or proxy ticket validation.
		targetService [REQUIRED] - the service identifier of the back-end service. Note that not all back-end services are web services so this service identifier will not always be an URL. However, the service identifier specified here MUST match the service parameter specified to /proxyValidate upon validation of the proxy ticket.
		
		2.7.2. response
		/proxy will return an XML-formatted CAS serviceResponse document as described in the XML schema in Appendix A. Below are example responses:
		
		Response on request success:
		  <cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas">
		      <cas:proxySuccess>
		          <cas:proxyTicket>PT-1856392-b98xZrQN4p90ASrw96c8</cas:proxyTicket>
		      </cas:proxySuccess>
		  </cas:serviceResponse>
		
		Response on request failure:
		<cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas">
		      <cas:proxyFailure code="INVALID_REQUEST">
		          'pgt' and 'targetService' parameters are both required
		      </cas:proxyFailure>
		  </cas:serviceResponse>
		{
		  "serviceResponse" : {
		    "authenticationFailure" : {
		      "code" : "INVALID_REQUEST",
		      "description" : "'pgt' and 'targetService' parameters are both required"
		    }
		  }
		}
		
		
		2.7.3. error codes
		The following values MAY be used as the code attribute of authentication failure responses. The following is the minimum set of error codes that all CAS servers MUST implement. Implementations MAY include others.
		
		INVALID_REQUEST - not all of the required request parameters were present
		
		UNAUTHORIZED_SERVICE - service is unauthorized to perform the proxy request
		
		INTERNAL_ERROR - an internal error occurred during ticket validation
		
		For all error codes, it is RECOMMENDED that CAS provide a more detailed message as the body of the <cas:authenticationFailure> block of the XML response.
     */
    @RequestMapping(value=CasConstants.ENDPOINT.ENDPOINT_PROXY ,produces =MediaType.APPLICATION_XML_VALUE,method={RequestMethod.GET,RequestMethod.POST})
    
    public String proxy(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = CasConstants.PARAMETER.PROXY_GRANTING_TICKET) String pgt,
            @RequestParam(value = CasConstants.PARAMETER.TARGET_SERVICE) String targetService,
            @RequestParam(value = CasConstants.PARAMETER.FORMAT,required=false,defaultValue=HttpResponseConstants.FORMAT_TYPE.XML) String format){
        _logger.debug("proxy  pgt {} , targetService {} , format {}" ,pgt,targetService, format);
        ProxyServiceResponseBuilder proxyServiceResponseBuilder=new ProxyServiceResponseBuilder(format);
        
        ProxyGrantingTicketImpl proxyGrantingTicketImpl = (ProxyGrantingTicketImpl)casProxyGrantingTicketServices.get(pgt);
        if(proxyGrantingTicketImpl != null) {
            ProxyTicketImpl proxyTicketImpl = new ProxyTicketImpl(proxyGrantingTicketImpl.getAuthentication(),proxyGrantingTicketImpl.getCasDetails());
            String proxyTicket =ticketServices.createTicket(proxyTicketImpl);
             proxyServiceResponseBuilder.success().setTicket(proxyTicket).setFormat(format);
        }else {
            proxyServiceResponseBuilder.success().setTicket("").setFormat(format);
        }
        return proxyServiceResponseBuilder.serviceResponseBuilder();
    }
    
    /**
     * @param request
     * @param response
     * @param ticket
     * @param service
     * @param pgtUrl
     * @param renew
     * @return
		2.6. /proxyValidate [CAS 2.0]
		/proxyValidate MUST perform the same validation tasks as /serviceValidate and additionally validate proxy tickets. /proxyValidate MUST be capable of validating both service tickets and proxy tickets. See Section 2.5.4 for details.
		
		
		2.6.1. parameters
		/proxyValidate has the same parameter requirements as /serviceValidate. See Section 2.5.1.
		
		
		2.6.2. response
		/proxyValidate will return an XML-formatted CAS serviceResponse as described in the XML schema in Appendix A. Below are example responses:
		
		Response on ticket validation success:
		  <cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas">
		    <cas:authenticationSuccess>
		      <cas:user>username</cas:user>
		      <cas:proxyGrantingTicket>PGTIOU-84678-8a9d...</cas:proxyGrantingTicket>
		      <cas:proxies>
		        <cas:proxy>https://proxy2/pgtUrl</cas:proxy>
		        <cas:proxy>https://proxy1/pgtUrl</cas:proxy>
		      </cas:proxies>
		    </cas:authenticationSuccess>
		  </cas:serviceResponse>
		
		{
		  "serviceResponse" : {
		    "authenticationSuccess" : {
		      "user" : "username",
		      "proxyGrantingTicket" : "PGTIOU-84678-8a9d...",
		      "proxies" : [ "https://proxy1/pgtUrl", "https://proxy2/pgtUrl" ]
		    }
		  }
		}
		
		Note: when authentication has proceeded through multiple proxies, the order in which the proxies were traversed MUST be reflected in the <cas:proxies> block. The most recently-visited proxy MUST be the first proxy listed, and all the other proxies MUST be shifted down as new proxies are added. In the above example, the service identified by <https://proxy1/pgtUrl> was visited first, and that service proxied authentication to the service identified by <https://proxy2/pgtUrl>.
		
		Response on ticket validation failure:
		
		  <cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'>
		      <cas:authenticationFailure code="INVALID_TICKET">
		         ticket PT-1856376-1HMgO86Z2ZKeByc5XdYD not recognized
		      </cas:authenticationFailure>
		  </cas:serviceResponse>
		
		{
		  "serviceResponse" : {
		    "authenticationFailure" : {
		      "code" : "INVALID_TICKET",
		      "description" : "Ticket PT-1856339-aA5Yuvrxzpv8Tau1cYQ7 not recognized"
		    }
		  }
		}
     */
    
    @Operation(summary = "CAS 2.0 ticket代理验证接口", description = "通过ticket获取当前登录用户信息",method="POST")
    @RequestMapping(value=CasConstants.ENDPOINT.ENDPOINT_PROXY_VALIDATE,produces =MediaType.APPLICATION_XML_VALUE,method={RequestMethod.GET,RequestMethod.POST})
    
    public String proxyValidate(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = CasConstants.PARAMETER.TICKET) String ticket,
            @RequestParam(value = CasConstants.PARAMETER.SERVICE) String service,
            @RequestParam(value = CasConstants.PARAMETER.PROXY_CALLBACK_URL,required=false) String pgtUrl,
            @RequestParam(value = CasConstants.PARAMETER.RENEW,required=false) String renew,
            @RequestParam(value = CasConstants.PARAMETER.FORMAT,required=false,defaultValue=HttpResponseConstants.FORMAT_TYPE.XML) String format){
        _logger.debug("proxyValidate ticket {} , service {} , pgtUrl {} , renew {} , format {}" ,ticket,service, pgtUrl,renew,format);
        
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
        
        return serviceResponseBuilder.serviceResponseBuilder();
    }
    
}
