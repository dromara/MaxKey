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
 

package org.maxkey.authn;

import java.util.ArrayList;

import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authn.online.OnlineTicketService;
import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.web.AuthorizationUtils;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.entity.Institutions;
import org.maxkey.entity.UserInfo;
import org.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.maxkey.password.onetimepwd.OtpAuthnService;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;


/**
 * database Authentication provider.
 * @author Crystal.Sea
 *
 */
public class RealmAuthenticationProvider extends AbstractAuthenticationProvider {
    private static final Logger _logger =
            LoggerFactory.getLogger(RealmAuthenticationProvider.class);

    protected String getProviderName() {
        return "RealmAuthenticationProvider";
    }
    

    public RealmAuthenticationProvider() {
		super();
	}


    public RealmAuthenticationProvider(
    		AbstractAuthenticationRealm authenticationRealm,
    		ApplicationConfig applicationConfig,
    	    AbstractOtpAuthn tfaOtpAuthn,
    	    OtpAuthnService otpAuthnService,
    	    OnlineTicketService onlineTicketServices) {
		this.authenticationRealm = authenticationRealm;
		this.applicationConfig = applicationConfig;
		this.tfaOtpAuthn = tfaOtpAuthn;
		this.otpAuthnService = otpAuthnService;
		this.onlineTicketServices = onlineTicketServices;
	}

	@Override
    protected Authentication doInternalAuthenticate(LoginCredential loginCredential) {

        _logger.debug("authentication " + loginCredential);

        //sessionValid(loginCredential.getSessionId());

        //jwtTokenValid(j_jwtToken);

        authTypeValid(loginCredential.getAuthType());
        
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
        if(loginCredential.getAuthType().equalsIgnoreCase(AuthType.MOBILE)) {
        	mobilecaptchaValid(loginCredential.getPassword(),loginCredential.getAuthType(),userInfo);
        }else {            
            //Match password 
        	authenticationRealm.passwordMatches(userInfo, loginCredential.getPassword());
        }
        //apply PasswordSetType and resetBadPasswordCount
        authenticationRealm.getPasswordPolicyValidator().applyPasswordPolicy(userInfo);
        
        UsernamePasswordAuthenticationToken authenticationToken = createOnlineSession(loginCredential,userInfo);
        
        return  authenticationToken;
    }

    /**
     * trustAuthentication.
     * @param username String
     * @param type String
     * @param provider String
     * @param code String
     * @param message String
     * @return boolean
     */
    @Override
    public  Authentication authentication(LoginCredential loginCredential,boolean isTrusted) {
        UserInfo loadeduserInfo = loadUserInfo(loginCredential.getUsername(), "");
        statusValid(loginCredential , loadeduserInfo);
        if (loadeduserInfo != null) {
        	
            //Validate PasswordPolicy
            authenticationRealm.getPasswordPolicyValidator().passwordPolicyValid(loadeduserInfo);
            if(!isTrusted) {
                authenticationRealm.passwordMatches(loadeduserInfo, loginCredential.getPassword());
            }
            //apply PasswordSetType and resetBadPasswordCount
            authenticationRealm.getPasswordPolicyValidator().applyPasswordPolicy(loadeduserInfo);
            Authentication authentication = createOnlineSession(loginCredential,loadeduserInfo);
            
            authenticationRealm.insertLoginHistory( loadeduserInfo, 
                                                    loginCredential.getAuthType(), 
                                                    loginCredential.getProvider(), 
                                                    loginCredential.getCode(), 
                                                    loginCredential.getMessage()
                                                );
            
            return authentication;
        }else {
            String i18nMessage = WebContext.getI18nValue("login.error.username");
            _logger.debug("login user {} not in this System . {}" , 
                            loginCredential.getUsername(),i18nMessage);
            throw new BadCredentialsException(WebContext.getI18nValue("login.error.username"));
        }
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
