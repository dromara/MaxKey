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
*/

package com.connsec.spring;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.opensaml.DefaultBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.connsec.spring.SAMLAuthenticationEntryPoint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/applicationContext-property-mappings.xml","file:src/main/webapp/WEB-INF/spring/applicationContext-sp-config.xml","file:src/main/webapp/WEB-INF/spring/applicationContext-spring-security.xml"})

public class SAMLAuthenticationEntryPointIntTest {

	private final static Logger logger = LoggerFactory
	.getLogger(SAMLAuthenticationEntryPointIntTest.class);
	
	//args to class under test 
	@Mock private HttpServletRequest request;
	MockHttpServletResponse response;
	@Mock AuthenticationException authException;
	
	private SAMLAuthenticationEntryPoint samlAuthenticationEntryPoint;

	@Autowired
	public void setSamlAuthenticationEntryPoint(
			SAMLAuthenticationEntryPoint samlAuthenticationEntryPoint) {
		this.samlAuthenticationEntryPoint = samlAuthenticationEntryPoint;
	}
	
	@Before
	public void before() throws Exception {
	
		MockitoAnnotations.initMocks(this);
		response = new MockHttpServletResponse();

	}
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		DefaultBootstrap.bootstrap();
	}
	
	@Test
	public void testCommence() throws Exception {
		
		samlAuthenticationEntryPoint.commence(request, response, authException);
		
		assertNotNull("response.getContentAsString was null", response.getContentAsString());
		
		logger.debug("The response was: {}", response.getContentAsString());
		assertTrue("The response did not contain a SAMLRequest", response.getContentAsString().contains("SAMLRequest"));
		
		
	}
	
}
