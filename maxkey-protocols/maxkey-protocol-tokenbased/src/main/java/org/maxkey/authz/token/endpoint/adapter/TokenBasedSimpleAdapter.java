package org.maxkey.authz.token.endpoint.adapter;

import java.util.Date;

import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.constants.BOOLEAN;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.TokenBasedDetails;
import org.maxkey.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class TokenBasedSimpleAdapter extends AbstractAuthorizeAdapter {
	final static Logger _logger = LoggerFactory.getLogger(TokenBasedSimpleAdapter.class);
	@Override
	public String generateInfo(UserInfo userInfo,Object app) {
		TokenBasedDetails details=(TokenBasedDetails)app;
	
		String tokenUsername="";
		
		if(BOOLEAN.isTrue(details.getUid())){
			tokenUsername=userInfo.getId();
		}else if(BOOLEAN.isTrue(details.getUsername())){
			tokenUsername= userInfo.getUsername();	
		}else if(BOOLEAN.isTrue(details.getEmail())){
			tokenUsername=userInfo.getEmail();
		}else if(BOOLEAN.isTrue(details.getWindowsAccount())){
			tokenUsername= userInfo.getWindowsAccount();
		}else if(BOOLEAN.isTrue(details.getEmployeeNumber())){
			tokenUsername=userInfo.getEmployeeNumber();
		}else if(BOOLEAN.isTrue(details.getDepartmentId())){
			tokenUsername= userInfo.getDepartmentId();
		}
		
		/*
		 * use UTC date time format
		 */
		Date currentDate=new Date();
		_logger.debug("UTC Local current date : "+DateUtils.toUtcLocal(currentDate));
		_logger.debug("UTC  current Date : "+DateUtils.toUtc(currentDate));
		
		
		String tokenString=tokenUsername+"@@"+DateUtils.toUtc(currentDate);
		_logger.debug("Token : "+tokenString);
		
		return tokenString;
	}

	@Override
	public String encrypt(String data, String algorithmKey, String algorithm) {
		return super.encrypt(data, algorithmKey, algorithm);
	}

	@Override
	public ModelAndView authorize(UserInfo userInfo, Object app, String data,ModelAndView modelAndView) {
		modelAndView.setViewName("authorize/tokenbased_sso_submint");
		TokenBasedDetails details=(TokenBasedDetails)app;
		modelAndView.addObject("action", details.getRedirectUri());
		
		modelAndView.addObject("token",data);
		
		return modelAndView;
	}

}
