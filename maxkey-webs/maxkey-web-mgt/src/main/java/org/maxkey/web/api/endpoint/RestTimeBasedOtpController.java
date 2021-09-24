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
 

package org.maxkey.web.api.endpoint;

import org.maxkey.entity.UserInfo;
import org.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.maxkey.persistence.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "基于时间令牌验证 API文档模块")
@Controller
@RequestMapping(value={"/im/api/otp"})
public class RestTimeBasedOtpController {

	@Autowired
    @Qualifier("timeBasedOtpAuthn")
    protected AbstractOtpAuthn timeBasedOtpAuthn;
    
    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    
    @ApiOperation(value = "基于时间令牌验证 API文档模块", notes = "传递参数username和token",httpMethod="GET")
    @ResponseBody
    @RequestMapping(value = "/timebased/validate", method = RequestMethod.GET)
    public boolean getUser(@RequestParam String username,
    							 @RequestParam String token) {
    	
    	UserInfo validUserInfo = userInfoService.loadByUsername(username);
    	if(validUserInfo != null) {
    		if(timeBasedOtpAuthn.validate(validUserInfo, token)) {
    			return true;
    		}
    	}
    	
        return false;
    }

 
}
