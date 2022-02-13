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
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.crypto.password.PasswordReciprocal;
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
		if(StringUtils.isBlank(id)) {
			_logger.error("parameter for app id " + id + "  is null.");
		}else {
			//session中为空或者id不一致重新加载
			if(app == null || !app.getId().equalsIgnoreCase(id)) {
				app=appsService.loadById(id);
				WebContext.setAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP, app);
			}
		}
		if(app	==	null){
			_logger.error("Applications id " + id + "  is not exist.");
		}
		return app;
	}
	
	protected Accounts getAccounts(Apps app){
		UserInfo userInfo = WebContext.getUserInfo();
		Apps  loadApp = getApp(app.getId());
		
		Accounts account = new Accounts(userInfo.getId(),loadApp.getId());
		account.setUsername(userInfo.getUsername());
		account.setAppName(app.getName());
		
		if(loadApp.getCredential() == Apps.CREDENTIALS.USER_DEFINED){
			account = accountsService.load(new Accounts(userInfo.getId(),loadApp.getId()));
			if(account != null){
				account.setRelatedPassword(
						PasswordReciprocal.getInstance().decoder(account.getRelatedPassword()));
			}
			
		}else if(loadApp.getCredential() == Apps.CREDENTIALS.SHARED){
			account.setRelatedUsername(loadApp.getSharedUsername());
			account.setRelatedPassword(PasswordReciprocal.getInstance().decoder(loadApp.getSharedPassword()));	
		}else if(loadApp.getCredential() == Apps.CREDENTIALS.SYSTEM){
			account.setUsername(
					AbstractAuthorizeAdapter.getValueByUserAttr(userInfo, loadApp.getSystemUserAttr())
			);
			//decoder database stored encode password
			account.setRelatedPassword(
					PasswordReciprocal.getInstance().decoder(WebContext.getUserInfo().getDecipherable()));
		}else if(loadApp.getCredential()==Apps.CREDENTIALS.NONE){
			account.setUsername(userInfo.getUsername());
			account.setRelatedPassword(userInfo.getUsername());
			
		}
		return account;
	}
	
	public ModelAndView generateInitCredentialModelAndView(String appId,String redirect_uri){
		ModelAndView modelAndView = 
				new ModelAndView(String.format(InitCredentialURL,appId, redirect_uri));
		return modelAndView;
	}
	
	public static String InitCredentialURL = "redirect:/authz/credential/forward?appId=%s&redirect_uri=%s";
}
