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

package com.connsec.saml;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;



public abstract class AbstractRequestIntTest {

	//	adapted from org.opensaml.saml2.binding.decoding.HTTPPostDecoderTest
  protected void populateRequestURL(HttpServletRequest request, String requestURL) {
      URL url = null;
      try {
          url = new URL(requestURL);
      } catch (MalformedURLException e) {
          fail("Malformed URL: " + e.getMessage());
      }
      
      when(request.getRequestURL()).thenReturn(new StringBuffer(requestURL));
      when(request.getScheme()).thenReturn(url.getProtocol());
      when(request.getServerName()).thenReturn(url.getHost());
      
      if (url.getPort() != -1) {
      	 when(request.getServerPort()).thenReturn(url.getPort());
          
      } else {
          if ("https".equalsIgnoreCase(url.getProtocol())) {
          	 when(request.getServerPort()).thenReturn(url.getPort());
          } else if ("http".equalsIgnoreCase(url.getProtocol())) {
          	 when(request.getServerPort()).thenReturn(80);
          }
      }
      
      when(request.getRequestURI()).thenReturn(url.getPath());
      when(request.getQueryString()).thenReturn(url.getQuery());
  }
	
}
