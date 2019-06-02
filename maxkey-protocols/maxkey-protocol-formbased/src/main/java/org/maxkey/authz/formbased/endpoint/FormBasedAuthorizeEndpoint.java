/**
 * 
 */
package org.maxkey.authz.formbased.endpoint;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.authz.formbased.endpoint.adapter.FormBasedDefaultAdapter;
import org.maxkey.constants.BOOLEAN;
import org.maxkey.dao.service.FormBasedDetailsService;
import org.maxkey.domain.Accounts;
import org.maxkey.domain.apps.Applications;
import org.maxkey.domain.apps.FormBasedDetails;
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
public class FormBasedAuthorizeEndpoint  extends AuthorizeBaseEndpoint{
	final static Logger _logger = LoggerFactory.getLogger(FormBasedAuthorizeEndpoint.class);
	
	@Autowired
	FormBasedDetailsService formBasedDetailsService;
	
	FormBasedDefaultAdapter defaultFormBasedAdapter=new FormBasedDefaultAdapter();
	
	@RequestMapping("/authz/formbased/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			@PathVariable("id") String id){
		FormBasedDetails formBasedDetails=formBasedDetailsService.get(id);
		_logger.debug(""+formBasedDetails);
		Applications  application= getApplication(id);
		formBasedDetails.setAdapter(application.getAdapter());
		formBasedDetails.setIsAdapter(application.getIsAdapter());
		ModelAndView modelAndView=null;
		
		Accounts appUser=getAppAccounts(formBasedDetails);
		
		_logger.debug("Accounts "+appUser);
		if(appUser	==	null){
			return generateInitCredentialModelAndView(id,"/authorize/formbased/"+id);
			
		}else{
			formBasedDetails.setAppUser(appUser);
			
			modelAndView=new ModelAndView();
			
			AbstractAuthorizeAdapter adapter;
			
			if(BOOLEAN.isTrue(formBasedDetails.getIsAdapter())){
				adapter =(AbstractAuthorizeAdapter)Instance.newInstance(formBasedDetails.getAdapter());
			}else{
				adapter =(AbstractAuthorizeAdapter)defaultFormBasedAdapter;
			}
			
			modelAndView=adapter.authorize(
					WebContext.getUserInfo(), 
					formBasedDetails, 
					appUser.getRelatedUsername()+"."+appUser.getRelatedPassword(), 
					modelAndView);
		}
		
		
		
		return modelAndView;
	}
}
