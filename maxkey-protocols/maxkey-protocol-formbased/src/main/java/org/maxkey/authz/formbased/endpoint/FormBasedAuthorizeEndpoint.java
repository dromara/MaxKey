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
package org.maxkey.authz.formbased.endpoint;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.authz.formbased.endpoint.adapter.FormBasedDefaultAdapter;
import org.maxkey.constants.Boolean;
import org.maxkey.domain.Accounts;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.AppsFormBasedDetails;
import org.maxkey.persistence.service.AppsFormBasedDetailsService;
import org.maxkey.util.Instance;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Crystal.Sea
 *
 */
@Controller
public class FormBasedAuthorizeEndpoint  extends AuthorizeBaseEndpoint{
	final static Logger _logger = LoggerFactory.getLogger(FormBasedAuthorizeEndpoint.class);
	
	@Autowired
	AppsFormBasedDetailsService formBasedDetailsService;
	
	FormBasedDefaultAdapter defaultFormBasedAdapter=new FormBasedDefaultAdapter();
	
	@RequestMapping("/authz/formbased/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			@PathVariable("id") String id){
		AppsFormBasedDetails formBasedDetails=formBasedDetailsService.getAppDetails(id);
		_logger.debug(""+formBasedDetails);
		Apps  application= getApp(id);
		formBasedDetails.setAdapter(application.getAdapter());
		formBasedDetails.setIsAdapter(application.getIsAdapter());
		ModelAndView modelAndView=null;
		
		Accounts appUser=getAccounts(formBasedDetails);
		
		_logger.debug("Accounts "+appUser);
		if(appUser	==	null){
			return generateInitCredentialModelAndView(id,"/authorize/formbased/"+id);
			
		}else{
			formBasedDetails.setAppUser(appUser);
			
			modelAndView=new ModelAndView();
			
			AbstractAuthorizeAdapter adapter;
			
			if(Boolean.isTrue(formBasedDetails.getIsAdapter())){
				adapter =(AbstractAuthorizeAdapter)Instance.newInstance(formBasedDetails.getAdapter());
			}else{
				adapter =(AbstractAuthorizeAdapter)defaultFormBasedAdapter;
			}
			
			modelAndView=adapter.authorize(
					WebContext.getUserInfo(), 
					formBasedDetails, 
					appUser.getRelatedUsername()+"."+appUser.getRelatedPassword(), 
					modelAndView);
		}
		
		_logger.debug("FormBased View Name " + modelAndView.getViewName());
		
		return modelAndView;
	}
}
