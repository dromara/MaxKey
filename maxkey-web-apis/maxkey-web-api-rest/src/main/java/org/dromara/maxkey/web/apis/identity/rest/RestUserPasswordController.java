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
 

package org.dromara.maxkey.web.apis.identity.rest;

import java.io.IOException;

import org.dromara.maxkey.entity.ChangePassword;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping(value={"/api/idm/Users"})
public class RestUserPasswordController {
	
	static final Logger _logger = LoggerFactory.getLogger(RestUserPasswordController.class);
	
    @Autowired
    UserInfoService userInfoService;
    
  
    @PostMapping(value = "/changePassword")
    @ResponseBody
    public String changePassword(@RequestParam(required = true) String username,
                                 @RequestParam(required = true) String password,
                                 UriComponentsBuilder builder) throws IOException {
    	
    	_logger.debug("UserInfo username {} , password {}", username , password);
    	
    	UserInfo loadUserInfo = userInfoService.findByUsername(username);
        if(loadUserInfo != null) {
        	ChangePassword changePassword  = new ChangePassword(loadUserInfo);
        	changePassword.setPassword(password);
        	changePassword.setDecipherable(loadUserInfo.getDecipherable());
            userInfoService.changePassword(changePassword,true);
        }
        return "true";
    }

}
