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
 

package org.maxkey.identity.rest;

import java.io.IOException;

import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping(value={"/identity/api/userinfo"})
public class RestUserInfoController {

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public UserInfo getUser(
                                       @PathVariable String id,
                                       @RequestParam(required = false) String attributes) {
        
        UserInfo loadUserInfo = userInfoService.get(id);
        loadUserInfo.setDecipherable(null);
        return loadUserInfo;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public UserInfo create(@RequestBody  UserInfo userInfo,
                                                      @RequestParam(required = false) String attributes,
                                                      UriComponentsBuilder builder) throws IOException {
        UserInfo loadUserInfo = userInfoService.loadByUsername(userInfo.getUsername());
        if(loadUserInfo != null) {
            userInfoService.update(userInfo);
        }else {
            userInfoService.insert(userInfo);
        }
        return userInfo;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public UserInfo replace(@PathVariable String id,
                                                       @RequestBody UserInfo userInfo,
                                                       @RequestParam(required = false) String attributes)
            throws IOException {
        UserInfo loadUserInfo = userInfoService.loadByUsername(userInfo.getUsername());
        if(loadUserInfo != null) {
            userInfoService.update(userInfo);
        }else {
            userInfoService.insert(userInfo);
        }
        return userInfo;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable final String id) {
        userInfoService.logisticDeleteAllByCid(id);
       
    }
}
