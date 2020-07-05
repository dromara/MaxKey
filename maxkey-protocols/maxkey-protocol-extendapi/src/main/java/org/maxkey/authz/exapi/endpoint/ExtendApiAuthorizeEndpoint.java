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
import org.maxkey.domain.Accounts;
import org.maxkey.domain.apps.Apps;
import org.maxkey.util.Instance;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Crystal.Sea
 *
 */
@Controller
public class ExtendApiAuthorizeEndpoint  extends AuthorizeBaseEndpoint{
	final static Logger _logger = LoggerFactory.getLogger(ExtendApiAuthorizeEndpoint.class);

	@RequestMapping("/authz/api/{id}")
	public ModelAndView authorize(HttpServletRequest request,@PathVariable("id") String id){
		
		Apps apps=getApp(id);
		_logger.debug(""+apps);
		
		if(Boolean.isTrue(apps.getIsAdapter())){
			Accounts appUser=getAccounts(apps);
			
			if(appUser	==	null){
				return generateInitCredentialModelAndView(id,"/authorize/api/"+id);
			}

			ModelAndView modelAndView=new ModelAndView();
			
			AbstractAuthorizeAdapter adapter =(AbstractAuthorizeAdapter)Instance.newInstance(apps.getAdapter());
			
			apps.setAppUser(appUser);
			
			modelAndView=adapter.authorize(
					WebContext.getUserInfo(), 
					apps, 
					appUser.getRelatedUsername()+"."+appUser.getRelatedPassword(), 
					modelAndView);
			return modelAndView;
		}else{
			String redirec_uri=getApp(id).getLoginUrl();
			return WebContext.redirect(redirec_uri);
		}
		
	}
}
