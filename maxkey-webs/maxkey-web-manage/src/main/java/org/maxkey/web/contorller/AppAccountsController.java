package org.maxkey.web.contorller;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.dao.service.AccountsService;
import org.maxkey.dao.service.ApplicationsService;
import org.maxkey.dao.service.UserInfoService;
import org.maxkey.domain.Accounts;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.Applications;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/app/accounts"})
public class AppAccountsController {
	final static Logger _logger = LoggerFactory.getLogger(AppAccountsController.class);

	@Autowired
	@Qualifier("appAccountsService")
	AccountsService appAccountsService;
	
	@Autowired
	@Qualifier("applicationsService")
	protected ApplicationsService applicationsService;
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@RequestMapping(value={"/list"})
	public ModelAndView appAccountsList(){
		ModelAndView modelAndView=new ModelAndView("app/accounts/list");
		return modelAndView;
	}

	@RequestMapping(value={"/grid"})
	@ResponseBody
	public JpaPageResults<Accounts> grid(@ModelAttribute("appAccounts") Accounts appAccounts){
		return appAccountsService.queryPageResults(appAccounts);
		
	}
	
	@RequestMapping(value = { "/forwardSelect/{appId}" })
	public ModelAndView forwardSelect(@PathVariable("appId") String appId) {
		ModelAndView modelAndView=new ModelAndView("app/accounts/appAccountsAddSelect");
		modelAndView.addObject("appId",appId);
		return modelAndView;
	}
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd(@ModelAttribute("appAccounts") Accounts appAccounts) {
		ModelAndView modelAndView=new ModelAndView("app/accounts/appAccountsAdd");
		Applications  app= applicationsService.get(appAccounts.getAppId());
		appAccounts.setAppName(app.getName());
		modelAndView.addObject("model",appAccounts);
		return modelAndView;
	}
	
	/**
	 * 
	 * @param group
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/add"})  
	public Message add(@ModelAttribute("appAccounts") Accounts appAccounts ) {
		_logger.debug("-update  :" + appAccounts);
		appAccounts.setRelatedPassword(ReciprocalUtils.encode(appAccounts.getRelatedPassword()));
		appAccountsService.insert(appAccounts);
		return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
		
	}
	
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("app/accounts/appAccountsUpdate");
		Accounts appAccounts =appAccountsService.get(id);
		
		appAccounts.setRelatedPassword(ReciprocalUtils.decoder(appAccounts.getRelatedPassword()));
		modelAndView.addObject("model",appAccounts);
		return modelAndView;
	}

	
	/**
	 * 
	 * @param group
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/update"})  
	public Message update(@ModelAttribute("appAccounts") Accounts appAccounts ) {
		_logger.debug("-update  :" + appAccounts);
		
		appAccounts.setRelatedPassword(ReciprocalUtils.encode(appAccounts.getRelatedPassword()));
		appAccountsService.update(appAccounts);
		return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
		
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("appAccounts") Accounts appAccounts ) {
		
		_logger.debug("-delete  AppAccounts :" + appAccounts);
		
		String[] appAccountsds=appAccounts.getId().split(",");
		for(int i=0;i<appAccountsds.length;i++){
			appAccountsService.remove(appAccountsds[i]);
		}
		
		return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.success);
		
		
	}
	
}
