package org.maxkey.authn.support.cas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.maxkey.config.ApplicationConfig;
import org.maxkey.constants.LOGINTYPE;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author Crystal.Sea
 *
 */
@Controller
public class CasEndpoint {
	private static Logger _logger = LoggerFactory.getLogger(CasEndpoint.class);
	
	@Autowired
  	@Qualifier("applicationConfig")
  	protected ApplicationConfig applicationConfig;
 	
	
	/**
	 * init login
	 * @return
	 */
 	@RequestMapping(value={"/logon/cas"})
	public ModelAndView casLogin(
			HttpServletRequest request,
			HttpServletResponse response) {
		_logger.debug("CasEndpoint /cas.");

		Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        
        
		String username=     assertion.getPrincipal().getName();
		
		_logger.debug("CAS username : "+username);
		
    	if(WebContext.setAuthentication(username,LOGINTYPE.CAS,"","","success")){
    		
    	}

		
		return WebContext.redirect("/login");
	}
}
