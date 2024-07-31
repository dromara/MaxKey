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
 

package org.dromara.maxkey.authz.token.endpoint.adapter;

import java.util.Date;

import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.entity.apps.AppsTokenBasedDetails;
import org.dromara.maxkey.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class TokenBasedSimpleAdapter extends AbstractAuthorizeAdapter {
	static final  Logger _logger = LoggerFactory.getLogger(TokenBasedSimpleAdapter.class);
	String token = "";
	
	@Override
	public Object generateInfo() {
		AppsTokenBasedDetails details=(AppsTokenBasedDetails)app;
	
		String tokenUsername = userInfo.getUsername();
		
		if(details.getUserPropertys()!=null && !details.getUserPropertys().equals("")) {
    		if(details.getUserPropertys().indexOf("userId")>-1){
    			tokenUsername=userInfo.getId();
    		}else if(details.getUserPropertys().indexOf("username")>-1){
    			tokenUsername= userInfo.getUsername();	
    		}else if(details.getUserPropertys().indexOf("email")>-1){
    			tokenUsername=userInfo.getEmail();
    		}else if(details.getUserPropertys().indexOf("windowsAccount")>-1){
    			tokenUsername= userInfo.getWindowsAccount();
    		}else if(details.getUserPropertys().indexOf("employeeNumber")>-1){
    			tokenUsername=userInfo.getEmployeeNumber();
    		}else if(details.getUserPropertys().indexOf("department")>-1){
    			tokenUsername= userInfo.getDepartmentId();
    		}else if(details.getUserPropertys().indexOf("departmentId")>-1){
                tokenUsername= userInfo.getDepartment();
            }
		}

		/*
		 * use UTC date time format
		 */
		Date currentDate=new Date();
		_logger.debug("UTC Local current date : "+DateUtils.toUtcLocal(currentDate));
		_logger.debug("UTC  current Date : "+DateUtils.toUtc(currentDate));
		
		
		token = tokenUsername+"@@"+DateUtils.toUtc(currentDate);
		_logger.debug("Token : {}",token);
		
		return token;
	}

	@Override
	public Object encrypt(Object data, String algorithmKey, String algorithm) {
		token = super.encrypt(token, algorithmKey, algorithm).toString();
		return token;
	}

	@Override
	public ModelAndView authorize(ModelAndView modelAndView) {
		modelAndView.setViewName("authorize/tokenbased_sso_submint");
		AppsTokenBasedDetails details=(AppsTokenBasedDetails)app;
		modelAndView.addObject("action", details.getRedirectUri());
		
		modelAndView.addObject("token",token);
		
		return modelAndView;
	}

	@Override
	public String serialize() {
		return token;
	}
}
