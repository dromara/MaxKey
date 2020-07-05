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
package org.maxkey.authz.desktop.endpoint;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.authz.desktop.endpoint.adapter.DesktopDefaultAdapter;
import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.constants.Boolean;
import org.maxkey.domain.Accounts;
import org.maxkey.domain.apps.AppsDesktopDetails;
import org.maxkey.persistence.service.AppsDesktopDetailsService;
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
public class DesktopAuthorizeEndpoint  extends AuthorizeBaseEndpoint{
	final static Logger _logger = LoggerFactory.getLogger(DesktopAuthorizeEndpoint.class);
	
	@Autowired
	AppsDesktopDetailsService desktopDetailsService;
	
	DesktopDefaultAdapter defaultDesktopAdapter=new DesktopDefaultAdapter();
	
	@RequestMapping("/authz/desktop/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			@PathVariable("id") String id){
		AppsDesktopDetails desktopDetails=desktopDetailsService.getAppDetails(id);
		_logger.debug(""+desktopDetails);
		
		Accounts appUser=getAccounts(desktopDetails);
		if(appUser	==	null){
			return generateInitCredentialModelAndView(id,"/authorize/desktop/"+id);
			
		}else{
			desktopDetails.setAppUser(appUser);
			ModelAndView modelAndView=new ModelAndView();
			
			AbstractAuthorizeAdapter adapter;
			if(Boolean.isTrue(desktopDetails.getIsAdapter())){
				adapter =(AbstractAuthorizeAdapter)Instance.newInstance(desktopDetails.getAdapter());
			}else{
				adapter =(AbstractAuthorizeAdapter)defaultDesktopAdapter;
			}
			
			String paramString=adapter.generateInfo(WebContext.getUserInfo(), desktopDetails);
			
			String encryptParamString=adapter.encrypt(paramString, null, null);
			
			String signParamString=adapter.sign(encryptParamString, desktopDetails);
			
			modelAndView=adapter.authorize(
					WebContext.getUserInfo(), 
					desktopDetails,
					signParamString, 
					modelAndView);
			
			return modelAndView;
		}
	}
}
