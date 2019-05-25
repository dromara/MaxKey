package org.maxkey.authz.formbased.endpoint.adapter;

import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.constants.BOOLEAN;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.FormBasedDetails;
import org.springframework.web.servlet.ModelAndView;

public class FormBasedNetease163EmailAdapter extends AbstractAuthorizeAdapter {

	@Override
	public String generateInfo(UserInfo userInfo,Object app) {
		return null;
	}

	@Override
	public String encrypt(String data, String algorithmKey, String algorithm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView authorize(UserInfo userInfo, Object app, String data,ModelAndView modelAndView) {
		modelAndView.setViewName("authorize/formbased_netease_163email_sso_submint");
		FormBasedDetails details=(FormBasedDetails)app;
		modelAndView.addObject("username", details.getAppUser().getRelatedUsername().substring(details.getAppUser().getRelatedUsername().indexOf("@")));
		modelAndView.addObject("email", details.getAppUser().getRelatedUsername());
		modelAndView.addObject("password",  details.getAppUser().getRelatedPassword());
		
		if(BOOLEAN.isTrue(details.getIsExtendAttr())){
			modelAndView.addObject("extendAttr", details.getExtendAttr());
			modelAndView.addObject("isExtendAttr", true);
		}else{
			modelAndView.addObject("isExtendAttr", false);
		}
		
		modelAndView.addObject("action", details.getRedirectUri());
		modelAndView.addObject("usernameMapping", details.getUsernameMapping());
		modelAndView.addObject("passwordMapping", details.getPasswordMapping());
		return modelAndView;
	}

}
