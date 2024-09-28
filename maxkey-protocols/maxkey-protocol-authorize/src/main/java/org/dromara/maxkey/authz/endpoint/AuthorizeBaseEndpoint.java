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
package org.dromara.maxkey.authz.endpoint;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.AccountsService;
import org.dromara.maxkey.persistence.service.AppsService;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.dromara.mybatis.jpa.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Crystal.Sea
 *
 */
public class AuthorizeBaseEndpoint {
	static final  Logger _logger = LoggerFactory.getLogger(AuthorizeBaseEndpoint.class);
	
	@Autowired 
    protected ApplicationConfig applicationConfig;
	
	@Autowired
	protected AppsService appsService;
		
	@Autowired
	protected AccountsService accountsService;
		
	protected Apps getApp(String id){
		Apps  app=(Apps)WebContext.getAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP);
		if(StringUtils.isBlank(id)) {
			_logger.error("parameter for app id {}  is null.",id);
		}else {
			//session中为空或者id不一致重新加载
			if(app == null || !app.getId().equalsIgnoreCase(id)) {
				app = appsService.get(id,true);
			}
			WebContext.setAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP, app);
		}
		if(app	==	null){
			_logger.error("Applications id {} is not exist.",id);
		}
		return app;
	}
	
	protected Accounts getAccounts(Apps app,UserInfo userInfo){
		Apps  loadApp = getApp(app.getId());
		
		Accounts account = new Accounts(userInfo.getId(),loadApp.getId());
		account.setUsername(userInfo.getUsername());
		account.setAppName(app.getAppName());
		
		if(loadApp.getCredential().equalsIgnoreCase(Apps.CREDENTIALS.USER_DEFINED)){
			account = accountsService.get( Query.builder().eq("appId", loadApp.getId()).eq("userid", userInfo.getId()));
			if(account != null){
				account.setRelatedPassword(
						PasswordReciprocal.getInstance().decoder(account.getRelatedPassword()));
			}
			
		}else if(loadApp.getCredential().equalsIgnoreCase(Apps.CREDENTIALS.SHARED)){
			account.setRelatedUsername(loadApp.getSharedUsername());
			account.setRelatedPassword(PasswordReciprocal.getInstance().decoder(loadApp.getSharedPassword()));	
		}else if(loadApp.getCredential().equalsIgnoreCase( Apps.CREDENTIALS.SYSTEM)){
			account.setUsername(
					AbstractAuthorizeAdapter.getValueByUserAttr(userInfo, loadApp.getSystemUserAttr())
			);
			//decoder database stored encode password
			account.setRelatedPassword(
					PasswordReciprocal.getInstance().decoder(userInfo.getDecipherable()));
		}else if(loadApp.getCredential().equalsIgnoreCase(Apps.CREDENTIALS.NONE)){
			account.setUsername(userInfo.getUsername());
			account.setRelatedPassword(userInfo.getUsername());
			
		}
		return account;
	}
	
	public ModelAndView initCredentialView(String appId,String redirect_uri){
		String initCredentialURL = 
				"" + 
				applicationConfig.getFrontendUri() + 
				"/#/authz/credential?appId=%s&redirect_uri=%s";
		
		initCredentialURL = String.format(initCredentialURL,appId, redirect_uri);
		_logger.debug("redirect to {}.",initCredentialURL);
		ModelAndView  modelAndView =new ModelAndView("redirect");
		modelAndView.addObject("redirect_uri", initCredentialURL);
		return modelAndView;
	}
	
}
