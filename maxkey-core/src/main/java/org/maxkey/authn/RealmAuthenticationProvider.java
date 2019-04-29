package org.maxkey.authn;

import javax.servlet.http.HttpSession;

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
        return "UserInfo";
    }
    
    @Override
    protected Authentication doInternalAuthenticate(Authentication authentication) {
    	HttpSession session = WebContext.getSession();
        // All your user authentication needs
        String j_username = (String) authentication.getPrincipal();
        String j_password = (String) authentication.getCredentials();
        String j_sessionId=WebContext.getRequest().getParameter("j_sessionid");
        String j_captcha=WebContext.getRequest().getParameter("j_captcha");
        String j_otp_captcha=WebContext.getRequest().getParameter("j_otp_captcha");
        String j_remeberMe=WebContext.getRequest().getParameter("j_remeberMe");
        String j_auth_type=WebContext.getRequest().getParameter("j_auth_type");
        String j_jwtToken=WebContext.getRequest().getParameter("j_jwttoken");

		_logger.info("principal : "+j_username);
		_logger.info("credentials : PROTECTED");
		_logger.info("j_auth_type input : "+j_auth_type);
		_logger.info("captcha input : "+j_captcha);
		_logger.info("j_otp_captcha input : "+j_otp_captcha);
        _logger.info("sessionId : "+j_sessionId);
    	_logger.info("session getId() : "+session.getId());
    	_logger.info("Authentication principal :"+authentication.getName()+" , credentials : ********");
 
    	sessionValid(j_sessionId);
    	
    	jwtTokenValid(j_jwtToken);
    	
    	authTypeValid(j_auth_type);
    	
    	captchaValid(j_captcha,j_auth_type);
    	
    	emptyPasswordValid(j_password);
    	
    	UserInfo userInfo = null;
    	
		emptyUsernameValid(j_username);
		
		userInfo= loadUserInfo(j_username,j_password);
    	
    	userinfoValid(userInfo, j_username);
    	
    	tftcaptchaValid(j_otp_captcha,j_auth_type,userInfo);
    	
    	authenticationRealm.passwordPolicyValid(userInfo);
    	
    	authenticationRealm.passwordMatches(userInfo, j_password);
    	/**
    	 *  put userInfo to current session context
    	 */
	    WebContext.setUserInfo(userInfo);
	    
	    if(applicationConfig.getLoginConfig().isRemeberMe()){
		    if(j_remeberMe!=null&&j_remeberMe.equals("remeberMe")){
		    	WebContext.getSession().setAttribute(WebConstants.REMEBER_ME_SESSION,j_username);
		    	_logger.debug("do Remeber Me");
		    }
	    }
	    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =new UsernamePasswordAuthenticationToken(
				userInfo,
				j_password,
				authenticationRealm.grantAuthorityAndNavs(userInfo));
	    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(WebContext.getRequest()));
	    
    	return usernamePasswordAuthenticationToken;
    }
}