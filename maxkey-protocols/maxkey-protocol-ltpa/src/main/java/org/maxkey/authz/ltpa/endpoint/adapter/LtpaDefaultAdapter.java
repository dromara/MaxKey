package org.maxkey.authz.ltpa.endpoint.adapter;

import java.util.Date;
import java.util.HashMap;

import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.constants.BOOLEAN;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.AppsTokenBasedDetails;
import org.maxkey.util.DateUtils;
import org.maxkey.util.JsonUtils;
import org.maxkey.util.StringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class LtpaDefaultAdapter extends AbstractAuthorizeAdapter {
	final static Logger _logger = LoggerFactory.getLogger(LtpaDefaultAdapter.class);
	@Override
	public String generateInfo(UserInfo userInfo,Object app) {
		AppsTokenBasedDetails details=(AppsTokenBasedDetails)app;
		HashMap<String,String> beanMap=new HashMap<String,String>();
		
		beanMap.put("randomId",(new StringGenerator()).uuidGenerate());
		
		if(BOOLEAN.isTrue(details.getUid())){
			beanMap.put("uid",userInfo.getId());
		}
		if(BOOLEAN.isTrue(details.getUsername())){
			beanMap.put("username", userInfo.getUsername());	
		}
		if(BOOLEAN.isTrue(details.getEmail())){
			beanMap.put("email", userInfo.getEmail());
		}
		if(BOOLEAN.isTrue(details.getWindowsAccount())){
			beanMap.put("windowsAccount", userInfo.getWindowsAccount());
		}
		if(BOOLEAN.isTrue(details.getEmployeeNumber())){
			beanMap.put("employeeNumber", userInfo.getEmployeeNumber());
		}
		if(BOOLEAN.isTrue(details.getDepartmentId())){
			beanMap.put("departmentId", userInfo.getDepartmentId());
		}
		if(BOOLEAN.isTrue(details.getDepartment())){
			beanMap.put("department", userInfo.getDepartment());
		}
		
		beanMap.put("displayName", userInfo.getDisplayName());
		
		/*
		 * use UTC date time format
		 * current date plus expires minute 
		 */
		Integer expiresLong=Integer.parseInt(details.getExpires());
		Date currentDate=new Date();
		Date expiresDate=DateUtils.addMinutes(currentDate,expiresLong);
		String expiresString=DateUtils.toUtc(expiresDate);
		_logger.debug("UTC Local current date : "+DateUtils.toUtcLocal(currentDate));
		_logger.debug("UTC  current Date : "+DateUtils.toUtc(currentDate));
		_logger.debug("UTC  expires Date : "+DateUtils.toUtc(expiresDate));
		
		beanMap.put("at", DateUtils.toUtc(currentDate));
		
		beanMap.put("expires", expiresString);
		
		String jsonString=JsonUtils.object2Json(beanMap);
		_logger.debug("Token : "+jsonString);
		
		return jsonString;
	}

	@Override
	public String encrypt(String data, String algorithmKey, String algorithm) {
		return super.encrypt(data, algorithmKey, algorithm);
	}

	@Override
	public ModelAndView authorize(UserInfo userInfo, Object app, String data,ModelAndView modelAndView) {
		// TODO Auto-generated method stub
		return null;
	}

}
