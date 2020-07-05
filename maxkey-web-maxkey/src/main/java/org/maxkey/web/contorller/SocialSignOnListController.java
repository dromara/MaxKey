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
 

package org.maxkey.web.contorller;

import java.util.ArrayList;
import java.util.List;

import org.maxkey.authn.support.socialsignon.service.SocialSignOnProvider;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.maxkey.authn.support.socialsignon.service.SocialsAssociate;
import org.maxkey.authn.support.socialsignon.service.SocialsAssociateService;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/socialsignon"})
public class SocialSignOnListController {
	final static Logger _logger = LoggerFactory.getLogger(SocialSignOnListController.class);
	
	@Autowired
	SocialSignOnProviderService socialSignOnProviderService;
	
	@Autowired
	protected SocialsAssociateService socialSignOnUserService;
	
	@Autowired
  	@Qualifier("applicationConfig")
  	protected ApplicationConfig applicationConfig;
	
	@RequestMapping(value = { "/list" })
	public ModelAndView forwardUpdate() {
		
		ModelAndView modelAndView=new ModelAndView("social/socialSignOnProvider");
		if(applicationConfig.getLoginConfig().isSocialSignOn()){
			List<SocialSignOnProvider>  listSocialSignOnProvider= socialSignOnProviderService.getSocialSignOnProviders();
			
			SocialsAssociate socialSignOnUser=new SocialsAssociate();
			socialSignOnUser.setUid(WebContext.getUserInfo().getId());
			List<SocialsAssociate>  listSocialSignOnUserToken= socialSignOnUserService.query(socialSignOnUser);
			List<SocialSignOnProvider>  listBindSocialSignOnProvider=new ArrayList<SocialSignOnProvider>();
			_logger.debug("list SocialSignOnProvider : "+listSocialSignOnProvider);
			_logger.debug("list SocialSignOnUserToken : "+listSocialSignOnUserToken);
			for (SocialSignOnProvider ssop : listSocialSignOnProvider){
				SocialSignOnProvider socialSignOnProvider=new SocialSignOnProvider();
				socialSignOnProvider.setProvider(ssop.getProvider());
				socialSignOnProvider.setProviderName(ssop.getProviderName());
				socialSignOnProvider.setIcon(ssop.getIcon());
				for(SocialsAssociate ssout :listSocialSignOnUserToken){
					if(ssout.getProvider().equals(ssop.getProvider())){
						socialSignOnProvider.setUserBind(true);
						_logger.debug("binded provider : "+ssout.getProvider());
					}
				}
				listBindSocialSignOnProvider.add(socialSignOnProvider);
			}
			
			if(listBindSocialSignOnProvider.size()%4>0){
				for(int i=0;i<=listBindSocialSignOnProvider.size()%4;i++){
					listBindSocialSignOnProvider.add(new SocialSignOnProvider());
				}
				
			}
			
			modelAndView.addObject("listSocialSignOnProvider", listBindSocialSignOnProvider);
		}
		return modelAndView;
	}
	
}
