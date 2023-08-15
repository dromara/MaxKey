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
 

package org.dromara.maxkey.authz.formbased.endpoint.adapter;

import java.time.Instant;

import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.constants.ConstsBoolean;
import org.dromara.maxkey.crypto.DigestUtils;
import org.dromara.maxkey.entity.apps.AppsFormBasedDetails;
import org.dromara.maxkey.web.WebContext;
import org.springframework.web.servlet.ModelAndView;

public class FormBasedRedirectAdapter extends AbstractAuthorizeAdapter {

	@Override
	public Object generateInfo() {
		return null;
	}

	@Override
	public ModelAndView authorize(ModelAndView modelAndView) {
		modelAndView.setViewName("authorize/formbased_redirect_submint");
		AppsFormBasedDetails details=(AppsFormBasedDetails)app;
		
		String password = account.getRelatedPassword();
        if(null==details.getPasswordAlgorithm()||details.getPasswordAlgorithm().equals("")){
        }else if(details.getPasswordAlgorithm().indexOf("HEX")>-1){
            password = DigestUtils.digestHex(account.getRelatedPassword(),details.getPasswordAlgorithm().substring(0, details.getPasswordAlgorithm().indexOf("HEX")));
        }else{
            password = DigestUtils.digestBase64(account.getRelatedPassword(),details.getPasswordAlgorithm());
        }
        
		modelAndView.addObject("id", details.getId());
		modelAndView.addObject("action", details.getRedirectUri());
		modelAndView.addObject("redirectUri", details.getRedirectUri());
		modelAndView.addObject("loginUrl", details.getLoginUrl());
		modelAndView.addObject("usernameMapping", details.getUsernameMapping());
		modelAndView.addObject("passwordMapping", details.getPasswordMapping());
		modelAndView.addObject("username", account.getRelatedUsername());
        modelAndView.addObject("password",  password);
        modelAndView.addObject("timestamp",  ""+Instant.now().getEpochSecond());
		
		if(WebContext.getAttribute("formbased_redirect_submint")==null){
			modelAndView.setViewName("authorize/formbased_redirect_submint");
			WebContext.setAttribute("formbased_redirect_submint", "formbased_redirect_submint");
		}else{
			modelAndView.setViewName("authorize/formbased_redirect_post_submint");
			if(details.getAuthorizeView()!=null&&!details.getAuthorizeView().equals("")){
				modelAndView.setViewName("authorize/"+details.getAuthorizeView());
			}
			WebContext.removeAttribute("formbased_redirect_submint");
		}
		
		
		if(ConstsBoolean.isTrue(details.getIsExtendAttr())){
			modelAndView.addObject("extendAttr", details.getExtendAttr());
			modelAndView.addObject("isExtendAttr", true);
		}else{
			modelAndView.addObject("isExtendAttr", false);
		}
		
		return modelAndView;
	}

}
