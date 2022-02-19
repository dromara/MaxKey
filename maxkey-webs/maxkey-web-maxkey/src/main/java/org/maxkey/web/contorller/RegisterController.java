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

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstsStatus;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.Register;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.RegisterService;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.DateUtils;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/signup"})
public class RegisterController {
	private static Logger _logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	RegisterService registerService;
	
	@Autowired 
  	@Qualifier("applicationConfig")
  	protected ApplicationConfig applicationConfig;
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@RequestMapping(value={"/forward"})
	public ModelAndView forward() {
		_logger.debug("register  /register/register.");
		return  new ModelAndView("register/register");
	}
	
	@RequestMapping(value={"/forward/email"})
	public ModelAndView forwardEmail() {
		_logger.debug("register  /register/register.");
		return  new ModelAndView("register/registerInst");
	}
	
	//邮件验证注册
	@RequestMapping(value={"/register"})
	public ModelAndView reg(@ModelAttribute("register") Register register) {
		_logger.debug("register  /register/register.");
		_logger.debug(""+register);
		ModelAndView modelAndView= new ModelAndView("register/registered");
		
		UserInfo userInfo = registerService.findByEmail(register.getWorkEmail());
		
		if(userInfo!=null){
			modelAndView.addObject("registered", 1);
			return modelAndView;
		}
		
		register.setId(register.generateId());
		registerService.insert(register);
		HtmlEmail email = new HtmlEmail();
		  
		try {
			email.setHostName(applicationConfig.getEmailConfig().getSmtpHost());
			email.setSmtpPort(applicationConfig.getEmailConfig().getPort());
			email.setAuthenticator(new DefaultAuthenticator(
							applicationConfig.getEmailConfig().getUsername(), 
							applicationConfig.getEmailConfig().getPassword()
						));
			
			email.addTo(register.getWorkEmail(), register.getDisplayName());
			email.setFrom(applicationConfig.getEmailConfig().getSender(), "MaxKey");
			email.setSubject("MaxKey Identity & Access Registration activate Email .");
			  
			String activateUrl=WebContext.getHttpContextPath()+"/register/forward/activate/"+register.getId();
			
			
			// set the html message
			String emailText="<html>";
			 			emailText+="<a href='"+activateUrl+"'>activate</a><br>";
			 			emailText+=" or copy "+activateUrl+" to brower.";
			 	   emailText+="</html>";
			email.setHtmlMsg(emailText);
			
			// set the alternative message
			email.setTextMsg("Your email client does not support HTML messages");
			
			// send the email
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
		modelAndView.addObject("registered", 0); 
		return  modelAndView;
	}
	
	@GetMapping(value={"/forward/activate/{id}"})
	public ModelAndView confirm(@PathVariable("id") String id) {
		_logger.debug("register  /register/forward/activate.");
		Register register=registerService.get(id);
		ModelAndView mav=new ModelAndView("register/activate");
		if(register!=null){
			mav.addObject("model", register);
		}
		
		return mav;
	}
	
	@PostMapping(value={"/activate/{id}"})
	public ModelAndView setPassWord(@PathVariable("id") String id,
									@RequestParam String password,
									@RequestParam String confirmpassword) {
		_logger.debug("register  /register/setpassword.");
		ModelAndView modelAndView=new ModelAndView("register/activated");
		if(password.equals(confirmpassword)){
			Register register=registerService.get(id);
			if(register!=null){
				SqlSession  sqlSession  = SqlSessionUtils.getSqlSession(
									WebContext.getBean("sqlSessionFactory",SqlSessionFactory.class));
				sqlSession.commit(false);
				
				UserInfo userInfo=new UserInfo();
				userInfo.setUsername(register.getWorkEmail());
				userInfo.setDisplayName(register.getDisplayName());
				
				userInfo.setWorkPhoneNumber(register.getWorkPhone());
				userInfo.setEmail(register.getWorkEmail());
				userInfo.setStatus(ConstsStatus.ACTIVE);
				userInfo.setDecipherable(PasswordReciprocal.getInstance().encode(password));
				
				password = passwordEncoder.encode(password );
				userInfo.setPassword(password);
				//default InstId
				if(StringUtils.isEmpty(userInfo.getInstId())) {
					userInfo.setInstId("1");
				}
				userInfo.setPasswordLastSetTime(DateUtils.format(new Date(), DateUtils.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS));
				userInfoService.insert(userInfo);

				registerService.remove(id);
				sqlSession.commit(true);
				modelAndView.addObject("activate", 1);
			}else{
				modelAndView.addObject("activate", 2);
			}
		}else{
			modelAndView.addObject("activate", 0);
		}
		return  modelAndView;
	}
 	
	//直接注册
 	@RequestMapping(value={"/registeron"})
 	@ResponseBody
	public Message registeron(UserInfo userInfo,@RequestParam String emailMobile) throws ServletException, IOException {
 		
 		if(StringUtils.isEmpty(emailMobile)) {
 			return new Message(WebContext.getI18nValue("register.emailMobile.error"),"1");
 		}
 		
 		if(StringUtils.isValidEmail(emailMobile)) {
 			userInfo.setEmail(emailMobile);
 		}
 		
 		if(StringUtils.isValidMobileNo(emailMobile)) {
 			userInfo.setMobile(emailMobile);
 		}
 		
 		if(!(StringUtils.isValidEmail(emailMobile)||StringUtils.isValidMobileNo(emailMobile))) {
 			return new Message(WebContext.getI18nValue("register.emailMobile.error"),"1");
 		}
 		
 		UserInfo temp = userInfoService.findByEmailMobile(emailMobile);
 		
 		if(temp!=null) {
 			return new Message(WebContext.getI18nValue("register.emailMobile.exist"),"1");
 		}
 		
 		temp = userInfoService.findByUsername(userInfo.getUsername());
 		if(temp!=null) {
 			return new Message(WebContext.getI18nValue("register.user.error"),"1");
 		}
 		//default InstId
 		if(StringUtils.isEmpty(userInfo.getInstId())) {
			userInfo.setInstId("1");
		}
 		
 		userInfo.setStatus(ConstsStatus.ACTIVE);
 		
 		if(userInfoService.insert(userInfo)) {
 			return new Message(WebContext.getI18nValue("login.text.register.success"),"0");
 		}
 		return new Message(WebContext.getI18nValue("login.text.register.error"),"1");
 		
 	}

}
