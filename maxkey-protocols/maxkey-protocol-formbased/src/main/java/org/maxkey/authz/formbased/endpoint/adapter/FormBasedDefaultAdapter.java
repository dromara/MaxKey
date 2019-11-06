package org.maxkey.authz.formbased.endpoint.adapter;

import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.constants.BOOLEAN;
import org.maxkey.crypto.DigestUtils;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.AppsFormBasedDetails;
import org.springframework.web.servlet.ModelAndView;

public class FormBasedDefaultAdapter extends AbstractAuthorizeAdapter {

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
		modelAndView.setViewName("authorize/formbased_sso_submint");
		AppsFormBasedDetails details=(AppsFormBasedDetails)app;
		
		modelAndView.addObject("username", details.getAppUser().getRelatedUsername());
		modelAndView.addObject("password",  details.getAppUser().getRelatedPassword());
		
		if(BOOLEAN.isTrue(details.getIsExtendAttr())){
			modelAndView.addObject("extendAttr", details.getExtendAttr());
			modelAndView.addObject("isExtendAttr", true);
		}else{
			modelAndView.addObject("isExtendAttr", false);
		}
		
		modelAndView.addObject("action", details.getRedirectUri());
		modelAndView.addObject("usernameMapping", details.getUsernameMapping());
		
		String passwordAlgorithm=null;
		String passwordMapping=details.getPasswordMapping();
		if(passwordMapping.indexOf(":")>-1){
			passwordAlgorithm=passwordMapping.substring(passwordMapping.indexOf(":")+1).toUpperCase();
			details.setPasswordMapping(passwordMapping.substring(0, passwordMapping.indexOf(":")));
		}
		modelAndView.addObject("passwordMapping", details.getPasswordMapping());
		
		if(null==passwordAlgorithm){
			modelAndView.addObject("password",  details.getAppUser().getRelatedPassword());
		}else if(passwordAlgorithm.indexOf("HEX")>-1){
			modelAndView.addObject("password",  DigestUtils.digestHex(details.getAppUser().getRelatedPassword(),passwordAlgorithm.substring(0, passwordAlgorithm.indexOf("HEX"))));
		}else{
			modelAndView.addObject("password",  DigestUtils.digestBase64(details.getAppUser().getRelatedPassword(),passwordAlgorithm));
		}
		
		if(details.getAuthorizeView()!=null&&!details.getAuthorizeView().equals("")){
			modelAndView.setViewName("authorize/"+details.getAuthorizeView());
		}
		
		return modelAndView;
	}

}
