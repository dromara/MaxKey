package com.connsec.spring;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.connsec.spring.IdentityProviderAuthenticationException;
import com.connsec.spring.RealAuthenticationFailureHandler;
import com.connsec.spring.ServiceProviderAuthenticationException;

public class RealAuthenticationFailureHandlerTest {

	//args to class under test
	@Mock HttpServletRequest request; 
	@Mock HttpServletResponse response;
	@Mock IdentityProviderAuthenticationException idpException;
	@Mock ServiceProviderAuthenticationException spException;
	@Mock HttpSession session;
	
	//collabs
	@Mock RequestCache requestCache;
	
	@Mock SavedRequest savedRequest;
	private String redirectURL = "http://sp/protected";
	
	//class under test
	RealAuthenticationFailureHandler handler;

	
	@Before
	public void before() {
		
		MockitoAnnotations.initMocks(this);
		
		handler = new RealAuthenticationFailureHandler(requestCache);
	}
	
	@Test
	public void testOnAuthnFailureIdentityProviderException() throws Exception {
		
		when(requestCache.getRequest(request, response)).thenReturn(savedRequest);
		when(savedRequest.getRedirectUrl()).thenReturn(redirectURL);
		
		handler.onAuthenticationFailure(request, response, idpException);
		
		verify(response).sendRedirect(redirectURL);
	}
	
	@Test
	public void testOnAuthnFailureServiceProviderException() throws Exception {
		
		when(requestCache.getRequest(request, response)).thenReturn(savedRequest);
		when(savedRequest.getRedirectUrl()).thenReturn(redirectURL);
		
		handler.onAuthenticationFailure(request, response, spException);
		
		verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
	}
	
	
	
}
