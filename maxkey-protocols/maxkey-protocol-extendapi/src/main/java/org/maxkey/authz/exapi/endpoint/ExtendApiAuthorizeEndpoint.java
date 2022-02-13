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

import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.constants.ConstsBoolean;
import org.maxkey.entity.Accounts;
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
		Apps apps = getApp(id);
		_logger.debug(""+apps);
		if(ConstsBoolean.isTrue(apps.getIsAdapter())){
			AbstractAuthorizeAdapter adapter = (AbstractAuthorizeAdapter)Instance.newInstance(apps.getAdapter());
			Accounts account = getAccounts(apps);
			if(apps.getCredential()==Apps.CREDENTIALS.USER_DEFINED && account == null) {
				return generateInitCredentialModelAndView(id,"/authorize/api/"+id);
			}
			
			adapter.setAuthentication((SigninPrincipal)WebContext.getAuthentication().getPrincipal());
			adapter.setUserInfo(WebContext.getUserInfo());
			adapter.setApp(apps);
			adapter.setAccount(account);
			
			return adapter.authorize(modelAndView);
		}else{
	        modelAndView.addObject("redirect_uri", apps.getLoginUrl());
	        return modelAndView;
		}
		
	}
}
