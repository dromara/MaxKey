package org.maxkey.authz.formbased.endpoint.adapter;

import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.constants.BOOLEAN;
import org.maxkey.crypto.DigestUtils;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.FormBasedDetails;
import org.maxkey.web.WebContext;
import org.springframework.web.servlet.ModelAndView;

public class FormBasedRedirectAdapter extends AbstractAuthorizeAdapter {

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
		modelAndView.setViewName("authorize/formbased_redirect_submint");
		FormBasedDetails details=(FormBasedDetails)app;
		
		modelAndView.addObject("id", details.getId());
		modelAndView.addObject("action", details.getRedirectUri());
		modelAndView.addObject("loginUrl", details.getLoginUrl());
		modelAndView.addObject("usernameMapping", details.getUsernameMapping());
		
		String passwordAlgorithm=null;
		String passwordMapping=details.getPasswordMapping();
		if(passwordMapping.indexOf(":")>-1){
			passwordAlgorithm=passwordMapping.substring(passwordMapping.indexOf(":")+1).toUpperCase();
			details.setPasswordMapping(passwordMapping.substring(0, passwordMapping.indexOf(":")));
		}
		modelAndView.addObject("passwordMapping", details.getPasswordMapping());
		
		modelAndView.addObject("username", details.getAppUser().getRelatedUsername());
		if(null==passwordAlgorithm){
			modelAndView.addObject("password",  details.getAppUser().getRelatedPassword());
		}else if(passwordAlgorithm.indexOf("HEX")>-1){
			modelAndView.addObject("password",  DigestUtils.digestHex(details.getAppUser().getRelatedPassword(),passwordAlgorithm.substring(0, passwordAlgorithm.indexOf("HEX"))));
		}else{
			modelAndView.addObject("password",  DigestUtils.digestBase64(details.getAppUser().getRelatedPassword(),passwordAlgorithm));
		}
		
		
		if(WebContext.getAttribute("formbased_redirect_submint")==null){
			modelAndView.setViewName("authorize/formbased_redirect_submint");
			WebContext.setAttribute("formbased_redirect_submint", "formbased_redirect_submint");
		}else{
			modelAndView.setViewName("authorize/formbased_redirect_post_submint");
			if(details.getAuthorizeView()!=null&&!details.getAuthorizeView().equals("")){
				modelAndView.setViewName("authorize/"+details.getAuthorizeView());
			}
			WebContext.removeAttribute("formbased_redirect_submint");
		}
		
		
		if(BOOLEAN.isTrue(details.getIsExtendAttr())){
			modelAndView.addObject("extendAttr", details.getExtendAttr());
			modelAndView.addObject("isExtendAttr", true);
		}else{
			modelAndView.addObject("isExtendAttr", false);
		}
		
		return modelAndView;
	}

}
