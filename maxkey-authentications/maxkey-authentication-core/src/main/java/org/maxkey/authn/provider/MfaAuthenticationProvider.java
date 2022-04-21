/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.maxkey.authn.provider;

import java.util.ArrayList;

import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.LoginCredential;
import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authn.jwt.AuthJwtService;
import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authn.online.OnlineTicketService;
import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.web.AuthorizationUtils;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstsLoginType;
import org.maxkey.entity.Institutions;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.MomentaryService;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;


/**
 * database Authentication provider.
 * @author Crystal.Sea
 *
 */
public class MfaAuthenticationProvider extends AbstractAuthenticationProvider {
    private static final Logger _logger =
            LoggerFactory.getLogger(MfaAuthenticationProvider.class);

    public String getProviderName() {
        return "normal" + PROVIDER_SUFFIX;
    }
    

    public MfaAuthenticationProvider() {
		super();
	}

    public MfaAuthenticationProvider(
    		AbstractAuthenticationRealm authenticationRealm,
    		ApplicationConfig applicationConfig,
    	    OnlineTicketService onlineTicketServices,
    	    AuthJwtService authJwtService,
    	    MomentaryService momentaryService) {
		this.authenticationRealm = authenticationRealm;
		this.applicationConfig = applicationConfig;
		this.onlineTicketServices = onlineTicketServices;
		this.authJwtService = authJwtService;
		this.momentaryService = momentaryService;
	}

    @Override
	public Authentication doAuthenticate(LoginCredential loginCredential) {
		UsernamePasswordAuthenticationToken authenticationToken = null;
		_logger.debug("Trying to authenticate user '{}' via {}", 
                loginCredential.getPrincipal(), getProviderName());
        try {
        	
	        _logger.debug("authentication " + loginCredential);
	        
	        Institutions inst = (Institutions)WebContext.getAttribute(WebConstants.CURRENT_INST);
	        if(inst.getCaptchaSupport().equalsIgnoreCase("YES")) {
	        	captchaValid(loginCredential.getCaptcha(),loginCredential.getAuthType());
	        }
	
	        emptyPasswordValid(loginCredential.getPassword());
	
	        UserInfo userInfo = null;
	
	        emptyUsernameValid(loginCredential.getUsername());
	
	        userInfo =  loadUserInfo(loginCredential.getUsername(),loginCredential.getPassword());
	
	        statusValid(loginCredential , userInfo);
	        //mfa 
	        tftcaptchaValid(loginCredential.getOtpCaptcha(),loginCredential.getAuthType(),userInfo);
	        
	        //Validate PasswordPolicy
	        authenticationRealm.getPasswordPolicyValidator().passwordPolicyValid(userInfo);
	             
	        //Match password 
	        authenticationRealm.passwordMatches(userInfo, loginCredential.getPassword());

	        //apply PasswordSetType and resetBadPasswordCount
	        authenticationRealm.getPasswordPolicyValidator().applyPasswordPolicy(userInfo);
	        
	        authenticationToken = createOnlineSession(loginCredential,userInfo);
	        // user authenticated
	        _logger.debug("'{}' authenticated successfully by {}.", 
	        		loginCredential.getPrincipal(), getProviderName());
	        
	        authenticationRealm.insertLoginHistory(userInfo, 
							        				ConstsLoginType.LOCAL, 
									                "", 
									                "xe00000004", 
									                WebConstants.LOGIN_RESULT.SUCCESS);
        } catch (AuthenticationException e) {
            _logger.error("Failed to authenticate user {} via {}: {}",
                    new Object[] {  loginCredential.getPrincipal(),
                                    getProviderName(),
                                    e.getMessage() });
            WebContext.setAttribute(
                    WebConstants.LOGIN_ERROR_SESSION_MESSAGE, e.getMessage());
        } catch (Exception e) {
            _logger.error("Login error Unexpected exception in {} authentication:\n{}" ,
                            getProviderName(), e.getMessage());
        }
       
        return  authenticationToken;
    }

    public UsernamePasswordAuthenticationToken createOnlineSession(LoginCredential credential,UserInfo userInfo) {
        //Online Tickit
        OnlineTicket onlineTicket = new OnlineTicket();

        userInfo.setOnlineTicket(onlineTicket.getTicketId());
        
        SigninPrincipal principal = new SigninPrincipal(userInfo);
        //set OnlineTicket
        principal.setOnlineTicket(onlineTicket);
        ArrayList<GrantedAuthority> grantedAuthoritys = authenticationRealm.grantAuthority(userInfo);
        principal.setAuthenticated(true);
        
        for(GrantedAuthority administratorsAuthority : grantedAdministratorsAuthoritys) {
            if(grantedAuthoritys.contains(administratorsAuthority)) {
            	principal.setRoleAdministrators(true);
                _logger.trace("ROLE ADMINISTRATORS Authentication .");
            }
        }
        _logger.debug("Granted Authority {}" , grantedAuthoritys);
        
        principal.setGrantedAuthorityApps(authenticationRealm.queryAuthorizedApps(grantedAuthoritys));
        
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                		principal, 
                        "PASSWORD", 
                        grantedAuthoritys
                );
        
        authenticationToken.setDetails(
                new WebAuthenticationDetails(WebContext.getRequest()));
        
        onlineTicket.setAuthentication(authenticationToken);
        
        //store onlineTicket
        this.onlineTicketServices.store(onlineTicket.getTicketId(), onlineTicket);
        
        /*
         *  put Authentication to current session context
         */
        AuthorizationUtils.setAuthentication(authenticationToken);
     
        return authenticationToken;
    }
  
}
