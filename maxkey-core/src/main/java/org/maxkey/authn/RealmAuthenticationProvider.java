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
import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @Override
    protected Authentication doInternalAuthenticate(Authentication authentication) {
        BasicAuthentication auth = (BasicAuthentication)authentication;

        _logger.debug("authentication " + auth);

        sessionValid(auth.getSessionId());

        //jwtTokenValid(j_jwtToken);

        authTypeValid(auth.getAuthType());

        captchaValid(auth.getCaptcha(),auth.getAuthType());

        emptyPasswordValid(auth.getPassword());

        UserInfo userInfo = null;

        emptyUsernameValid(auth.getUsername());

        userInfo =  loadUserInfo(auth.getUsername(),auth.getPassword());

        userinfoValid(userInfo, auth.getPassword());

        tftcaptchaValid(auth.getOtpCaptcha(),auth.getAuthType(),userInfo);

        authenticationRealm.getPasswordPolicyValidator().passwordPolicyValid(userInfo);

        authenticationRealm.passwordMatches(userInfo, auth.getPassword());
        
        UsernamePasswordAuthenticationToken authenticationToken = setOnline(auth,userInfo);
        //RemeberMe Config check then set  RemeberMe cookies
        if (applicationConfig.getLoginConfig().isRemeberMe()) {
            if (auth.getRemeberMe() != null && auth.getRemeberMe().equals("remeberMe")) {
                WebContext.getSession().setAttribute(
                        WebConstants.REMEBER_ME_SESSION,auth.getUsername());
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
    public Authentication basicAuthenticate(Authentication authentication) {
        BasicAuthentication auth = (BasicAuthentication) authentication;
        UserInfo loadeduserInfo = loadUserInfo(auth.getUsername(), "");
        if (loadeduserInfo != null) {
            authenticationRealm.passwordMatches(loadeduserInfo, auth.getPassword());

            authenticationRealm.getPasswordPolicyValidator().passwordPolicyValid(loadeduserInfo);

            authenticationRealm.insertLoginHistory(loadeduserInfo, auth.getAuthType(), "", "", "SUCCESS");
                        
            return setOnline(auth,loadeduserInfo);
        }else {
            String message = WebContext.getI18nValue("login.error.username");
            _logger.debug("login user  " + auth.getUsername() + " not in this System ." + message);
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
            BasicAuthentication auth = new BasicAuthentication();
            auth.setUsername(loadeduserInfo.getUsername());
            
            authenticationRealm.insertLoginHistory(loadeduserInfo, type, provider, code, message);
            
            return setOnline(auth,loadeduserInfo);
        }else {
            String i18nMessage = WebContext.getI18nValue("login.error.username");
            _logger.debug("login user  " + username + " not in this System ." + i18nMessage);
            throw new BadCredentialsException(WebContext.getI18nValue("login.error.username"));
        }
    }
    
    public UsernamePasswordAuthenticationToken setOnline(BasicAuthentication authentication,UserInfo userInfo) {
        //Online Tickit Id
        String onlineTickitId = WebConstants.ONLINE_TICKET_PREFIX + "-" + java.util.UUID.randomUUID().toString().toLowerCase();
        _logger.debug("set online Tickit Cookie " + onlineTickitId + " on domain "+ this.applicationConfig.getBaseDomainName());
        
        WebContext.setCookie(WebContext.getResponse(), 
                this.applicationConfig.getBaseDomainName(), 
                WebConstants.ONLINE_TICKET_NAME, 
                onlineTickitId, 
                0);
        
        //set OnlineTicket
        OnlineTicket onlineTicket = new OnlineTicket(onlineTickitId,authentication);
        this.onlineTicketServices.store(onlineTickitId, onlineTicket);
        authentication.setOnlineTicket(onlineTicket);
        ArrayList<GrantedAuthority> grantedAuthoritys = authenticationRealm.grantAuthority(userInfo);
        //set default roles
        grantedAuthoritys.add(new SimpleGrantedAuthority("ROLE_USER"));
        grantedAuthoritys.add(new SimpleGrantedAuthority("ROLE_ORDINARY_USER"));
        
        authentication.setAuthenticated(true);
        
        for(GrantedAuthority administratorsAuthority : grantedAdministratorsAuthoritys) {
            if(grantedAuthoritys.contains(administratorsAuthority)) {
                authentication.setRoleAdministrators(true);
                _logger.trace("ROLE ADMINISTRATORS Authentication .");
            }
        }
        
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authentication, 
                        "PASSWORD", 
                        grantedAuthoritys
                );
        
        authenticationToken.setDetails(
                new WebAuthenticationDetails(WebContext.getRequest()));
        
        /*
         *  put userInfo to current session context
         */
        WebContext.setAuthentication(authenticationToken);
        
        userInfo.setOnlineTicket(onlineTicket);
        WebContext.setUserInfo(userInfo);
        
        return authenticationToken;
    }
  
}
