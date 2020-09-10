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

import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        authenticationRealm.grantAuthority(userInfo);
        /*
         *  put userInfo to current session context
         */
        WebContext.setUserInfo(userInfo);

        auth.setAuthenticated(true);

        if (auth.isAuthenticated() && applicationConfig.getLoginConfig().isRemeberMe()) {
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

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                auth,
                "PASSWORD",
                authenticationRealm.grantAuthority(userInfo));
        usernamePasswordAuthenticationToken.setDetails(
                new WebAuthenticationDetails(WebContext.getRequest()));

        return usernamePasswordAuthenticationToken;
    }
    
    @Override
    public Authentication basicAuthenticate(Authentication authentication) {
        BasicAuthentication basicAuth = (BasicAuthentication) authentication;
        UserInfo loadeduserInfo = loadUserInfo(basicAuth.getUsername(), "");
        if (loadeduserInfo != null) {

            authenticationRealm.passwordMatches(loadeduserInfo, basicAuth.getPassword());

            authenticationRealm.getPasswordPolicyValidator().passwordPolicyValid(loadeduserInfo);

            WebContext.setUserInfo(loadeduserInfo);

            authentication.setAuthenticated(true);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authentication, "PASSWORD", authenticationRealm.grantAuthority(loadeduserInfo));

            WebContext.setAuthentication(authenticationToken);
            WebContext.setUserInfo(loadeduserInfo);
            authenticationRealm.insertLoginHistory(loadeduserInfo, basicAuth.getAuthType(), "", "", "SUCCESS");

            return authenticationToken;
        }else {
            String message = WebContext.getI18nValue("login.error.username");
            _logger.debug("login user  " + basicAuth.getUsername() + " not in this System ." + message);
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
            WebContext.setUserInfo(loadeduserInfo);
            BasicAuthentication authentication = new BasicAuthentication();
            authentication.setUsername(loadeduserInfo.getUsername());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            authentication, 
                            "PASSWORD", 
                            authenticationRealm.grantAuthority(loadeduserInfo)
                    );

            authentication.setAuthenticated(true);
            WebContext.setAuthentication(authenticationToken);
            WebContext.setUserInfo(loadeduserInfo);

            authenticationRealm.insertLoginHistory(loadeduserInfo, type, provider, code, message);
            
            return authenticationToken;
        }else {
            String i18nMessage = WebContext.getI18nValue("login.error.username");
            _logger.debug("login user  " + username + " not in this System ." + i18nMessage);
            throw new BadCredentialsException(WebContext.getI18nValue("login.error.username"));
        }
    }
  
}
