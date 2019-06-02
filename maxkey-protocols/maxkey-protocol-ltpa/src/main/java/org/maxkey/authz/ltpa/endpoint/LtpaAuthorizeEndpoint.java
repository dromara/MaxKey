/**
 * 
 */
package org.maxkey.authz.ltpa.endpoint;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.authz.ltpa.endpoint.adapter.LtpaDefaultAdapter;
import org.maxkey.config.ApplicationConfig;
import org.maxkey.constants.BOOLEAN;
import org.maxkey.dao.service.TokenBasedDetailsService;
import org.maxkey.domain.apps.TokenBasedDetails;
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
public class LtpaAuthorizeEndpoint  extends AuthorizeBaseEndpoint{

	final static Logger _logger = LoggerFactory.getLogger(LtpaAuthorizeEndpoint.class);
	@Autowired
	TokenBasedDetailsService tokenBasedDetailsService;
	
	@Autowired
	ApplicationConfig applicationConfig;
	
	LtpaDefaultAdapter defaultLtpaAdapter=new LtpaDefaultAdapter();
	
	@RequestMapping("/authz/ltpa/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("id") String id){
		
		
		TokenBasedDetails ltpaDetails=tokenBasedDetailsService.get(id);
		_logger.debug(""+ltpaDetails);
		String cookieValue="";
		
		AbstractAuthorizeAdapter adapter;
		
		if(BOOLEAN.isTrue(ltpaDetails.getIsAdapter())){
			adapter =(AbstractAuthorizeAdapter)Instance.newInstance(ltpaDetails.getAdapter());
		}else{
			adapter =(AbstractAuthorizeAdapter)defaultLtpaAdapter;
		}
		
		String tokenData=adapter.generateInfo(
				WebContext.getUserInfo(), 
				ltpaDetails);
		
		String encryptTokenData=adapter.encrypt(
				tokenData, 
				ltpaDetails.getAlgorithmKey(), 
				ltpaDetails.getAlgorithm());
		
		String signTokenData=adapter.sign(
				encryptTokenData, 
				ltpaDetails);
		
		cookieValue=signTokenData;
		
		_logger.debug("Cookie Name : "+ltpaDetails.getCookieName());
		
		Cookie cookie= new Cookie(ltpaDetails.getCookieName(),cookieValue);
		
		Integer maxAge=Integer.parseInt(ltpaDetails.getExpires())*60;
		_logger.debug("Cookie Max Age :"+maxAge+" seconds.");
		cookie.setMaxAge(maxAge);
		
		cookie.setPath("/");
		//
		//cookie.setDomain("."+applicationConfig.getSubDomainName());
		//tomcat 8.5
		cookie.setDomain(applicationConfig.getSubDomainName());
		
		_logger.debug("Sub Domain Name : "+"."+applicationConfig.getSubDomainName());
		response.addCookie(cookie);
		
		if(ltpaDetails.getRedirectUri().indexOf(applicationConfig.getSubDomainName())>-1){
			return WebContext.redirect(ltpaDetails.getRedirectUri());
		}else{
			_logger.error(ltpaDetails.getRedirectUri()+" not in domain "+applicationConfig.getSubDomainName());
			return null;
		}
	}
}
