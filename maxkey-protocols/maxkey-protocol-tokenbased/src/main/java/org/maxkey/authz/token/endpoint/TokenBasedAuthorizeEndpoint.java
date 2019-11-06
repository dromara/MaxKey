/**
 * 
 */
package org.maxkey.authz.token.endpoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.authz.token.endpoint.adapter.TokenBasedDefaultAdapter;
import org.maxkey.config.ApplicationConfig;
import org.maxkey.constants.BOOLEAN;
import org.maxkey.dao.service.AppsTokenBasedDetailsService;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.AppsTokenBasedDetails;
import org.maxkey.util.Instance;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TokenBasedAuthorizeEndpoint  extends AuthorizeBaseEndpoint{

	final static Logger _logger = LoggerFactory.getLogger(TokenBasedAuthorizeEndpoint.class);
	@Autowired
	AppsTokenBasedDetailsService tokenBasedDetailsService;
	
	TokenBasedDefaultAdapter defaultTokenBasedAdapter=new TokenBasedDefaultAdapter();
	
	@Autowired
	ApplicationConfig applicationConfig;
	
	@RequestMapping("/authorize/tokenbased/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("id") String id){
		ModelAndView modelAndView=new ModelAndView();
		
		
		AppsTokenBasedDetails tokenBasedDetails=null;
		tokenBasedDetails=tokenBasedDetailsService.get(id);
		_logger.debug(""+tokenBasedDetails);
		
		Apps  application= getApp(id);
		tokenBasedDetails.setAdapter(application.getAdapter());
		tokenBasedDetails.setIsAdapter(application.getIsAdapter());
		
		AbstractAuthorizeAdapter adapter;
		if(BOOLEAN.isTrue(tokenBasedDetails.getIsAdapter())){
			adapter =(AbstractAuthorizeAdapter)Instance.newInstance(tokenBasedDetails.getAdapter());
		}else{
			adapter =(AbstractAuthorizeAdapter)defaultTokenBasedAdapter;
		}
		
		String tokenData=adapter.generateInfo(
				WebContext.getUserInfo(), 
				tokenBasedDetails);
		
		String encryptTokenData=adapter.encrypt(
				tokenData, 
				tokenBasedDetails.getAlgorithmKey(), 
				tokenBasedDetails.getAlgorithm());
		
		String signTokenData=adapter.sign(
				encryptTokenData, 
				tokenBasedDetails);
		
		modelAndView=adapter.authorize(
				WebContext.getUserInfo(), 
				tokenBasedDetails, 
				signTokenData, 
				modelAndView);
		
		return modelAndView;
		
	}

}
