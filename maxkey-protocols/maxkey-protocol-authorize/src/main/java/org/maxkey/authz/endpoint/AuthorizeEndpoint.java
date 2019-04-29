/**
 * 
 */
package org.maxkey.authz.endpoint;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.maxkey.client.oauth.builder.ServiceBuilder;
import org.maxkey.client.oauth.builder.api.ConnsecApi20;
import org.maxkey.client.oauth.oauth.OAuthService;
import org.maxkey.constants.PROTOCOLS;
import org.maxkey.dao.service.CasDetailsService;
import org.maxkey.domain.apps.Applications;
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

	@Autowired
	@Qualifier("oauth20JdbcClientDetailsService")
	private ClientDetailsService clientDetailsService;
	
	@Autowired
	CasDetailsService casDetailsService;
	
	//all single sign on url
	@RequestMapping("/authz/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			@PathVariable("id") String id){
		
		ModelAndView modelAndView=null;
		
		Applications  application=getApp(id);
		WebContext.setAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID, id);
		
		if(application.getProtocol().equalsIgnoreCase(PROTOCOLS.EXTEND_API)){
			
			modelAndView=WebContext.forward("/authz/api/"+id);
			
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.FORMBASED)){
			
			 modelAndView=WebContext.forward("/authz/formbased/"+id);
			 
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.OAUTH20)){
			ClientDetails  clientDetails =clientDetailsService.loadClientByClientId(application.getId());
			OAuthService service = new ServiceBuilder()
	        .provider(ConnsecApi20.class)
	        .apiKey(application.getId())
	        .apiSecret(application.getSecret())
	        .callback(clientDetails.getRegisteredRedirectUri().toArray()[0].toString())
	        .build();
			_logger.debug(""+clientDetails);
			
			String authorizationUrl = service.getAuthorizationUrl(null);
			modelAndView=WebContext.redirect(authorizationUrl);
			 
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.OAUTH10A)){
			/*
			 * Application must get request_token for authn
			 */
			modelAndView=WebContext.forward("/authz/oauth10a/"+id);
			
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.OPEN_ID_CONNECT)){
			
			// modelAndView=new ModelAndView("openid connect");
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.SAML20)){
			
			 modelAndView=WebContext.forward("/authz/saml20/idpinit/"+application.getId());
			 
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.SAML11)){
			
			modelAndView=WebContext.forward("/authz/saml11/idpinit/"+application.getId());
			 
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.TOKENBASED)){
			
			modelAndView=WebContext.forward("/authorize/tokenbased/"+id);
			
		}else if (application.getProtocol().equalsIgnoreCase(PROTOCOLS.LTPA)){
			
			modelAndView=WebContext.forward("/authz/ltpa/"+id);
			
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
