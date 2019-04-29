/*
*   Copyright 2010 James Cox <james.s.cox@gmail.com>
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.


package com.connsec.spring;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.LogoutRequest;
import org.opensaml.saml2.core.Response;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.xml.security.SecurityException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;

import com.connsec.saml.binding.BindingAdapter;
import com.connsec.spring.SAMLAuthenticationToken;
import com.connsec.spring.SAMLResponseAuthenticationProcessingFilter;
import com.connsec.spring.ServiceProviderAuthenticationException;
import com.connsec.spring.User;


public class SAMLResponseAuthenticationProcessingFilterTest {

	
	//args to class under test
	@Mock private HttpServletRequest request;
	@Mock private HttpServletResponse response;
	@Mock private FilterChain filterChain;
	
	//derived
	@Mock private SAMLMessageContext messageContext;
	@Mock private Response samlResponse;
	@Mock private LogoutRequest logoutRequest;
	private String b64EncodedSAMLMessage = "some b64 encoded message";
	@Mock HttpSession session;
	@Mock User principal;

	@Mock SAMLAuthenticationToken authResult;
	
	//static state
	@Mock SecurityContext context;
	
	//collabs
	String filterProcessURI = "/AssertionConsumerService";
	String contextPath = "/sp";

	@Mock private AuthenticationManager authenticationManager;
	@Mock private BindingAdapter bindingAdapter;
	@Mock private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Mock private AuthenticationFailureHandler authenticationFailureHandler;
	@Mock private ApplicationEventPublisher eventPublisher;
	@Mock private SessionRegistry sessionRegistry;
	@Mock RememberMeServices rememberMeServices;
	
	//class under test
	@InjectMocks SAMLResponseAuthenticationProcessingFilter filter;
	
	@Before
	public void before() throws Exception {

		filter = new SAMLResponseAuthenticationProcessingFilter(filterProcessURI);
		MockitoAnnotations.initMocks(this);

		SecurityContextHolder.setContext(context);
	}
	
	
	@Test
	public void testdoFilterHttpNotAnAuthRequest() throws Exception {
		
		when(request.getRequestURI()).thenReturn(contextPath + "/someplace/else.ht");
		when(request.getContextPath()).thenReturn(contextPath);
		
		filter.doFilter(request, response, filterChain);
		
		verify(authenticationManager, never()).authenticate((Authentication) any());
		verify(filterChain).doFilter(request, response);
	}
	
	@Test
	public void testdoFilterBindingAdapterThrowsMessageDecodingException() throws Exception {
		
		when(request.getRequestURI()).thenReturn(contextPath+filterProcessURI);
		when(request.getContextPath()).thenReturn(contextPath);
		when(bindingAdapter.extractSAMLMessageContext(request)).thenThrow(new MessageDecodingException("MessageDecodingException!"));
		
		filter.doFilter(request, response, filterChain);
		
		verify(filterChain, never()).doFilter(request, response);
		verify(authenticationFailureHandler).onAuthenticationFailure(eq(request), eq(response), isA(ServiceProviderAuthenticationException.class));
	}
	
	@Test
	public void testdoFilterBindingAdapterThrowsSecurityException() throws Exception {
		
		when(request.getRequestURI()).thenReturn(contextPath+filterProcessURI);
		when(request.getContextPath()).thenReturn(contextPath);
		when(bindingAdapter.extractSAMLMessageContext(request)).thenThrow(new SecurityException("SecurityException!"));
		
		filter.doFilter(request, response, filterChain);
		
		verify(filterChain, never()).doFilter(request, response);
		verify(authenticationFailureHandler).onAuthenticationFailure(eq(request), eq(response),  isA(ServiceProviderAuthenticationException.class));
	}
	
	@Test
	public void testdoFilterValidMessageIsNotASAMLResponse() throws Exception {
		
		when(request.getRequestURI()).thenReturn(contextPath+filterProcessURI);
		when(request.getContextPath()).thenReturn(contextPath);
		when(bindingAdapter.extractSAMLMessageContext(request)).thenReturn(messageContext);
		when(messageContext.getInboundMessage()).thenReturn(logoutRequest);
		
		filter.doFilter(request, response, filterChain);
		
		verify(authenticationManager, never()).authenticate((Authentication) any());
		verify(authenticationFailureHandler).onAuthenticationFailure(eq(request), eq(response),  isA(ServiceProviderAuthenticationException.class));

	}
	

	@Test
	public void testdoFilterHttpAuthMngrThrowsAuthnException() throws Exception {
		
		when(request.getRequestURI()).thenReturn(contextPath+filterProcessURI);
		when(request.getContextPath()).thenReturn(contextPath);
		when(bindingAdapter.extractSAMLMessageContext(request)).thenReturn(messageContext);
		when(messageContext.getInboundSAMLMessage()).thenReturn(samlResponse);
				
		doThrow(new BadCredentialsException("BadCreds!")).when(authenticationManager).authenticate((Authentication) any());
		
		filter.doFilter(request, response, filterChain);
		
		verify(authenticationFailureHandler).onAuthenticationFailure(eq(request), eq(response), (AuthenticationException) any());

	}
	
	@Test
	public void testAttemptAuthentication() throws Exception {

		when(request.getRequestURI()).thenReturn(contextPath+filterProcessURI);
		when(request.getContextPath()).thenReturn(contextPath);
		when(bindingAdapter.extractSAMLMessageContext(request)).thenReturn(messageContext);
		when(messageContext.getInboundSAMLMessage()).thenReturn(samlResponse);
		when(bindingAdapter.extractSAMLMessage(request)).thenReturn(b64EncodedSAMLMessage);
		
		when(authenticationManager.authenticate((Authentication) any())).thenReturn(authResult);
		
		filter.doFilter(request, response, filterChain);
		
		verify(authenticationSuccessHandler).onAuthenticationSuccess(request, response, authResult);

		
	}
	
}
*/