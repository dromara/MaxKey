package org.maxkey.web.contorller;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.maxkey.config.ApplicationConfig;
import org.maxkey.dao.service.ForgotPasswordService;
import org.maxkey.dao.service.UserInfoService;
import org.maxkey.domain.ForgotPassword;
import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/forgotpassword"})
public class ForgotPasswordContorller {

	private static Logger _logger = LoggerFactory.getLogger(ForgotPasswordContorller.class);
	
	@Autowired
	ForgotPasswordService forgotPasswordService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired 
  	protected ApplicationConfig applicationConfig;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@RequestMapping(value={"/forward"})
	public ModelAndView forwardreg() {
		_logger.debug("Registration  /forgotpassword/forward.");
		return  new ModelAndView("forgotpassword/forward");
	}
	
	
	@RequestMapping(value={"/email"})
	public ModelAndView email(@RequestParam String email) {
		_logger.debug("Registration  /forgotpassword/email.");
		_logger.debug("email : "+email);
		UserInfo userInfo=forgotPasswordService.queryUserInfoByEmail(email);
		ModelAndView modelAndView=new ModelAndView("forgotpassword/email");
		modelAndView.addObject("emailsend", 0);
		modelAndView.addObject("email", email);
		
		if(userInfo!=null){
			ForgotPassword forgotPassword =new ForgotPassword();
			forgotPassword.setId(forgotPassword.generateId());
			forgotPassword.setEmail(email);
			forgotPassword.setUid(userInfo.getId());
			forgotPassword.setUsername(userInfo.getUsername());
			forgotPasswordService.insert(forgotPassword);
			
			HtmlEmail hemail = new HtmlEmail();
			  
			  try {
				 hemail.setHostName(applicationConfig.getEmailConfig().getSmtpHost());
				 hemail.setSmtpPort(applicationConfig.getEmailConfig().getPort());
				 hemail.setAuthenticator(new DefaultAuthenticator(applicationConfig.getEmailConfig().getUsername(), applicationConfig.getEmailConfig().getPassword()));
				
				 hemail.addTo(userInfo.getEmail(), userInfo.getNickName());
				 hemail.setFrom(applicationConfig.getEmailConfig().getSenderMail(), "ConnSec");
				 hemail.setSubject("ConnSec Cloud Identity & Access ReSet Password .");
				  
				// set the html message
				 String forgotPasswordUrl=WebContext.getHttpContextPath()+"/forgotpassword/resetpwd/"+forgotPassword.getId();
					
					
					// set the html message
					String emailText="<html>";
					 			emailText+="<a href='"+forgotPasswordUrl+"'>Reset Password</a><br>";
					 			emailText+=" or copy "+forgotPasswordUrl+" to brower.";
					 	   emailText+="</html>";
					 	   
					hemail.setHtmlMsg(emailText);
				
				// set the alternative message
				 hemail.setTextMsg("Your email client does not support HTML messages");
				
				// send the email
				 hemail.send();
				 modelAndView.addObject("emailsend", 1);
			} catch (EmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return modelAndView ;
	}
	
	@RequestMapping(value={"/resetpwd/{id}"})
	public ModelAndView resetpwd(@PathVariable("id") String id) {
		_logger.debug("Registration  /forgotpassword/resetpwd.");
		ForgotPassword forgotPassword=forgotPasswordService.get(id);
		ModelAndView mav=new ModelAndView("forgotpassword/resetpwd");
		if(forgotPassword!=null){
			mav.addObject("model", forgotPassword);
		}
		
		return mav;
	}
	
	@RequestMapping(value={"/setpassword/{id}"})
	public ModelAndView setPassWord(@PathVariable("id") String id,@RequestParam String password,@RequestParam String confirmpassword) {
		_logger.debug("forgotPassword  /forgotPassword/pwdreseted.");
		ModelAndView modelAndView=new ModelAndView("forgotpassword/pwdreseted");
		if(password.equals(confirmpassword)){
			ForgotPassword forgotPassword=forgotPasswordService.get(id);
			if(forgotPassword!=null){
				UserInfo userInfo=new UserInfo();
				userInfo.setId(forgotPassword.getUid());
				userInfo.setPassword(password);
				userInfo.setDecipherable(password);
				userInfo.setUsername(forgotPassword.getUsername());
				userInfoService.changePassword(userInfo);
				forgotPasswordService.remove(id);
				modelAndView.addObject("pwdreseted", 1);
			}else{
				modelAndView.addObject("pwdreseted", 2);
			}
		}else{
			modelAndView.addObject("pwdreseted", 0);
		}
		return  modelAndView;
	}
}
