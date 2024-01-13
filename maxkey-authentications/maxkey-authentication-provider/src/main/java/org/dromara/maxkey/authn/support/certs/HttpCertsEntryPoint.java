/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.authn.support.certs;

import java.security.cert.X509Certificate;

import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.authn.support.httpheader.HttpHeaderEntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HttpCertsEntryPoint  implements AsyncHandlerInterceptor {
	private static final Logger _logger = LoggerFactory.getLogger(HttpHeaderEntryPoint.class);
	
	static String CERTIFICATE_ATTRIBUTE = "javax.servlet.request.X509Certificate";
	static String PEER_CERTIFICATES_ATTRIBUTE = "javax.net.ssl.peer_certificates";
	
    boolean enable;
    
    @Autowired
    @Qualifier("authenticationProvider")
    AbstractAuthenticationProvider authenticationProvider ;
    
    @Override
	 public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 
		 if(!enable){
			 return true;
		 }

		 _logger.debug("Certificate Login Start ...");
		 _logger.debug("Request url : "+ request.getRequestURL());
		 _logger.debug("Request URI : "+ request.getRequestURI());
		 _logger.trace("Request ContextPath : "+ request.getContextPath());
		 _logger.trace("Request ServletPath : "+ request.getServletPath());
		 _logger.trace("RequestSessionId : "+ request.getRequestedSessionId());
		 _logger.trace("isRequestedSessionIdValid : "+ request.isRequestedSessionIdValid());
		 _logger.trace("getSession : "+ request.getSession(false));
		 
		X509Certificate[] certificates= (X509Certificate[])request.getAttribute(CERTIFICATE_ATTRIBUTE); // 2.2 spec
		if (certificates == null) {
			 certificates = (X509Certificate[]) request.getAttribute(PEER_CERTIFICATES_ATTRIBUTE); // 2.1 spec
        }
		
		for (X509Certificate cert : certificates) {
			cert.checkValidity();
			_logger.debug("cert validated");
			_logger.debug("cert infos {}" , cert.toString());
			_logger.debug("Version {}" , cert.getVersion());
			_logger.debug("SerialNumber {}" , cert.getSerialNumber().toString(16));
			_logger.debug("SubjectDN {}" , cert.getSubjectDN());
			_logger.debug("IssuerDN {}" , cert.getIssuerDN());
			_logger.debug("NotBefore {}" , cert.getNotBefore());
			_logger.debug("SigAlgName {}" , cert.getSigAlgName());
		    byte[] sign = cert.getSignature();
		    _logger.debug("Signature ");
		    for (int j = 0; j < sign.length; j++){
		    	_logger.debug("{} , ",sign[j] );
		    }
		    java.security.PublicKey pk = cert.getPublicKey();
		    byte[] pkenc = pk.getEncoded();
		    _logger.debug("PublicKey ");
		    for (int j = 0; j < pkenc.length; j++){
		    	_logger.debug("{} ,",pkenc[j]);
		    }
		}
		 return true;
    }

	public HttpCertsEntryPoint(boolean enable, AbstractAuthenticationProvider authenticationProvider) {
		super();
		this.enable = enable;
		this.authenticationProvider = authenticationProvider;
	}
    
    
}
