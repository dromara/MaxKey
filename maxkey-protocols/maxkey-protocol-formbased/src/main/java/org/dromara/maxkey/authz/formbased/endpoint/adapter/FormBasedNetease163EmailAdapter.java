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

import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.constants.ConstsBoolean;
import org.dromara.maxkey.entity.apps.AppsFormBasedDetails;
import org.springframework.web.servlet.ModelAndView;

public class FormBasedNetease163EmailAdapter extends AbstractAuthorizeAdapter {
    
    @Override
    public String generateInfo() {
        return null;
    }

    @Override
    public ModelAndView authorize(ModelAndView modelAndView) {
        modelAndView.setViewName("authorize/formbased_163email_sso_submint");
        AppsFormBasedDetails details=(AppsFormBasedDetails)app;
        modelAndView.addObject("username", account.getRelatedUsername().substring(account.getRelatedUsername().indexOf("@")));
        modelAndView.addObject("email", account.getRelatedUsername());
        modelAndView.addObject("password",  account.getRelatedPassword());
        
        if(ConstsBoolean.isTrue(details.getIsExtendAttr())){
            modelAndView.addObject("extendAttr", details.getExtendAttr());
            modelAndView.addObject("isExtendAttr", true);
        }else{
            modelAndView.addObject("isExtendAttr", false);
        }
        
        modelAndView.addObject("action", details.getRedirectUri());
        modelAndView.addObject("usernameMapping", details.getUsernameMapping());
        modelAndView.addObject("passwordMapping", details.getPasswordMapping());
        return modelAndView;
    }

}
