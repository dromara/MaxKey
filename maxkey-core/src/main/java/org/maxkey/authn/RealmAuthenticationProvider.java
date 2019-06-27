package org.maxkey.authn;

import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;


/**
 * database Authentication provider
 * @author Crystal.Sea
 *
 */
public class RealmAuthenticationProvider extends AbstractAuthenticationProvider {
	
    private static final Logger _logger = LoggerFactory.getLogger(RealmAuthenticationProvider.class);

    protected String getProviderName() {
        return "RealmAuthenticationProvider";
    }
    
    @Override
    protected Authentication doInternalAuthenticate(Authentication authentication) {
    	BasicAuthentication auth =(BasicAuthentication)authentication;

    	_logger.debug("authentication "+auth);
 
    	sessionValid(auth.getJ_sessionid());
    	
    	//jwtTokenValid(j_jwtToken);
    	
    	authTypeValid(auth.getJ_auth_type());
    	
    	captchaValid(auth.getJ_captcha(),auth.getJ_auth_type());
    	
    	emptyPasswordValid(auth.getJ_password());
    	
    	UserInfo userInfo = null;
    	
		emptyUsernameValid(auth.getJ_username());
		
		userInfo= loadUserInfo(auth.getJ_username(),auth.getJ_password());
    	
    	userinfoValid(userInfo, auth.getJ_password());
    	
    	tftcaptchaValid(auth.getJ_otp_captcha(),auth.getJ_auth_type(),userInfo);
    	
    	authenticationRealm.passwordPolicyValid(userInfo);
    	
    	authenticationRealm.passwordMatches(userInfo, auth.getJ_password());
    	authenticationRealm.grantAuthority(userInfo);
    	/**
    	 *  put userInfo to current session context
    	 */
	    WebContext.setUserInfo(userInfo);
	    
	    if(applicationConfig.getLoginConfig().isRemeberMe()){
		    if(auth.getJ_remeberme()!=null&&auth.getJ_remeberme().equals("remeberMe")){
		    	WebContext.getSession().setAttribute(WebConstants.REMEBER_ME_SESSION,auth.getJ_username());
		    	_logger.debug("do Remeber Me");
		    }
	    }

	    auth.setAuthenticated(true);
	    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =new UsernamePasswordAuthenticationToken(
	    		auth,
				"PASSWORD",
				authenticationRealm.grantAuthority(userInfo));
	    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(WebContext.getRequest()));
	    
    	return usernamePasswordAuthenticationToken;
    }
}