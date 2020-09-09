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

import org.maxkey.authn.BasicAuthentication;
import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authz.cas.endpoint.response.ServiceResponseBuilder;
import org.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.maxkey.authz.cas.endpoint.ticket.ServiceTicketImpl;
import org.maxkey.authz.cas.endpoint.ticket.TicketGrantingTicketImpl;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.AppsCasDetails;
import org.maxkey.persistence.db.PasswordPolicyValidator;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    protected PasswordPolicyValidator passwordPolicyValidator;
    
    @Autowired
    @Qualifier("authenticationRealm")
    protected AbstractAuthenticationRealm authenticationRealm;
    

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
    	    
            AbstractAuthenticationRealm authenticationRealm = 
                    (AbstractAuthenticationRealm) WebContext.getBean("authenticationRealm");
            UserInfo loadeduserInfo = authenticationRealm.loadUserInfo(username, "");
            if (loadeduserInfo != null) {
                
                authenticationRealm.passwordMatches(loadeduserInfo, password);
                
                passwordPolicyValidator.passwordPolicyValid(loadeduserInfo);
                
                WebContext.setUserInfo(loadeduserInfo);
                BasicAuthentication authentication =new BasicAuthentication();
                authentication.setUsername(username);
                authentication.setPassword(password);
                authentication.setAuthType("basic");
                
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                authentication, 
                                "PASSWORD", 
                                authenticationRealm.grantAuthority(loadeduserInfo)
                        );

                authentication.setAuthenticated(true);
                WebContext.setAuthentication(usernamePasswordAuthenticationToken);
                WebContext.setUserInfo(loadeduserInfo);

                authenticationRealm.insertLoginHistory(loadeduserInfo, "CAS", "", "", "SUCCESS");
                
                TicketGrantingTicketImpl ticketGrantingTicket=new TicketGrantingTicketImpl("Random",WebContext.getAuthentication(),null);
                
                String ticket=ticketServices.createTicket(ticketGrantingTicket);
                String location = applicationConfig.getServerPrefix()+"/authz/cas/v1/tickets/" + ticket;
                HttpHeaders headers = new HttpHeaders();
                headers.add("location", location);
                return new ResponseEntity<>("Location: " + location, headers ,HttpStatus.CREATED);
                
            }else {
    	        String message = WebContext.getI18nValue("login.error.username");
                _logger.debug("login user  " + username + " not in this System ." + message);
                throw new BadCredentialsException(WebContext.getI18nValue("login.error.username"));
    	    }
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
                    (TicketGrantingTicketImpl) ticketServices.consumeTicket(ticketGrantingTicket);
            AppsCasDetails casDetails=new AppsCasDetails();
            if(casService.startsWith("http")) {
                casDetails.setService(casService);
                
                List<AppsCasDetails> casDetailsList=casDetailsService.query(casDetails);
                
                casDetails=(casDetailsList!=null && casDetailsList.size()==1)?casDetailsList.get(0):null;
            }else {
                casDetails=casDetailsService.getAppDetails(casService);
            }
            
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
                       (TicketGrantingTicketImpl) ticketServices.consumeTicket(ticketGrantingTicket);
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
                   (TicketGrantingTicketImpl) ticketServices.consumeTicket(ticketGrantingTicket);
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
            
            AbstractAuthenticationRealm authenticationRealm = 
                    (AbstractAuthenticationRealm) WebContext.getBean("authenticationRealm");
            UserInfo loadeduserInfo = authenticationRealm.loadUserInfo(username, "");
            if (loadeduserInfo != null) {
                
                authenticationRealm.passwordMatches(loadeduserInfo, password);
                
                passwordPolicyValidator.passwordPolicyValid(loadeduserInfo);
                
                WebContext.setUserInfo(loadeduserInfo);
                BasicAuthentication authentication =new BasicAuthentication();
                authentication.setUsername(username);
                authentication.setPassword(password);
                authentication.setAuthType("basic");
                
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                authentication, 
                                "PASSWORD", 
                                authenticationRealm.grantAuthority(loadeduserInfo)
                        );

                authentication.setAuthenticated(true);
                WebContext.setAuthentication(usernamePasswordAuthenticationToken);
                WebContext.setUserInfo(loadeduserInfo);

                authenticationRealm.insertLoginHistory(loadeduserInfo, "CAS", "", "", "SUCCESS");
                
                TicketGrantingTicketImpl ticketGrantingTicket=new TicketGrantingTicketImpl("Random",WebContext.getAuthentication(),null);
                
                String ticket=ticketServices.createTicket(ticketGrantingTicket);
                String location = applicationConfig.getServerPrefix()+"/authz/cas/v1/tickets/" + ticket;
                HttpHeaders headers = new HttpHeaders();
                headers.add("location", location);
                ServiceResponseBuilder serviceResponseBuilder=new ServiceResponseBuilder();
                serviceResponseBuilder.setFormat(CasConstants.FORMAT_TYPE.JSON);
                //for user
                serviceResponseBuilder.setAttribute("uid", loadeduserInfo.getId());
                serviceResponseBuilder.setAttribute("displayName",loadeduserInfo.getDisplayName());
                serviceResponseBuilder.setAttribute("firstName", loadeduserInfo.getGivenName());
                serviceResponseBuilder.setAttribute("lastname", loadeduserInfo.getFamilyName());
                serviceResponseBuilder.setAttribute("mobile", loadeduserInfo.getMobile());
                serviceResponseBuilder.setAttribute("birthday", loadeduserInfo.getBirthDate());
                serviceResponseBuilder.setAttribute("gender", loadeduserInfo.getGender()+"");
                
                //for work
                serviceResponseBuilder.setAttribute("employeeNumber", loadeduserInfo.getEmployeeNumber());
                serviceResponseBuilder.setAttribute("title", loadeduserInfo.getJobTitle());
                serviceResponseBuilder.setAttribute("email", loadeduserInfo.getWorkEmail());
                serviceResponseBuilder.setAttribute("department", loadeduserInfo.getDepartment());
                serviceResponseBuilder.setAttribute("departmentId", loadeduserInfo.getDepartmentId());
                serviceResponseBuilder.setAttribute("workRegion",loadeduserInfo.getWorkRegion());
                
                serviceResponseBuilder.success().setUser(loadeduserInfo.getUsername());
                return new ResponseEntity<>(serviceResponseBuilder.serviceResponseBuilder(), headers ,HttpStatus.OK);
                
            }else {
                String message = WebContext.getI18nValue("login.error.username");
                _logger.debug("login user  " + username + " not in this System ." + message);
                throw new BadCredentialsException(WebContext.getI18nValue("login.error.username"));
            }
        } catch (final AuthenticationException e) {
            _logger.error("BadCredentialsException ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (final Exception e) {
            
            _logger.error("Exception ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
}
