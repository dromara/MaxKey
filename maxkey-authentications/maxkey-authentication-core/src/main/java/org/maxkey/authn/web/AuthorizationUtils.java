package org.maxkey.authn.web;

import java.text.ParseException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authn.jwt.AuthJwtService;
import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authn.online.OnlineTicketService;
import org.maxkey.entity.UserInfo;
import org.maxkey.util.AuthorizationHeaderUtils;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.springframework.security.core.Authentication;

public class AuthorizationUtils {

	static final String Authorization = "Authorization";
	
	public static  void authenticateWithCookie(
			HttpServletRequest request,
			AuthJwtService authJwtService,
			OnlineTicketService onlineTicketService
			) throws ParseException{
		 if(getAuthentication() == null) {
			Cookie authCookie = WebContext.getCookie(request, Authorization);
			if(authCookie != null ) {
		    	String  authorization =  authCookie.getValue();
		    	doAuthenticate(authorization,authJwtService,onlineTicketService);
			}
		 }
	}
	
	public static  void authenticate(
			HttpServletRequest request,
			AuthJwtService authJwtService,
			OnlineTicketService onlineTicketService
			) throws ParseException{
		 if(getAuthentication() == null) {
			 String  authorization = AuthorizationHeaderUtils.resolveBearer(request);
			if(authorization != null ) {
		    	doAuthenticate(authorization,authJwtService,onlineTicketService);
			}
		 }
	}
	
	public static void doAuthenticate(
			String  authorization,
			AuthJwtService authJwtService,
			OnlineTicketService onlineTicketService) throws ParseException {
		if(authJwtService.validateJwtToken(authorization)) {
			String ticket = authJwtService.resolveTicket(authorization);
			OnlineTicket onlineTicket = onlineTicketService.get(ticket);
			if(onlineTicket != null) {
				setAuthentication(onlineTicket.getAuthentication());
			}
		}
	}
	
	
    public static void setAuthentication(Authentication authentication) {
    	WebContext.setAttribute(WebConstants.AUTHENTICATION, authentication);
    }

    public static Authentication getAuthentication() {
        Authentication authentication = (Authentication) WebContext.getAttribute(WebConstants.AUTHENTICATION);
        return authentication;
    }
    
    public static  boolean isAuthenticated() {
    	return getAuthentication() != null;
    }
    
    public static  boolean isNotAuthenticated() {
    	return getAuthentication() == null;
    }
    
    public static SigninPrincipal getPrincipal() {
    	 Authentication authentication =  getAuthentication();
    	return authentication == null ? null :(SigninPrincipal) authentication.getPrincipal();
    }
    
    public static UserInfo getUserInfo() {
    	Authentication authentication =  getAuthentication();
    	UserInfo userInfo = null;
    	if(isAuthenticated() && (authentication.getPrincipal() instanceof SigninPrincipal)) {
        	SigninPrincipal signinPrincipal = ((SigninPrincipal) authentication.getPrincipal());
        	userInfo = signinPrincipal.getUserInfo();
        }
    	return userInfo;
    }
	
}
