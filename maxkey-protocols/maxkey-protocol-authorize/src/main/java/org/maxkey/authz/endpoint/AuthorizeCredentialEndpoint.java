/**
 * 
 */
package org.maxkey.authz.endpoint;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.Accounts;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Crystal.Sea
 *
 */
@Controller
public class AuthorizeCredentialEndpoint extends AuthorizeBaseEndpoint{

	@RequestMapping("/authz/credential/forward")
	public ModelAndView authorizeCredentialForward(
			@RequestParam("appId") String appId,
			@RequestParam("redirect_uri") String redirect_uri){
		ModelAndView modelAndView=new ModelAndView("authorize/init_sso_credential");
		modelAndView.addObject("username", "");
		modelAndView.addObject("password", "");
		modelAndView.addObject("setpassword", true);
		modelAndView.addObject("uid", WebContext.getUserInfo().getId());
		modelAndView.addObject("appId", appId);
		modelAndView.addObject("redirect_uri", redirect_uri);
		return modelAndView;
	}
	
	@RequestMapping("/authz/credential")
	public ModelAndView authorizeCredential(
			HttpServletRequest request,
			@RequestParam("uid") String uid,
			@RequestParam("appId") String appId,
			@RequestParam("identity_username") String identity_username,
			@RequestParam("identity_password") String identity_password,
			@RequestParam("redirect_uri") String redirect_uri){
		
		if(StringUtils.isNotEmpty(identity_username)&&StringUtils.isNotEmpty(identity_password)){
			Accounts appUser =new Accounts ();
			UserInfo userInfo=WebContext.getUserInfo();
			appUser.setId(appUser.generateId());
			
			appUser.setUid(userInfo.getId());
			appUser.setUsername(userInfo.getUsername());
			appUser.setDisplayName(userInfo.getDisplayName());
			
			appUser.setAppId(appId);
			appUser.setAppName(getApplication(appId).getName());
			
			appUser.setRelatedUsername(identity_username);
			appUser.setRelatedPassword(ReciprocalUtils.encode(identity_password));
			
			if(accountsService.insert(appUser)){
				
			}
		}
		
		return WebContext.redirect(redirect_uri);
	}
			
}
