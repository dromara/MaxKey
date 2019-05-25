package org.maxkey.authz.formbased.endpoint.adapter;

import java.util.Date;

import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.crypto.DigestUtils;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.FormBasedDetails;
import org.springframework.web.servlet.ModelAndView;

public class FormBasedNeteaseNoteYoudaoAdapter extends AbstractAuthorizeAdapter {

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
		modelAndView.setViewName("authorize/formbased_netease_noteyoudao_sso_submint");
		FormBasedDetails details=(FormBasedDetails)app;
		modelAndView.addObject("username", details.getAppUser().getRelatedUsername());
		modelAndView.addObject("password",  DigestUtils.md5Hex(details.getAppUser().getRelatedPassword()));
		modelAndView.addObject("currentTime",  (new Date()).getTime());
		
		/*
		if(BOOLEAN.isTrue(details.getIsExtendAttr())){
			modelAndView.addObject("extendAttr", details.getExtendAttr());
			modelAndView.addObject("isExtendAttr", true);
		}else{
			modelAndView.addObject("isExtendAttr", false);
		}
		
		modelAndView.addObject("action", details.getRedirectUri());
		modelAndView.addObject("usernameMapping", details.getUsernameMapping());
		modelAndView.addObject("passwordMapping", details.getPasswordMapping());
		*/
		return modelAndView;
	}

}
