/**
 * 
 */
package org.maxkey.authz.endpoint;

import javax.servlet.http.HttpServletRequest;


import org.maxkey.constants.PROTOCOLS;
import org.maxkey.dao.service.AppsCasDetailsService;
import org.maxkey.domain.apps.Apps;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Crystal.Sea
 *
 */
@Controller
public class AuthorizeEndpoint extends AuthorizeBaseEndpoint{
	@Autowired
	AppsCasDetailsService casDetailsService;
	
	//all single sign on url
	@RequestMapping("/authz/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			@PathVariable("id") String id){
		
		ModelAndView modelAndView=null;
		
		Apps  application=getApp(id);
		WebContext.setAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID, id);
		
		if(application.getProtocol().equalsIgnoreCase(PROTOCOLS.EXTEND_API)){
			modelAndView=WebContext.forward("/authz/api/"+id);
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.FORMBASED)){
			 modelAndView=WebContext.forward("/authz/formbased/"+id);
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.OAUTH20)){
			 modelAndView=WebContext.forward("/authz/oauthv20/"+application.getId());
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.OPEN_ID_CONNECT)){
			// modelAndView=new ModelAndView("openid connect");
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.SAML20)){
			 modelAndView=WebContext.forward("/authz/saml20/idpinit/"+application.getId());
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.TOKENBASED)){
			modelAndView=WebContext.forward("/authz/tokenbased/"+id);
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.CAS)){
			modelAndView=WebContext.forward("/authz/cas/"+id);
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.DESKTOP)){
			modelAndView=WebContext.forward("/authz/desktop/"+id);
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.BASIC)){
			modelAndView=WebContext.redirect(application.getLoginUrl());
		}
		
		_logger.debug(modelAndView.getViewName());
		
		return modelAndView;
	}

	@RequestMapping("/authz/oauth10a/{id}")
	public ModelAndView authorizeOAuth10a(
			@PathVariable("id") String id){
		
		 String redirec_uri=getApp(id).getLoginUrl();
		return WebContext.redirect(redirec_uri);
		
	}
	
}
