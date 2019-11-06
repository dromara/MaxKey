/**
 * 
 */
package org.maxkey.authz.endpoint;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.maxkey.client.utils.HttpEncoder;
import org.maxkey.constants.PROTOCOLS;
import org.maxkey.dao.service.AppsCasDetailsService;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.oauth2.provider.ClientDetails;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	private static final String OAUTH_V20_AUTHORIZATION_URL = "%s/oauth/v20/authorize?client_id=%s&response_type=code&redirect_uri=%s&approval_prompt=auto";
	
	@Autowired
	@Qualifier("oauth20JdbcClientDetailsService")
	private ClientDetailsService clientDetailsService;
	
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
			ClientDetails  clientDetails =clientDetailsService.loadClientByClientId(application.getId());
			_logger.debug(""+clientDetails);
			String authorizationUrl = String.format(OAUTH_V20_AUTHORIZATION_URL, 
							applicationConfig.getServerPrefix(),
							clientDetails.getClientId(), 
							HttpEncoder.encode(clientDetails.getRegisteredRedirectUri().toArray()[0].toString())
					);
			
			_logger.debug("authorizationUrl "+authorizationUrl);
			
			modelAndView=WebContext.redirect(authorizationUrl);
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.OPEN_ID_CONNECT)){
			// modelAndView=new ModelAndView("openid connect");
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.SAML20)){
			 modelAndView=WebContext.forward("/authz/saml20/idpinit/"+application.getId());
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.TOKENBASED)){
			modelAndView=WebContext.forward("/authorize/tokenbased/"+id);
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
