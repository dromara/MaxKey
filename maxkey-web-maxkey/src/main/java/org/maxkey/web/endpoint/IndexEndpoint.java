package org.maxkey.web.endpoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.maxkey.config.ApplicationConfig;
import org.maxkey.constants.ConstantsPasswordSetType;
import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Index
 * @author Crystal.Sea
 *
 */
@Controller
public class IndexEndpoint {
	private static Logger _logger = LoggerFactory.getLogger(IndexEndpoint.class);
	
	@Autowired
  	@Qualifier("applicationConfig")
  	ApplicationConfig applicationConfig;
	@RequestMapping(value={"/forwardindex"})
	public ModelAndView forwardindex(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
	    
		_logger.debug("IndexEndpoint /forwardindex.");
		ModelAndView modelAndView=new ModelAndView();
		Integer passwordSetType=(Integer)WebContext.getSession().getAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE);
		if(passwordSetType==null || passwordSetType==ConstantsPasswordSetType.PASSWORD_NORMAL){
			if(applicationConfig.getLoginConfig().getDefaultUri()!=null&&
					!applicationConfig.getLoginConfig().getDefaultUri().equals("")){
				if(applicationConfig.getLoginConfig().getDefaultUri().startsWith("http")){
					return  WebContext.redirect(applicationConfig.getLoginConfig().getDefaultUri());
				}
				return  WebContext.redirect(applicationConfig.getLoginConfig().getDefaultUri());
			}
			modelAndView.setViewName("index");
			return  modelAndView;
		}
		
		UserInfo userInfo=WebContext.getUserInfo();
		modelAndView.addObject("model", userInfo);
		if(passwordSetType==ConstantsPasswordSetType.PASSWORD_EXPIRED){
			modelAndView.setViewName("passwordExpired");
			return  modelAndView;
		}else if(passwordSetType==ConstantsPasswordSetType.INITIAL_PASSWORD||
				 passwordSetType==ConstantsPasswordSetType.MANAGER_CHANGED_PASSWORD){
			modelAndView.setViewName("passwordInitial");
			return  modelAndView;
		}
		
		
		
		return  new ModelAndView("index");
	}
	
	@RequestMapping(value={"/index"})
	public ModelAndView home(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		_logger.debug("IndexEndpoint /index.");
		
		if(applicationConfig.getLoginConfig().getDefaultUri()!=null&&
				!applicationConfig.getLoginConfig().getDefaultUri().equals("")	){
			return  WebContext.redirect(applicationConfig.getLoginConfig().getDefaultUri());
		}
		
		return  new ModelAndView("index");
	}
	
	@RequestMapping(value={"/"})
	public ModelAndView index() {
		_logger.debug("IndexEndpoint /.");
		return  new ModelAndView("index");
		
	}
}