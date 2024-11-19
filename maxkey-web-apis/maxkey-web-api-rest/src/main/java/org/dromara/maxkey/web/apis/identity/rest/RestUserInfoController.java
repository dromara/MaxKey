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

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value={"/api/idm/Users"})
public class RestUserInfoController {
	
	static final Logger _logger = LoggerFactory.getLogger(RestUserInfoController.class);
	
    @Autowired
    UserInfoService userInfoService;
    
    @GetMapping(value = "/{id}")
    public UserInfo getUser(
                                       @PathVariable String id,
                                       @RequestParam(required = false) String attributes) {
    	_logger.debug("UserInfo id {} , attributes {}", id , attributes);
        UserInfo loadUserInfo = userInfoService.get(id);
        loadUserInfo.setDecipherable(null);
        return loadUserInfo;
    }

    @PostMapping
    public UserInfo create(@RequestBody  UserInfo userInfo,
                                                      @RequestParam(required = false) String attributes,
                                                      UriComponentsBuilder builder) {
    	_logger.debug("UserInfo content {} , attributes {}", userInfo , attributes);
        UserInfo loadUserInfo = userInfoService.findByUsername(userInfo.getUsername());
        if(loadUserInfo != null) {
            userInfoService.update(userInfo);
        }else {
            userInfoService.insert(userInfo);
        }
        return userInfo;
    }
    
    @PutMapping(value = "/{id}")
    public UserInfo replace(@PathVariable String id,
                                                       @RequestBody UserInfo userInfo,
                                                       @RequestParam(required = false) String attributes) {
    	_logger.debug("UserInfo content {} , attributes {}", userInfo , attributes);
        UserInfo loadUserInfo = userInfoService.findByUsername(userInfo.getUsername());
        if(loadUserInfo != null) {
            userInfoService.update(userInfo);
        }else {
            userInfoService.insert(userInfo);
        }
        return userInfo;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable final String id) {
    	_logger.debug("UserInfo id {} ", id );
        userInfoService.delete(id);
    }
    
    @GetMapping(value = { "/.search" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<JpaPageResults<UserInfo>> search(@ModelAttribute UserInfo userInfo) {
		_logger.debug("UserInfo {}",userInfo);
		if(StringUtils.isBlank(userInfo.getInstId())){
			userInfo.setInstId("1");
    	}
		return new Message<>(userInfoService.fetchPageResults(userInfo));
	}

}
