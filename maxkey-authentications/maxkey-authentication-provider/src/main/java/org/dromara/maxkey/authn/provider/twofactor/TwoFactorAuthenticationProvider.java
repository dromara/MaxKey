

 

package org.dromara.maxkey.authn.provider.twofactor;

import java.util.HashMap;
import java.util.Map;

import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.constants.ConstsJwt;
import org.dromara.maxkey.constants.ConstsLoginType;
import org.dromara.maxkey.constants.ConstsTwoFactor;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.LoginService;
import org.dromara.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import com.nimbusds.jwt.JWTClaimsSet;

/**
 * TwoFactor Authentication provider.双因素认证提供者
 * 
 * @author Crystal.Sea
 *
 */
public class TwoFactorAuthenticationProvider extends AbstractAuthenticationProvider {
    private static final Logger logger =LoggerFactory.getLogger(TwoFactorAuthenticationProvider.class);

    Map<String,AbstractAuthenticationProvider> twoFactorProvider = new HashMap<>();
    
    public String getProviderName() {
        return "twoFactor" + PROVIDER_SUFFIX;
    }
    
    public TwoFactorAuthenticationProvider(
    		AbstractAuthenticationRealm authenticationRealm,
    	    SessionManager sessionManager,
    	    LoginService loginService,
    	    AuthTokenService authTokenService) {
		this.authenticationRealm = authenticationRealm;
		this.sessionManager = sessionManager;
		this.authTokenService = authTokenService;
	}
    
    public void addProvider(int twoFactor,AbstractAuthenticationProvider provider) {
    	twoFactorProvider.put(twoFactor+"", provider);
    }

    @Override
	public Authentication doAuthenticate(LoginCredential credential) {
    	logger.debug("Credential {}" , credential);
		emptyOtpCaptchaValid(credential.getOtpCaptcha());
        try {
	        if(authTokenService.validateJwtToken(credential.getJwtToken())) {
	 			//解析refreshToken，转换会话id
	 			JWTClaimsSet claim = authTokenService.resolve(credential.getJwtToken());
	 			String sessionId = claim.getJWTID();
	 			String userId = claim.getClaim(ConstsJwt.USER_ID).toString();
	 			//String style = claim.getClaim(AuthorizationUtils.STYLE).toString();
	 			//尝试刷新会话
	 			logger.trace("Try to  get user {} , sessionId [{}]" , userId, sessionId);
	 			Session session  = sessionManager.getTwoFactor(sessionId);
		 		if(session != null) {//有会话
		 			Authentication twoFactorAuth  = null;
		 			SignPrincipal principal =(SignPrincipal)  session.getAuthentication().getPrincipal();
		 			String loginType;
		 			switch(principal.getTwoFactor()) {
			 			case ConstsTwoFactor.TOTP -> {
			 				loginType = ConstsLoginType.TwoFactor.TWO_FACTOR_TOTP;
			 			}
			 			case ConstsTwoFactor.EMAIL -> {
			 				loginType = ConstsLoginType.TwoFactor.TWO_FACTOR_EMAIL;
			 			}
			 			case ConstsTwoFactor.SMS -> {
			 				loginType = ConstsLoginType.TwoFactor.TWO_FACTOR_MOBILE;
			 			}
			 			default ->{
			 				loginType = ConstsLoginType.TwoFactor.TWO_FACTOR_TOTP;
			 			}
		 			}
		 			logger.debug("loginType {}",loginType);
		 			AbstractAuthenticationProvider authenticationProvider = twoFactorProvider.get(principal.getTwoFactor()+"");
		 			logger.debug("Provider {}",authenticationProvider.getProviderName());
		 			UserInfo user = authenticationRealm.loadUserInfoById(userId);
		 			//进行二次认证校验
		 			twoFactorAuth = authenticationProvider.doTwoFactorAuthenticate(credential , user);

		 			if(twoFactorAuth != null) {
		 				logger.debug("twoFactorAuth success .");
			 			//设置正常状态
			 			principal.clearTwoFactor();
			 			//重新设置令牌参数
			 			sessionManager.create(sessionId, session);
			 			sessionManager.removeTwoFactor(sessionId);
			 			AuthorizationUtils.setAuthentication(session.getAuthentication());
			 			authenticationRealm.insertLoginHistory(user, 
			 					loginType, 
				                "", 
				                "xe00000004",
				                WebConstants.LOGIN_RESULT.SUCCESS);
			 			return session.getAuthentication();
		 			}else {
		 				logger.debug("twoFactorAuth fail .");
		 			}
		 		}else {//无会话
		 			logger.debug("Session is timeout , sessionId [{}]" , sessionId);
		 		}
	 		}else {//验证失效
	 			logger.debug("jwt token is not validate .");
	 		}
        }catch(Exception e) {
        	logger.error("Exception !",e);
 		}
        
        return  null;
    }
    
}
