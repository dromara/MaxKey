package org.maxkey.authz.cas.endpoint.adapter;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.maxkey.authz.cas.endpoint.response.ServiceResponseBuilder;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.domain.UserInfo;
import org.springframework.web.servlet.ModelAndView;

public class CasDefaultAdapter extends AbstractAuthorizeAdapter {
	
	static String Charset_UTF8="UTF-8";
	
	@Override
	public ModelAndView authorize(UserInfo userInfo, Object app, String data, ModelAndView modelAndView) {

		return null;
	}

	public String base64Attr(String attrValue){
		String b64="";
		try {
			b64="base64:"+Base64.encodeBase64String(attrValue.getBytes(Charset_UTF8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return b64;
	}
	
	@Override
	public String generateInfo(UserInfo userInfo, Object serviceResponseObject) {
		ServiceResponseBuilder serviceResponseBuilder=(ServiceResponseBuilder)serviceResponseObject;
		//for user
		serviceResponseBuilder.setAttribute("uid", userInfo.getId());
		serviceResponseBuilder.setAttribute("displayName", base64Attr(userInfo.getDisplayName()));
		serviceResponseBuilder.setAttribute("firstName", base64Attr(userInfo.getGivenName()));
		serviceResponseBuilder.setAttribute("lastname", base64Attr(userInfo.getFamilyName()));
		serviceResponseBuilder.setAttribute("mobile", userInfo.getMobile());
		serviceResponseBuilder.setAttribute("birthday", userInfo.getBirthDate());
		serviceResponseBuilder.setAttribute("gender", userInfo.getGender()+"");
		
		//for work
		serviceResponseBuilder.setAttribute("employeeNumber", userInfo.getEmployeeNumber());
		serviceResponseBuilder.setAttribute("title", base64Attr(userInfo.getJobTitle()));
		serviceResponseBuilder.setAttribute("email", userInfo.getWorkEmail());
		serviceResponseBuilder.setAttribute("department", base64Attr(userInfo.getDepartment()));
		serviceResponseBuilder.setAttribute("departmentId", userInfo.getDepartmentId());
		serviceResponseBuilder.setAttribute("workRegion",base64Attr(userInfo.getWorkRegion()));
		
	
		return null;
	}

}
