/**
 * 
 */
package org.maxkey.authz.endpoint;

import org.maxkey.constants.PROTOCOLS;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.dao.service.AccountsService;
import org.maxkey.dao.service.ApplicationsService;
import org.maxkey.domain.Accounts;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.Applications;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Crystal.Sea
 *
 */
public class AuthorizeBaseEndpoint {
	final static Logger _logger = LoggerFactory.getLogger(AuthorizeBaseEndpoint.class);
	
	@Autowired
	@Qualifier("applicationsService")
	protected ApplicationsService applicationsService;
	
	@Autowired
	AccountsService appAccountsService;
	
	
	protected Applications getApp(String id){
		Applications  application=null;
		if(id.equals("manage")){
			application =new Applications();
			application.setId("manage");
			application.setName("Manage App");
			application.setProtocol(PROTOCOLS.TOKENBASED);
			application.setIsAdapter(1);
			application.setAdapter("com.connsec.web.authorize.endpoint.adapter.TokenBasedJWTAdapter");
		}else{
			application=applicationsService.get(id);
		}
		
		if(application	==	null){
			_logger.error("Applications for id "+id + "  is null");
		}
		WebContext.setAttribute(AuthorizeBaseEndpoint.class.getName(), application);
		return application;
	}
	
	protected Applications getSessionApplication(String id){
		Object object= WebContext.getAttribute(AuthorizeBaseEndpoint.class.getName());
		Applications  application=null;
		if(object	!=	null){
			application	=	(Applications)object;
		}else{
			application	=	getApp(id);
		}
		return application;
	}
	
	protected Accounts getAppAccounts(Applications application){
		Accounts appAccount=new Accounts();
		UserInfo userInfo=WebContext.getUserInfo();
		if(application.getCredential()==Applications.CREDENTIALS.USER_DEFINED){
			
			appAccount=appAccountsService.load(new Accounts(userInfo.getId(),application.getId()));
			if(appAccount!=null){
				appAccount.setRelatedPassword(ReciprocalUtils.decoder(appAccount.getRelatedPassword()));
			}
		}else if(application.getCredential()==Applications.CREDENTIALS.SHARED){
			
			appAccount.setRelatedUsername(application.getSharedUsername());
			appAccount.setRelatedPassword(ReciprocalUtils.decoder(application.getSharedPassword()));
			
		}else if(application.getCredential()==Applications.CREDENTIALS.SYSTEM){
			
			if(application.getSystemUserAttr().equalsIgnoreCase("uid")){
				appAccount.setUsername(userInfo.getId());
			}else if(application.getSystemUserAttr().equalsIgnoreCase("username")){
				appAccount.setUsername(userInfo.getUsername());
			}else if(application.getSystemUserAttr().equalsIgnoreCase("employeeNumber")){
				appAccount.setUsername(userInfo.getEmployeeNumber());
			}else if(application.getSystemUserAttr().equalsIgnoreCase("email")){
				appAccount.setUsername(userInfo.getEmail());
			}else if(application.getSystemUserAttr().equalsIgnoreCase("windowsAccount")){
				appAccount.setUsername(userInfo.getWindowsAccount());
			}
			//decoder database stored encode password
			appAccount.setRelatedPassword(ReciprocalUtils.decoder(WebContext.getUserInfo().getDecipherable()));
			
			
		}else if(application.getCredential()==Applications.CREDENTIALS.NONE){
			
			appAccount.setUsername(userInfo.getUsername());
			appAccount.setRelatedPassword(userInfo.getUsername());
			
		}
		return appAccount;
	}
	
	public ModelAndView generateInitCredentialModelAndView(String appId,String redirect_uri){
		ModelAndView modelAndView=new ModelAndView("redirect:/authz/credential/forward?appId="+appId+"&redirect_uri="+redirect_uri);
		return modelAndView;
	}
	
}
