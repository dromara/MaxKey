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
import java.util.HashMap;

import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.entity.apps.AppsTokenBasedDetails;
import org.dromara.maxkey.util.DateUtils;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.util.StringGenerator;
import org.dromara.maxkey.web.WebConstants;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class TokenBasedDefaultAdapter extends AbstractAuthorizeAdapter {
	static final  Logger _logger = LoggerFactory.getLogger(TokenBasedDefaultAdapter.class);
	String token = "";
	
	@Override
	public Object generateInfo() {
		AppsTokenBasedDetails details=(AppsTokenBasedDetails)app;
		HashMap<String,String> beanMap=new HashMap<String,String>();
		
		beanMap.put("randomId",(new StringGenerator()).uuidGenerate());
		if(details.getUserPropertys()!=null && !details.getUserPropertys().equals("")) {
		    
    		if(details.getUserPropertys().indexOf("userId")>-1){
                beanMap.put("userId",userInfo.getId());
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
		beanMap.put(WebConstants.ONLINE_TICKET_NAME, principal.getSessionId());
		
		/*
		 * use UTC date time format
		 * current date plus expires minute 
		 */
		DateTime currentDateTime = DateTime.now();
		Date expirationTime = currentDateTime.plusSeconds(details.getExpires()).toDate();
		String expiresString = DateUtils.toUtc(expirationTime);
		_logger.debug("UTC Local current date : "+DateUtils.toUtcLocal(currentDateTime.toDate()));
		_logger.debug("UTC  current Date : "+DateUtils.toUtc(currentDateTime));
		_logger.debug("UTC  expires Date : "+DateUtils.toUtc(currentDateTime));
		
		beanMap.put("at", DateUtils.toUtc(currentDateTime));
		
		beanMap.put("expires", expiresString);
		
		token = JsonUtils.toString(beanMap);
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
		
		modelAndView.addObject("token",token );
		return modelAndView;
	}

	@Override
	public String serialize() {
		return token;
	}
	
}
