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
 

package org.maxkey.authn;

import java.util.ArrayList;

import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authn.online.OnlineTicketServices;
import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.domain.UserInfo;
import org.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


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
    	    AbstractOtpAuthn tfaOptAuthn,
    	    AbstractRemeberMeService remeberMeService,
    	    OnlineTicketServices onlineTicketServices) {
		this.authenticationRealm = authenticationRealm;
		this.applicationConfig = applicationConfig;
		this.tfaOptAuthn = tfaOptAuthn;
		this.remeberMeService =  remeberMeService;
		this.onlineTicketServices = onlineTicketServices;
	}

	@Override
    protected Authentication doInternalAuthenticate(LoginCredential loginCredential) {

        _logger.debug("authentication " + loginCredential);

        sessionValid(loginCredential.getSessionId());

        //jwtTokenValid(j_jwtToken);

        authTypeValid(loginCredential.getAuthType());

        captchaValid(loginCredential.getCaptcha(),loginCredential.getAuthType());

        emptyPasswordValid(loginCredential.getPassword());

        UserInfo userInfo = null;

        emptyUsernameValid(loginCredential.getUsername());

        userInfo =  loadUserInfo(loginCredential.getUsername(),loginCredential.getPassword());

        userinfoValid(userInfo, loginCredential.getPassword());

        tftcaptchaValid(loginCredential.getOtpCaptcha(),loginCredential.getAuthType(),userInfo);

        authenticationRealm.getPasswordPolicyValidator().passwordPolicyValid(userInfo);

        authenticationRealm.passwordMatches(userInfo, loginCredential.getPassword());
        
        UsernamePasswordAuthenticationToken authenticationToken = setOnline(loginCredential,userInfo);
        //RemeberMe Config check then set  RemeberMe cookies
        if (applicationConfig.getLoginConfig().isRemeberMe()) {
            if (loginCredential.getRemeberMe() != null && loginCredential.getRemeberMe().equals("remeberMe")) {
                WebContext.getSession().setAttribute(
                        WebConstants.REMEBER_ME_SESSION,loginCredential.getUsername());
                _logger.debug("do Remeber Me");
                remeberMeService.createRemeberMe(
                        userInfo.getUsername(), 
                        WebContext.getRequest(), 
                        ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
                            .getResponse()
                );
            }
        }
        
        return  authenticationToken;
    }
    
    @Override
    public Authentication basicAuthenticate(LoginCredential loginCredential) {
        UserInfo loadeduserInfo = loadUserInfo(loginCredential.getUsername(), "");
        if (loadeduserInfo != null) {
            authenticationRealm.passwordMatches(loadeduserInfo, loginCredential.getPassword());

            authenticationRealm.getPasswordPolicyValidator().passwordPolicyValid(loadeduserInfo);

            authenticationRealm.insertLoginHistory(loadeduserInfo, loginCredential.getAuthType(), "", "", "SUCCESS");
                        
            return setOnline(loginCredential,loadeduserInfo);
        }else {
            String message = WebContext.getI18nValue("login.error.username");
            _logger.debug("login user  " + loginCredential.getUsername() + " not in this System ." + message);
            throw new BadCredentialsException(WebContext.getI18nValue("login.error.username"));
        }
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
    public  Authentication trustAuthentication(String username, 
                                            String type, 
                                            String provider, 
                                            String code,
                                            String message) {
        UserInfo loadeduserInfo = loadUserInfo(username, "");
        if (loadeduserInfo != null) {
            LoginCredential loginCredential = new LoginCredential();
            loginCredential.setUsername(loadeduserInfo.getUsername());
            
            authenticationRealm.insertLoginHistory(loadeduserInfo, type, provider, code, message);
            
            return setOnline(loginCredential,loadeduserInfo);
        }else {
            String i18nMessage = WebContext.getI18nValue("login.error.username");
            _logger.debug("login user  " + username + " not in this System ." + i18nMessage);
            throw new BadCredentialsException(WebContext.getI18nValue("login.error.username"));
        }
    }
    
    public UsernamePasswordAuthenticationToken setOnline(LoginCredential credential,UserInfo userInfo) {
        //Online Tickit Id
        String onlineTickitId = WebConstants.ONLINE_TICKET_PREFIX + "-" + java.util.UUID.randomUUID().toString().toLowerCase();
        _logger.debug("set online Tickit Cookie " + onlineTickitId + " on domain "+ this.applicationConfig.getBaseDomainName());
        
        OnlineTicket onlineTicket = new OnlineTicket(onlineTickitId);
        
        //set ONLINE_TICKET cookie
        WebContext.setCookie(WebContext.getResponse(), 
                this.applicationConfig.getBaseDomainName(), 
                WebConstants.ONLINE_TICKET_NAME, 
                onlineTickitId);
        
        SigninPrincipal signinPrincipal = new SigninPrincipal(userInfo);
        //set OnlineTicket
        signinPrincipal.setOnlineTicket(onlineTicket);
        ArrayList<GrantedAuthority> grantedAuthoritys = authenticationRealm.grantAuthority(userInfo);
        signinPrincipal.setAuthenticated(true);
        
        for(GrantedAuthority administratorsAuthority : grantedAdministratorsAuthoritys) {
            if(grantedAuthoritys.contains(administratorsAuthority)) {
                signinPrincipal.setRoleAdministrators(true);
                _logger.trace("ROLE ADMINISTRATORS Authentication .");
            }
        }
        _logger.debug("Granted Authority " + grantedAuthoritys);
        
        signinPrincipal.setGrantedAuthorityApps(authenticationRealm.queryAuthorizedApps(grantedAuthoritys));
        
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        signinPrincipal, 
                        "PASSWORD", 
                        grantedAuthoritys
                );
        
        authenticationToken.setDetails(
                new WebAuthenticationDetails(WebContext.getRequest()));
        
        onlineTicket.setAuthentication(authenticationToken);
        
        this.onlineTicketServices.store(onlineTickitId, onlineTicket);
        
        /*
         *  put userInfo to current session context
         */
        WebContext.setAuthentication(authenticationToken);
        
        WebContext.setUserInfo(userInfo);
        
        return authenticationToken;
    }
  
}
