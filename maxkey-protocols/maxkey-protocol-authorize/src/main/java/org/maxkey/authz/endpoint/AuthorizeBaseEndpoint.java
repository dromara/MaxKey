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
package org.maxkey.authz.endpoint;

import org.apache.commons.lang3.StringUtils;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.entity.Accounts;
import org.maxkey.entity.UserInfo;
import org.maxkey.entity.apps.Apps;
import org.maxkey.persistence.service.AccountsService;
import org.maxkey.persistence.service.AppsService;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Crystal.Sea
 *
 */
public class AuthorizeBaseEndpoint {
	final static Logger _logger = LoggerFactory.getLogger(AuthorizeBaseEndpoint.class);
	
	@Autowired 
    @Qualifier("applicationConfig")
    protected ApplicationConfig applicationConfig;
	
	@Autowired
	@Qualifier("appsService")
	protected AppsService appsService;
		
	@Autowired
	@Qualifier("accountsService")
	protected AccountsService accountsService;
		
	protected Apps getApp(String id){
		Apps  app=(Apps)WebContext.getAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP);
		//session中为空或者id不一致重新加载
		if(StringUtils.isBlank(id) || !app.getId().equalsIgnoreCase(id)) {
			app=appsService.loadById(id);
			WebContext.setAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP, app);
		}
		if(app	==	null){
			_logger.error("Applications for id "+id + "  is null");
		}
		
		return app;
	}
	
	protected Accounts getAccounts(Apps app){
		Accounts account=new Accounts();
		UserInfo userInfo=WebContext.getUserInfo();
		Apps  loadApp = getApp(app.getId());
		if(loadApp.getCredential()==Apps.CREDENTIALS.USER_DEFINED){
			
			account=accountsService.load(new Accounts(userInfo.getId(),loadApp.getId()));
			if(account!=null){
				account.setRelatedPassword(ReciprocalUtils.decoder(account.getRelatedPassword()));
			}
		}else if(loadApp.getCredential()==Apps.CREDENTIALS.SHARED){
			
			account.setRelatedUsername(loadApp.getSharedUsername());
			account.setRelatedPassword(ReciprocalUtils.decoder(loadApp.getSharedPassword()));
			
		}else if(loadApp.getCredential()==Apps.CREDENTIALS.SYSTEM){
			
			if(loadApp.getSystemUserAttr().equalsIgnoreCase("userId")){
				account.setUsername(userInfo.getId());
			}else if(loadApp.getSystemUserAttr().equalsIgnoreCase("username")){
				account.setUsername(userInfo.getUsername());
			}else if(loadApp.getSystemUserAttr().equalsIgnoreCase("employeeNumber")){
				account.setUsername(userInfo.getEmployeeNumber());
			}else if(loadApp.getSystemUserAttr().equalsIgnoreCase("email")){
				account.setUsername(userInfo.getEmail());
			}else if(loadApp.getSystemUserAttr().equalsIgnoreCase("windowsAccount")){
				account.setUsername(userInfo.getWindowsAccount());
			}
			//decoder database stored encode password
			account.setRelatedPassword(ReciprocalUtils.decoder(WebContext.getUserInfo().getDecipherable()));
			
		}else if(loadApp.getCredential()==Apps.CREDENTIALS.NONE){
			
			account.setUsername(userInfo.getUsername());
			account.setRelatedPassword(userInfo.getUsername());
			
		}
		return account;
	}
	
	public ModelAndView generateInitCredentialModelAndView(String appId,String redirect_uri){
		ModelAndView modelAndView=new ModelAndView("redirect:/authz/credential/forward?appId="+appId+"&redirect_uri="+redirect_uri);
		return modelAndView;
	}
	
}
