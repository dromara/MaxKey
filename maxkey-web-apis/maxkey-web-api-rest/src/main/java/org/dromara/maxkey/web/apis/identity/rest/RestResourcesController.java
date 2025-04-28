/*
 * Copyright [2025] [MaxKey of copyright http://www.maxkey.top]
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

import java.util.HashSet;
import java.util.Set;


import org.slf4j.LoggerFactory;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.authz.vo.AppResourcesVo;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.entity.permissions.Resources;
import org.dromara.maxkey.persistence.service.AppsService;
import org.dromara.maxkey.persistence.service.AuthzResourceService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/idm/Resources")
public class RestResourcesController {
	static final  Logger logger = LoggerFactory.getLogger(RestResourcesController.class);

    @Autowired
    UserInfoService userInfoService;
    
    @Autowired
    AppsService appsService;
    
    @Autowired
    AuthzResourceService authzResourceService;
   
    @Operation(summary = "获取应用功能权限清单", description = "获取应用功能权限清单",method="GET")
    @GetMapping("/functionList")
    public Message<AppResourcesVo> getFunctionList(@RequestParam("userId") String userId) {
    	logger.debug("userId {} ", userId);
        UserInfo user = userInfoService.get(userId);
        ///获取appId登录
        User  principal = (User)AuthorizationUtils.getAuthentication().getPrincipal();
        Apps app = appsService.get(principal.getUsername(),true);
    	logger.debug("appId {} " , app.getId());	
    	Apps relatedApp = new Apps();
    	if(user != null) {
	    	relatedApp.setId(app.getId());
	    	relatedApp.setAppName(app.getAppName());
	    	relatedApp.setLoginUrl(app.getLoginUrl());
	    	relatedApp.setLogoutUrl(app.getLogoutUrl());
	    	relatedApp.setProtocol(app.getProtocol());
	    	relatedApp.setCategory(app.getCategory());
	    	relatedApp.setVendor(app.getVendor());
	    	relatedApp.setVendorUrl(app.getVendorUrl());
	    	relatedApp.setDescription(app.getDescription());
	    	Set<Resources> functions  = authzResourceService.getResourcesBySubject(user,app);
	    	return new Message<>(new AppResourcesVo(relatedApp,functions)); 
    	}else {
    		return new Message<>(new AppResourcesVo(relatedApp,new HashSet<>())); 
    	}
    }
}
