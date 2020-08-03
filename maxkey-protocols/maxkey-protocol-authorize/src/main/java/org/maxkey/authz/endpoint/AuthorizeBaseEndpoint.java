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

import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.Accounts;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.Apps;
import org.maxkey.persistence.service.AccountsService;
import org.maxkey.persistence.service.AppsService;
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
		Apps  app=(Apps)WebContext.getAttribute(AuthorizeBaseEndpoint.class.getName());
		//session中为空或者id不一致重新加载
		if(app==null||!app.getId().equalsIgnoreCase(id)) {
			app=appsService.get(id);
			WebContext.setAttribute(AuthorizeBaseEndpoint.class.getName(), app);
		}
		if(app	==	null){
			_logger.error("Applications for id "+id + "  is null");
		}
		
		return app;
	}
	
	protected Accounts getAccounts(Apps app){
		Accounts account=new Accounts();
		UserInfo userInfo=WebContext.getUserInfo();
		Apps  application= getApp(app.getId());
		if(application.getCredential()==Apps.CREDENTIALS.USER_DEFINED){
			
			account=accountsService.load(new Accounts(userInfo.getId(),application.getId()));
			if(account!=null){
				account.setRelatedPassword(ReciprocalUtils.decoder(account.getRelatedPassword()));
			}
		}else if(application.getCredential()==Apps.CREDENTIALS.SHARED){
			
			account.setRelatedUsername(application.getSharedUsername());
			account.setRelatedPassword(ReciprocalUtils.decoder(application.getSharedPassword()));
			
		}else if(application.getCredential()==Apps.CREDENTIALS.SYSTEM){
			
			if(application.getSystemUserAttr().equalsIgnoreCase("uid")){
				account.setUsername(userInfo.getId());
			}else if(application.getSystemUserAttr().equalsIgnoreCase("username")){
				account.setUsername(userInfo.getUsername());
			}else if(application.getSystemUserAttr().equalsIgnoreCase("employeeNumber")){
				account.setUsername(userInfo.getEmployeeNumber());
			}else if(application.getSystemUserAttr().equalsIgnoreCase("email")){
				account.setUsername(userInfo.getEmail());
			}else if(application.getSystemUserAttr().equalsIgnoreCase("windowsAccount")){
				account.setUsername(userInfo.getWindowsAccount());
			}
			//decoder database stored encode password
			account.setRelatedPassword(ReciprocalUtils.decoder(WebContext.getUserInfo().getDecipherable()));
			
			
		}else if(application.getCredential()==Apps.CREDENTIALS.NONE){
			
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
