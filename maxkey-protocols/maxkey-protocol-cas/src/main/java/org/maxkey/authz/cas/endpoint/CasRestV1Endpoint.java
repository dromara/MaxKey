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

import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.BasicAuthentication;
import org.maxkey.authz.cas.endpoint.response.ServiceResponseBuilder;
import org.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.maxkey.authz.cas.endpoint.ticket.ServiceTicketImpl;
import org.maxkey.authz.cas.endpoint.ticket.TicketGrantingTicketImpl;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.AppsCasDetails;
import org.maxkey.web.WebContext;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Crystal.Sea
 * https://apereo.github.io/cas/6.2.x/protocol/REST-Protocol.html
 */
@Controller
public class CasRestV1Endpoint  extends CasBaseAuthorizeEndpoint{
    final static Logger _logger = LoggerFactory.getLogger(CasRestV1Endpoint.class);
	
    @Autowired
    @Qualifier("authenticationProvider")
    AbstractAuthenticationProvider authenticationProvider ;
    

	@RequestMapping(value="/authz/cas/v1/tickets", 
	        method=RequestMethod.POST, 
	        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> casLoginRestTickets(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value=CasConstants.PARAMETER.SERVICE,required=false) String casService,
            @RequestParam(value=CasConstants.PARAMETER.REST_USERNAME,required=true) String username,
            @RequestParam(value=CasConstants.PARAMETER.REST_PASSWORD,required=true) String password){
	    try {
    	    if (password == null || password.isEmpty()) {
                throw new BadCredentialsException("No credentials are provided or extracted to authenticate the REST request");
            }
    	    
    	    BasicAuthentication authentication =new BasicAuthentication(username,password,"CASREST");
    	    
    	    authenticationProvider.basicAuthenticate(authentication);
            
            TicketGrantingTicketImpl ticketGrantingTicket=new TicketGrantingTicketImpl("Random",WebContext.getAuthentication(),null);
            
            String ticket=casTicketGrantingTicketServices.createTicket(ticketGrantingTicket);
            String location = applicationConfig.getServerPrefix()+"/authz/cas/v1/tickets/" + ticket;
            HttpHeaders headers = new HttpHeaders();
            headers.add("location", location);
            return new ResponseEntity<>("Location: " + location, headers ,HttpStatus.CREATED);
 
	    } catch (final AuthenticationException e) {
	        _logger.error("BadCredentialsException ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (final Exception e) {
            
            _logger.error("Exception ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@RequestMapping(value="/authz/cas/v1/tickets/{ticketGrantingTicket}", 
	            method=RequestMethod.POST, 
	            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> requestServiceTicket(
	            HttpServletRequest request,
	            HttpServletResponse response,
	            @PathVariable("ticketGrantingTicket") String ticketGrantingTicket,
	            @RequestParam(value=CasConstants.PARAMETER.SERVICE,required=false) String casService,
	            @RequestParam(value=CasConstants.PARAMETER.RENEW,required=false) String renew,
	            @RequestParam(value=CasConstants.PARAMETER.REST_USERNAME,required=false) String username,
	            @RequestParam(value=CasConstants.PARAMETER.REST_PASSWORD,required=false) String password){
	       try {
            TicketGrantingTicketImpl ticketGrantingTicketImpl = 
                    (TicketGrantingTicketImpl) casTicketGrantingTicketServices.get(ticketGrantingTicket);
            
            AppsCasDetails  casDetails=casDetailsService.getAppDetails(casService);
            
            ServiceTicketImpl serviceTicket=new ServiceTicketImpl(ticketGrantingTicketImpl.getAuthentication(),casDetails);
            String ticket=ticketServices.createTicket(serviceTicket);
            return new ResponseEntity<>(ticket, HttpStatus.OK);
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	       return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
	   }
	   
    @RequestMapping(value="/authz/cas/v1/tickets/{ticketGrantingTicket}", 
	            method=RequestMethod.GET)
    public ResponseEntity<String> verifyTicketGrantingTicketStatus(
	            @PathVariable("ticketGrantingTicket") String ticketGrantingTicket,
	            HttpServletRequest request,
	            HttpServletResponse response){
	       try {
            TicketGrantingTicketImpl ticketGrantingTicketImpl = 
                       (TicketGrantingTicketImpl) casTicketGrantingTicketServices.get(ticketGrantingTicket);
                if(ticketGrantingTicketImpl != null) {
                    return new ResponseEntity<>("", HttpStatus.OK);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	       return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
	}
    
    @RequestMapping(value="/authz/cas/v1/tickets/{ticketGrantingTicket}", 
            method=RequestMethod.DELETE)
    public ResponseEntity<String> destroyTicketGrantingTicket(
            @PathVariable("ticketGrantingTicket") String ticketGrantingTicket,
            HttpServletRequest request,
            HttpServletResponse response){
       try {
        TicketGrantingTicketImpl ticketGrantingTicketImpl = 
                   (TicketGrantingTicketImpl) casTicketGrantingTicketServices.remove(ticketGrantingTicket);
            if(ticketGrantingTicketImpl != null) {
                return new ResponseEntity<>("", HttpStatus.OK);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }
	   
	   
	@RequestMapping(value="/authz/cas/v1/users", 
            method=RequestMethod.POST, 
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
            
            BasicAuthentication authentication =new BasicAuthentication(username,password,"CASREST");
            
            authenticationProvider.basicAuthenticate(authentication);
            UserInfo userInfo =WebContext.getUserInfo();
            TicketGrantingTicketImpl ticketGrantingTicket=new TicketGrantingTicketImpl("Random",WebContext.getAuthentication(),null);
            
            String ticket=casTicketGrantingTicketServices.createTicket(ticketGrantingTicket);
            String location = applicationConfig.getServerPrefix()+"/authz/cas/v1/tickets/" + ticket;
            HttpHeaders headers = new HttpHeaders();
            headers.add("location", location);
            ServiceResponseBuilder serviceResponseBuilder=new ServiceResponseBuilder();
            serviceResponseBuilder.setFormat(CasConstants.FORMAT_TYPE.JSON);
            //for user
            serviceResponseBuilder.setAttribute("uid", userInfo.getId());
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
