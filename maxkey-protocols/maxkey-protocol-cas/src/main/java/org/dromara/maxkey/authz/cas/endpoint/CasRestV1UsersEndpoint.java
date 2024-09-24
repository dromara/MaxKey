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


import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.authz.cas.endpoint.response.ServiceResponseBuilder;
import org.dromara.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.dromara.maxkey.authz.cas.endpoint.ticket.TicketGrantingTicketImpl;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.web.HttpResponseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Crystal.Sea
 * https://apereo.github.io/cas/6.2.x/protocol/REST-Protocol.html
 */
@Tag(name = "2-4-CAS REST API文档模块")
@Controller
public class CasRestV1UsersEndpoint  extends CasBaseAuthorizeEndpoint{
	static final Logger _logger = LoggerFactory.getLogger(CasRestV1UsersEndpoint.class);
	
    @Autowired
    @Qualifier("authenticationProvider")
    AbstractAuthenticationProvider authenticationProvider ;
	   
    @Operation(summary = "CAS REST认证接口", description = "用户名密码登录接口",method="POST")   
	@PostMapping(value=CasConstants.ENDPOINT.ENDPOINT_REST_USERS_V1, 
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> casLoginRestUsers(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value=CasConstants.PARAMETER.SERVICE,required=false) String casService,
            @RequestParam(value=CasConstants.PARAMETER.REST_USERNAME,required=true) String username,
            @RequestParam(value=CasConstants.PARAMETER.REST_PASSWORD,required=true) String password){
	    try {
            if (password == null || password.isEmpty()) {
                throw new BadCredentialsException("No credentials are provided or extracted to authenticate the REST request");
            }
            
            LoginCredential loginCredential =new LoginCredential(username,password,"CASREST");
            
            authenticationProvider.authenticate(loginCredential,false);
            UserInfo userInfo = AuthorizationUtils.getUserInfo();
            TicketGrantingTicketImpl ticketGrantingTicket=new TicketGrantingTicketImpl("Random",AuthorizationUtils.getAuthentication(),null);
            
            String ticket=casTicketGrantingTicketServices.createTicket(ticketGrantingTicket);
            String location = applicationConfig.getServerPrefix() + CasConstants.ENDPOINT.ENDPOINT_REST_TICKET_V1 + ticket;
            HttpHeaders headers = new HttpHeaders();
            headers.add("location", location);
            ServiceResponseBuilder serviceResponseBuilder=new ServiceResponseBuilder();
            serviceResponseBuilder.setFormat(HttpResponseConstants.FORMAT_TYPE.JSON);
            //for user
            serviceResponseBuilder.setAttribute("userId", userInfo.getId());
            serviceResponseBuilder.setAttribute("displayName",userInfo.getDisplayName());
            serviceResponseBuilder.setAttribute("firstName", userInfo.getGivenName());
            serviceResponseBuilder.setAttribute("lastname", userInfo.getFamilyName());
            serviceResponseBuilder.setAttribute("mobile", userInfo.getMobile());
            serviceResponseBuilder.setAttribute("birthday", userInfo.getBirthDate());
            serviceResponseBuilder.setAttribute("gender", userInfo.getGender()+"");
            
            //for work
            serviceResponseBuilder.setAttribute("employeeNumber", userInfo.getEmployeeNumber());
            serviceResponseBuilder.setAttribute("title", userInfo.getJobTitle());
            serviceResponseBuilder.setAttribute("email", userInfo.getWorkEmail());
            serviceResponseBuilder.setAttribute("department", userInfo.getDepartment());
            serviceResponseBuilder.setAttribute("departmentId", userInfo.getDepartmentId());
            serviceResponseBuilder.setAttribute("workRegion",userInfo.getWorkRegion());
            
            serviceResponseBuilder.success().setUser(userInfo.getUsername());
            
            return new ResponseEntity<>(serviceResponseBuilder.serviceResponseBuilder(), headers ,HttpStatus.OK);
        } catch (final AuthenticationException e) {
            _logger.error("BadCredentialsException ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (final Exception e) {
            
            _logger.error("Exception ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
}
