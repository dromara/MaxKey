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

import javax.validation.Valid;
import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageScope;
import org.maxkey.web.message.MessageType;
import org.maxkey.web.message.OperateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = { "/profile" })
public class ProfileController {
    static final Logger _logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = { "/myProfile" })
    public ModelAndView forwardBasic() {
        ModelAndView modelAndView = new ModelAndView("profile/myProfile");
        UserInfo userInfo = userInfoService.loadByUsername(WebContext.getUserInfo().getUsername());
        WebContext.getSession().setAttribute(userInfo.getId(), userInfo.getPicture());

        //  HashMap<String,Object>extraAttributeMap=new HashMap<String,Object>();
        //  extraAttributeMap=(HashMap<String,Object>)JsonUtils.json2Object(userInfo.getExtraAttribute(),extraAttributeMap);
        //  modelAndView.addObject("extraAttributeMap", extraAttributeMap);
        //  _logger.info("extraAttributeMap : "+extraAttributeMap);
        //
        modelAndView.addObject("model", userInfo);
        return modelAndView;
    }

    /**
     * 修改用户.
     * 
     * @param userInfo
     * @param result
     * @return
     */
    @RequestMapping(value = "/update/myProfile")
    public ModelAndView updatebasic(
                @Valid @ModelAttribute("userInfo") UserInfo userInfo,
                BindingResult result) {
        _logger.debug(userInfo.toString());

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

        if (userInfoService.updateProfile(userInfo) > 0) {
            new Message(
                    WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS), 
                    userInfo, MessageType.success,
                    OperateType.add, MessageScope.DB);

        } else {
            new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR), MessageType.error);
        }

        return WebContext.redirect("/profile/myProfile");

    }

}
