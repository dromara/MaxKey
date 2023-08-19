/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.web.contorller;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.persistence.service.FileUploadService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = { "/config/profile" })
public class ProfileController {
    static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
	FileUploadService fileUploadService;

    @RequestMapping(value = { "/get" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(@CurrentUser UserInfo currentUser) {
        UserInfo userInfo = userInfoService.findByUsername(currentUser.getUsername());
		userInfo.trans();
        return new Message<UserInfo>(userInfo).buildResponse();
    }

    /**
     * 修改用户.
     * 
     * @param userInfo
     * @param result
     * @return
     */
    @ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> update(
				@RequestBody  UserInfo userInfo,
				@CurrentUser UserInfo currentUser,
                BindingResult result) {
        logger.debug(userInfo.toString());

//		if(userInfo.getExtraAttributeValue()!=null){
//			String []extraAttributeLabel=userInfo.getExtraAttributeName().split(",");
//			String []extraAttributeValue=userInfo.getExtraAttributeValue().split(",");
//			Map<String,String> extraAttributeMap=new HashMap<String,String> ();
//			for(int i=0;i<extraAttributeLabel.length;i++){
//				extraAttributeMap.put(extraAttributeLabel[i], extraAttributeValue[i]);
//			}
//			String extraAttribute=JsonUtils.object2Json(extraAttributeMap);
//			userInfo.setExtraAttribute(extraAttribute);
//		}
        if(StringUtils.isNotBlank(userInfo.getPictureId())) {
			userInfo.setPicture(fileUploadService.get(userInfo.getPictureId()).getUploaded());
			fileUploadService.remove(userInfo.getPictureId());
		}
        
        if (userInfoService.updateProfile(userInfo) > 0) {
        	return new Message<UserInfo>(Message.SUCCESS).buildResponse();
        } 
        
        return new Message<UserInfo>(Message.FAIL).buildResponse();
        
    }

}
