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
 

package org.dromara.maxkey.authn.support.kerberos;

import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.constants.ConstsLoginType;
import org.dromara.maxkey.crypto.ReciprocalUtils;
import org.dromara.maxkey.util.DateUtils;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.web.WebConstants;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class HttpKerberosEntryPoint implements AsyncHandlerInterceptor {
	private static final Logger _logger = LoggerFactory.getLogger(HttpKerberosEntryPoint.class);
	
    boolean enable;
    
  	ApplicationConfig applicationConfig;
    
    AbstractAuthenticationProvider authenticationProvider ;
    
	KerberosService kerberosService;
	
	 @Override
	 public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 boolean isAuthenticated= AuthorizationUtils.isAuthenticated();
		 String kerberosTokenString = request.getParameter(WebConstants.KERBEROS_TOKEN_PARAMETER);
		 String kerberosUserDomain = request.getParameter(WebConstants.KERBEROS_USERDOMAIN_PARAMETER);
		 
		 if(!enable 
				 || isAuthenticated 
				 || kerberosTokenString == null){
			 return true;
		 }
		 
		 _logger.trace("Kerberos Login Start ...");
		 _logger.trace("Request url : "+ request.getRequestURL());
		 _logger.trace("Request URI : "+ request.getRequestURI());
		 _logger.trace("Request ContextPath : "+ request.getContextPath());
		 _logger.trace("Request ServletPath : "+ request.getServletPath());
		 _logger.trace("RequestSessionId : "+ request.getRequestedSessionId());
		 _logger.trace("isRequestedSessionIdValid : "+ request.isRequestedSessionIdValid());
		 _logger.trace("getSession : "+ request.getSession(false));
		 
		// session not exists，session timeout，recreate new session
		 if(request.getSession(false) == null) {
		    _logger.trace("recreate new session .");
			request.getSession(true);
		 }
		 
		 _logger.trace("getSession.getId : "+ request.getSession().getId());

		//for Kerberos Login
		_logger.debug("Try Kerberos login ");
		_logger.debug("encoder Kerberos Token "+kerberosTokenString);
		_logger.debug("kerberos UserDomain "+kerberosUserDomain);
		
		String decoderKerberosToken=null;
		for(KerberosProxy kerberosProxy : kerberosService.getKerberosProxys()){
			if(kerberosProxy.getUserdomain().equalsIgnoreCase(kerberosUserDomain)){
				decoderKerberosToken=ReciprocalUtils.aesDecoder(kerberosTokenString, kerberosProxy.getCrypto());
				break;
			}
		}
		_logger.debug("decoder Kerberos Token "+decoderKerberosToken);
		KerberosToken  kerberosToken=new KerberosToken();
		kerberosToken=(KerberosToken)JsonUtils.stringToObject(decoderKerberosToken, kerberosToken);
		_logger.debug("Kerberos Token "+kerberosToken);
		
		DateTime notOnOrAfter=DateUtils.toUtcDate(kerberosToken.getNotOnOrAfter());
		_logger.debug("Kerberos Token is After Now  "+notOnOrAfter.isAfterNow());
		
		if(notOnOrAfter.isAfterNow()){
		    LoginCredential loginCredential =new LoginCredential(kerberosToken.getPrincipal(),"",ConstsLoginType.KERBEROS);
		    loginCredential.setProvider(kerberosUserDomain);
            authenticationProvider.authenticate(loginCredential,true);
	    	_logger.debug("Kerberos Logined in , username " + kerberosToken.getPrincipal());
		}
		
		return true;
	}

	 public HttpKerberosEntryPoint() {
	        super();
	 }

    public HttpKerberosEntryPoint (boolean enable) {
        super();
        this.enable = enable;
    }

    public HttpKerberosEntryPoint(AbstractAuthenticationProvider authenticationProvider, KerberosService kerberosService,
			ApplicationConfig applicationConfig, boolean enable) {
		super();
		this.authenticationProvider = authenticationProvider;
		this.kerberosService = kerberosService;
		this.applicationConfig = applicationConfig;
		this.enable = enable;
	}

	public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public void setAuthenticationProvider(AbstractAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}


	
}
