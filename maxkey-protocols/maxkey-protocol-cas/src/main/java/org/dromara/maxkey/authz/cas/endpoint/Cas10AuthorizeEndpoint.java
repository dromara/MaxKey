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


import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authz.cas.endpoint.response.Service10ResponseBuilder;
import org.dromara.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.dromara.maxkey.authz.cas.endpoint.ticket.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * https://apereo.github.io/cas/6.2.x/protocol/CAS-Protocol-Specification.html
 */
@Tag(name = "2-3-CAS API文档模块")
@RestController
public class Cas10AuthorizeEndpoint   extends CasBaseAuthorizeEndpoint{

	static final  Logger _logger = LoggerFactory.getLogger(Cas10AuthorizeEndpoint.class);
	
	/**
	 * @param request
	 * @param response
	 * @param ticket
	 * @param service
	 * @param renew
	 * @return 
	 *    
2.4. /validate [CAS 1.0]
/validate checks the validity of a service ticket. /validate is part of the CAS 1.0 protocol and thus does not handle proxy authentication. CAS MUST respond with a ticket validation failure response when a proxy ticket is passed to /validate.


2.4.1. parameters
The following HTTP request parameters MAY be specified to /validate. They are case sensitive and MUST all be handled by /validate.

service [REQUIRED] - the identifier of the service for which the ticket was issued, as discussed in Section 2.2.1. As a HTTP request parameter, the service value MUST be URL-encoded as described in Section 2.2 of RFC 1738 [4].

Note: It is STRONGLY RECOMMENDED that all service urls be filtered via the service management tool, such that only authorized and known client applications would be able to use the CAS server. Leaving the service management tool open to allow lenient access to all applications will potentially increase the risk of service attacks and other security vulnerabilities. Furthermore, it is RECOMMENDED that only secure protocols such as https be allowed for client applications for further strengthen the authenticating client.

ticket [REQUIRED] - the service ticket issued by /login. Service tickets are described in Section 3.1.

renew [OPTIONAL] - if this parameter is set, ticket validation will only succeed if the service ticket was issued from the presentation of the user��s primary credentials. It will fail if the ticket was issued from a single sign-on session.


2.4.2. response
/validate will return one of the following two responses:
			On ticket validation success:
			yes<LF>
			username<LF>
			
			On ticket validation failure:
			no<LF>
			<LF>
	 */
	@Operation(summary = "CAS 1.0 ticket验证接口", description = "通过ticket获取当前登录用户信息",method="POST")
	@RequestMapping(value=CasConstants.ENDPOINT.ENDPOINT_VALIDATE,method={RequestMethod.GET,RequestMethod.POST})
	public String validate(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = CasConstants.PARAMETER.TICKET) String ticket,
			@RequestParam(value = CasConstants.PARAMETER.SERVICE) String service,
			@RequestParam(value = CasConstants.PARAMETER.RENEW,required=false) String renew){
	    _logger.debug("serviceValidate ticket {} , service {} , renew {}" , ticket,service,renew);
	    
		Ticket storedTicket = null;
		try {
			storedTicket = ticketServices.consumeTicket(ticket);
		} catch (Exception e) {
			_logger.error("consume Ticket error " , e);
		}
		
		if(storedTicket != null){
			String principal=((SignPrincipal)storedTicket.getAuthentication().getPrincipal()).getUsername();
			_logger.debug("principal {}",principal);
			return new Service10ResponseBuilder().success()
					.setUser(principal)
					.serviceResponseBuilder();
		}else{
		    _logger.debug("Ticket not found .");
			return new Service10ResponseBuilder().failure()
					.serviceResponseBuilder();
		}
	}
}
