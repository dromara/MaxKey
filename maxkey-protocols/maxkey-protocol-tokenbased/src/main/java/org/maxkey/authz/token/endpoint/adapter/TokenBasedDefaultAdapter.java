/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.maxkey.authz.token.endpoint.adapter;

import java.util.Date;
import java.util.HashMap;

import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.AppsTokenBasedDetails;
import org.maxkey.util.DateUtils;
import org.maxkey.util.JsonUtils;
import org.maxkey.util.StringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class TokenBasedDefaultAdapter extends AbstractAuthorizeAdapter {
	final static Logger _logger = LoggerFactory.getLogger(TokenBasedDefaultAdapter.class);
	@Override
	public String generateInfo(UserInfo userInfo,Object app) {
		AppsTokenBasedDetails details=(AppsTokenBasedDetails)app;
		HashMap<String,String> beanMap=new HashMap<String,String>();
		
		beanMap.put("randomId",(new StringGenerator()).uuidGenerate());
		if(details.getUserPropertys()!=null && !details.getUserPropertys().equals("")) {
		    
    		if(details.getUserPropertys().indexOf("uid")>-1){
                beanMap.put("uid",userInfo.getId());
            }
    		
    		if(details.getUserPropertys().indexOf("username")>-1){
                beanMap.put("username",userInfo.getUsername());
            }
    		
    		if(details.getUserPropertys().indexOf("email")>-1){
                beanMap.put("email",userInfo.getEmail());
            }
    		
    		if(details.getUserPropertys().indexOf("windowsAccount")>-1){
                beanMap.put("windowsAccount",userInfo.getWindowsAccount());
            }
    		
    		if(details.getUserPropertys().indexOf("employeeNumber")>-1){
                beanMap.put("employeeNumber",userInfo.getEmployeeNumber());
            }
    		
    		if(details.getUserPropertys().indexOf("department")>-1){
                beanMap.put("department",userInfo.getDepartment());
            }
    		
    		if(details.getUserPropertys().indexOf("departmentId")>-1){
                beanMap.put("departmentId",userInfo.getDepartmentId());
            }
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
		modelAndView.setViewName("authorize/tokenbased_sso_submint");
		AppsTokenBasedDetails details=(AppsTokenBasedDetails)app;
		modelAndView.addObject("action", details.getRedirectUri());
		
		modelAndView.addObject("token",data );
		return modelAndView;
	}
}
