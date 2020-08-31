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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.constants.ConstantsPasswordSetType;
import org.maxkey.constants.ConstantsTimeInterval;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.db.PasswordPolicyValidator;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value={"/safe"})
public class SafeController {
	final static Logger _logger = LoggerFactory.getLogger(SafeController.class);
	
	@Autowired
	private UserInfoService userInfoService;
	
	@ResponseBody
	@RequestMapping(value="/forward/changePasswod") 
	public ModelAndView fowardChangePasswod() {
			ModelAndView modelAndView=new ModelAndView("safe/changePassword");
			modelAndView.addObject("model", WebContext.getUserInfo());
			return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value="/changePassword") 
	public Message changePasswod(
			@RequestParam(value ="oldPassword",required = true) String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword) {
		
			if(userInfoService.changePassword(oldPassword,newPassword,confirmPassword)) {
				return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			}else {
				return  new Message(
				        WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR)+"<br>"
				        +WebContext.getAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT),
				        MessageType.error);
			}	
	}

	@RequestMapping(value="/changeExpiredPassword") 
	public ModelAndView changeExpiredPassword(
			@RequestParam(value ="oldPassword" ,required = false) String oldPassword,
			@RequestParam(value ="newPassword",required = false) String newPassword,
			@RequestParam(value ="confirmPassword",required = false) String confirmPassword) {
			ModelAndView modelAndView=new ModelAndView("passwordExpired");
	        if(newPassword ==null ||newPassword.equals("")) {
	            
	        }else if(userInfoService.changePassword(oldPassword,newPassword,confirmPassword)){
	            WebContext.getSession().setAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE,ConstantsPasswordSetType.PASSWORD_NORMAL);
				return WebContext.redirect("/index");
			}
	        
			Object errorMessage=WebContext.getAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT);
			UserInfo userInfo=WebContext.getUserInfo();
            modelAndView.addObject("model", userInfo);
            modelAndView.addObject("errorMessage", errorMessage==null?"":errorMessage);
			return modelAndView;
	}
	
	
	@RequestMapping(value="/changeInitPassword") 
	public ModelAndView changeInitPassword(
			@RequestParam(value ="oldPassword",required = false) String oldPassword,
			@RequestParam(value ="newPassword",required = false) String newPassword,
			@RequestParam(value ="confirmPassword",required = false) String confirmPassword) {
		ModelAndView modelAndView=new ModelAndView("passwordInitial");
        if(newPassword ==null ||newPassword.equals("")) {
            
        }else if(userInfoService.changePassword(oldPassword,newPassword,confirmPassword)){
            WebContext.getSession().setAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE,ConstantsPasswordSetType.PASSWORD_NORMAL);
			return WebContext.redirect("/index");
		}
		
        Object errorMessage=WebContext.getAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT);
        modelAndView.addObject("errorMessage", errorMessage==null?"":errorMessage);
        UserInfo userInfo=WebContext.getUserInfo();
        modelAndView.addObject("model", userInfo);
        return modelAndView;
	}
	

	@ResponseBody
	@RequestMapping(value="/forward/changeAppLoginPasswod") 
	public ModelAndView fowardChangeAppLoginPasswod() {
			ModelAndView modelAndView=new ModelAndView("safe/changeAppLoginPasswod");
			modelAndView.addObject("model", WebContext.getUserInfo());
			return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value="/changeAppLoginPasswod") 
	public Message changeAppLoginPasswod(
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword) {
		
		UserInfo userInfo =WebContext.getUserInfo();
		_logger.debug("App Login Password : "+userInfo.getAppLoginPassword());
		_logger.debug("App Login new Password : "+ReciprocalUtils.encode(newPassword));
		if(newPassword.equals(confirmPassword)){
			if(StringUtils.isNullOrBlank(userInfo.getAppLoginPassword())||userInfo.getAppLoginPassword().equals(ReciprocalUtils.encode(oldPassword))){
				userInfo.setAppLoginPassword(ReciprocalUtils.encode(newPassword));
				boolean change= userInfoService.changeAppLoginPassword(userInfo);
				_logger.debug(""+change);
				return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.prompt);
			}
		}
		
		return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR),MessageType.error);
		
	}
	
	
	@RequestMapping(value="/forward/setting") 
	public ModelAndView fowardSetting() {
			ModelAndView modelAndView=new ModelAndView("safe/setting");
			modelAndView.addObject("model", WebContext.getUserInfo());
			return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value="/setting") 
	public Message setting(
	        HttpServletRequest request,
            HttpServletResponse response,
			@RequestParam("authnType") String authnType,
			@RequestParam("mobile") String mobile,
			@RequestParam("mobileVerify") String mobileVerify,
			@RequestParam("email") String email,
			@RequestParam("emailVerify") String emailVerify,
			@RequestParam("theme") String theme) {
		UserInfo userInfo =WebContext.getUserInfo();
		userInfo.setAuthnType(Integer.parseInt(authnType));
		userInfoService.changeAuthnType(userInfo);
		
		userInfo.setMobile(mobile);
		userInfoService.changeMobile(userInfo);
		
		userInfo.setEmail(email);

        userInfo.setTheme(theme);
        WebContext.setCookie(response, WebConstants.THEME_COOKIE_NAME, theme, ConstantsTimeInterval.ONE_WEEK);
        
		userInfoService.changeEmail(userInfo);
		
		
		return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);
		
	}
	
}
