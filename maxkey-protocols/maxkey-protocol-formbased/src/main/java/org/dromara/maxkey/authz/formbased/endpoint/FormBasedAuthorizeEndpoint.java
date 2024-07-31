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
package org.dromara.maxkey.authz.formbased.endpoint;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.authz.formbased.endpoint.adapter.FormBasedDefaultAdapter;
import org.dromara.maxkey.constants.ConstsBoolean;
import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.apps.AppsFormBasedDetails;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.AppsFormBasedDetailsService;
import org.dromara.maxkey.util.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Crystal.Sea
 *
 */
@Tag(name = "2-7-FormBased接口文档模块")
@Controller
public class FormBasedAuthorizeEndpoint  extends AuthorizeBaseEndpoint{
	static final  Logger _logger = LoggerFactory.getLogger(FormBasedAuthorizeEndpoint.class);
	
	@Autowired
	AppsFormBasedDetailsService formBasedDetailsService;
	
	FormBasedDefaultAdapter defaultFormBasedAdapter=new FormBasedDefaultAdapter();
	
	@Operation(summary = "FormBased认证地址接口", description = "参数应用ID",method="GET")
	@RequestMapping("/authz/formbased/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			@PathVariable("id") String id,
			@CurrentUser UserInfo currentUser){
		
		AppsFormBasedDetails formBasedDetails = formBasedDetailsService.getAppDetails(id , true);
		_logger.debug("formBasedDetails {}",formBasedDetails);
		Apps  application = getApp(id);
		formBasedDetails.setAdapter(application.getAdapter());
		formBasedDetails.setIsAdapter(application.getIsAdapter());
		ModelAndView modelAndView=null;
		
		Accounts account = getAccounts(formBasedDetails,currentUser);
		_logger.debug("Accounts {}",account);
		
		if(account	==	null){
			return initCredentialView(id,"/authz/formbased/"+id);
		}else{
			modelAndView=new ModelAndView();
			
			AbstractAuthorizeAdapter adapter;
			
			if(ConstsBoolean.isTrue(formBasedDetails.getIsAdapter())){
				Object formBasedAdapter = Instance.newInstance(formBasedDetails.getAdapter());
				adapter =(AbstractAuthorizeAdapter)formBasedAdapter;
			}else{
				FormBasedDefaultAdapter formBasedDefaultAdapter =new FormBasedDefaultAdapter();
				adapter =(AbstractAuthorizeAdapter)formBasedDefaultAdapter;
			}
			adapter.setPrincipal(AuthorizationUtils.getPrincipal());
			adapter.setApp(formBasedDetails);
			adapter.setAccount(account);
			
			modelAndView = adapter.authorize(modelAndView);
		}
		
		_logger.debug("FormBased View Name {}" , modelAndView.getViewName());
		
		return modelAndView;
	}
}
