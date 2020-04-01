package org.maxkey.authn;

import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        authenticationRealm.passwordPolicyValid(userInfo);

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
}