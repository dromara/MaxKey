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
 

/**
 * 
 */
package org.maxkey.authz.exapi.endpoint;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.constants.Boolean;
import org.maxkey.entity.Accounts;
import org.maxkey.entity.UserInfo;
import org.maxkey.entity.apps.Apps;
import org.maxkey.util.Instance;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Crystal.Sea
 *
 */
@Tag(name = "2-8-ExtendApi接口文档模块")
@Controller
public class ExtendApiAuthorizeEndpoint  extends AuthorizeBaseEndpoint{
	final static Logger _logger = LoggerFactory.getLogger(ExtendApiAuthorizeEndpoint.class);

	@Operation(summary = "ExtendApi认证地址接口", description = "参数应用ID",method="GET")
	@RequestMapping("/authz/api/{id}")
	public ModelAndView authorize(HttpServletRequest request,@PathVariable("id") String id){
	    
	    ModelAndView modelAndView=new ModelAndView("authorize/redirect_sso_submit");
		Apps apps=getApp(id);
		_logger.debug(""+apps);
		UserInfo userInfo = WebContext.getUserInfo();
		if(Boolean.isTrue(apps.getIsAdapter())){
			
			AbstractAuthorizeAdapter adapter =(AbstractAuthorizeAdapter)Instance.newInstance(apps.getAdapter());
			String username ="";
			String password ="";
			if(apps.getCredential()==1) {
				if(apps.getSystemUserAttr().equalsIgnoreCase("userId")) {
					username = userInfo.getId();
				}else if(apps.getSystemUserAttr().equalsIgnoreCase("username")) {
					username = userInfo.getUsername();
				}else if(apps.getSystemUserAttr().equalsIgnoreCase("email")) {
					username = userInfo.getEmail();
				}else if(apps.getSystemUserAttr().equalsIgnoreCase("employeeNumber")) {
					username = userInfo.getEmployeeNumber();
				}else if(apps.getSystemUserAttr().equalsIgnoreCase("windowsaccount")) {
					username = userInfo.getWindowsAccount();
				}else if(apps.getSystemUserAttr().equalsIgnoreCase("mobile")) {
					username = userInfo.getMobile();
				}else if(apps.getSystemUserAttr().equalsIgnoreCase("workEmail")) {
					username = userInfo.getWorkEmail();
				}else {
					username = userInfo.getEmail();
				}
					
			} else if(apps.getCredential()==2) {
				username = apps.getSharedUsername();
				password = apps.getSharedPassword();
			}else if(apps.getCredential()==3) {
				Accounts appUser=getAccounts(apps);
				if(appUser	==	null){
						return generateInitCredentialModelAndView(id,"/authorize/api/"+id);
				}
				apps.setAppUser(appUser);
			}
			
			modelAndView=adapter.authorize(
					WebContext.getUserInfo(), 
					apps, 
					username+"="+password, 
					modelAndView);
			return modelAndView;
		}else{
	        modelAndView.addObject("redirect_uri", getApp(id).getLoginUrl());
	        
	        return modelAndView;
		}
		
	}
}
