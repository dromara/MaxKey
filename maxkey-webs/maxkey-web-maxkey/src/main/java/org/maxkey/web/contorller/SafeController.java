package org.maxkey.web.contorller;

import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.dao.service.UserInfoService;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
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
		
			changeUserPassword(oldPassword,newPassword,confirmPassword);
			
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
		
	}

	@RequestMapping(value="/changeExpiredPassword") 
	public ModelAndView changeExpiredPassword(
			@RequestParam(value ="oldPassword",required = false) String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword) {
			ModelAndView modelAndView=new ModelAndView("passwordExpired");
		
			if(changeUserPassword(oldPassword,newPassword,confirmPassword)){
				return WebContext.redirect("/index");
				//modelAndView.setViewName("index");
			}
				
		
			new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
		 
			return modelAndView;
	}
	
	
	@RequestMapping(value="/changeInitPassword") 
	public ModelAndView changeInitPassword(
			@RequestParam(value ="oldPassword",required = false) String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword) {
		ModelAndView modelAndView=new ModelAndView("passwordInitial");
		
		if(changeUserPassword(oldPassword,newPassword,confirmPassword)){
			return WebContext.redirect("/index");
			//modelAndView.setViewName("index");
		}
		
		  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
		  return modelAndView;
	}
	
	public boolean changeUserPassword(String oldPassword,
									String newPassword,
									String confirmPassword){
		UserInfo userInfo =WebContext.getUserInfo();
		if(newPassword.equals(confirmPassword)){
			if(oldPassword==null || 
					userInfo.getPassword().equals(passwordEncoder.encode(PasswordReciprocal.getInstance().rawPassword(userInfo.getUsername(),oldPassword)))){
				userInfo.setPassword(newPassword);
				userInfoService.changePassword(userInfo);
				//TODO syncProvisioningService.changePassword(userInfo);
				return true;
			}
		}
		return false;
		
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
		_logger.debug("App Login Password : "+ReciprocalUtils.decoder(userInfo.getAppLoginPassword()));
		if(newPassword.equals(confirmPassword)){
			if(StringUtils.isNullOrBlank(userInfo.getAppLoginPassword())||userInfo.getAppLoginPassword().equals(ReciprocalUtils.encode(oldPassword))){
				userInfo.setAppLoginPassword(ReciprocalUtils.encode(newPassword));
				boolean change= userInfoService.changeAppLoginPassword(userInfo);
				_logger.debug(""+change);
				return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.prompt);
			}
		}
		
		return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
		
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
			@RequestParam("authnType") String authnType) {
		UserInfo userInfo =WebContext.getUserInfo();
		userInfo.setAuthnType(Integer.parseInt(authnType));
		
		userInfoService.changeAuthnType(userInfo);
		
		return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
		
	}
	
	@RequestMapping(value="/forward/question") 
	public ModelAndView fowardQuestion() {
			ModelAndView modelAndView=new ModelAndView("safe/question");
			modelAndView.addObject("model", WebContext.getUserInfo());
			return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value="/question") 
	public Message question(
			@RequestParam("passwordQuestion") String passwordQuestion,
			@RequestParam("passwordAnswer")   String passwordAnswer) {
		UserInfo userInfo =WebContext.getUserInfo();
		userInfo.setPasswordQuestion(userInfo.getPasswordQuestion());
		userInfo.setPasswordAnswer(userInfo.getPasswordAnswer());
		userInfoService.changePasswordQuestion(userInfo);
		return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
		
	}
	
	@RequestMapping(value="/forward/mobile") 
	public ModelAndView fowardMobile() {
			ModelAndView modelAndView=new ModelAndView("safe/mobile");
			modelAndView.addObject("model", WebContext.getUserInfo());
			return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value="/mobile") 
	public Message mobile(
			@RequestParam("mobile") String mobile,
			@RequestParam("verify") String verify) {
		
		UserInfo userInfo =WebContext.getUserInfo();
		userInfo.setMobile(mobile);
		userInfoService.changeMobile(userInfo);
		return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
		
	}
	
	@RequestMapping(value="/forward/email") 
	public ModelAndView fowardEmail() {
			ModelAndView modelAndView=new ModelAndView("safe/email");
			modelAndView.addObject("model", WebContext.getUserInfo());
			return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value="/email") 
	public Message email(
			@RequestParam("email") String email,
			@RequestParam("verify") String verify) {
		
		UserInfo userInfo =WebContext.getUserInfo();
		userInfo.setEmail(email);
		userInfoService.changeEmail(userInfo);

		return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
		
	}
	
}
