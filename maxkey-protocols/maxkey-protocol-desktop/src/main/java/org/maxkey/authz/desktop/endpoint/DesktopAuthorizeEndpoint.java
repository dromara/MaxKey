/**
 * 
 */
package org.maxkey.authz.desktop.endpoint;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.authz.desktop.endpoint.adapter.DesktopDefaultAdapter;
import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.constants.BOOLEAN;
import org.maxkey.dao.service.AppsDesktopDetailsService;
import org.maxkey.domain.Accounts;
import org.maxkey.domain.apps.AppsDesktopDetails;
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
public class DesktopAuthorizeEndpoint  extends AuthorizeBaseEndpoint{
	final static Logger _logger = LoggerFactory.getLogger(DesktopAuthorizeEndpoint.class);
	
	@Autowired
	AppsDesktopDetailsService desktopDetailsService;
	
	DesktopDefaultAdapter defaultDesktopAdapter=new DesktopDefaultAdapter();
	
	@RequestMapping("/authz/desktop/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			@PathVariable("id") String id){
		AppsDesktopDetails desktopDetails=desktopDetailsService.get(id);
		_logger.debug(""+desktopDetails);
		
		Accounts appUser=getAccounts(desktopDetails);
		if(appUser	==	null){
			return generateInitCredentialModelAndView(id,"/authorize/desktop/"+id);
			
		}else{
			desktopDetails.setAppUser(appUser);
			ModelAndView modelAndView=new ModelAndView();
			
			AbstractAuthorizeAdapter adapter;
			if(BOOLEAN.isTrue(desktopDetails.getIsAdapter())){
				adapter =(AbstractAuthorizeAdapter)Instance.newInstance(desktopDetails.getAdapter());
			}else{
				adapter =(AbstractAuthorizeAdapter)defaultDesktopAdapter;
			}
			
			String paramString=adapter.generateInfo(WebContext.getUserInfo(), desktopDetails);
			
			String encryptParamString=adapter.encrypt(paramString, null, null);
			
			String signParamString=adapter.sign(encryptParamString, desktopDetails);
			
			modelAndView=adapter.authorize(
					WebContext.getUserInfo(), 
					desktopDetails,
					signParamString, 
					modelAndView);
			
			return modelAndView;
		}
	}
}
