/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.maxkey.web.contorller;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.Accounts;
import org.maxkey.persistence.service.AccountsService;
import org.maxkey.persistence.service.AppsService;
import org.maxkey.persistence.service.UserInfoService;
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
public class AccountsController {
	final static Logger _logger = LoggerFactory.getLogger(AccountsController.class);

	@Autowired
	@Qualifier("accountsService")
	AccountsService accountsService;
	
	@Autowired
	@Qualifier("appsService")
	protected AppsService appsService;
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@RequestMapping(value={"/list"})
	public ModelAndView appAccountsList(){
		ModelAndView modelAndView=new ModelAndView("/accounts/appAccountsList");
		return modelAndView;
	}

	@RequestMapping(value={"/grid"})
	@ResponseBody
	public JpaPageResults<Accounts> grid(@ModelAttribute("appAccounts") Accounts appAccounts){
		return accountsService.queryPageResults(appAccounts);
		
	}
	
	@RequestMapping(value = { "/forwardSelect/{appId}" })
	public ModelAndView forwardSelect(@PathVariable("appId") String appId) {
		ModelAndView modelAndView=new ModelAndView("/accounts/appAccountsAddSelect");
		modelAndView.addObject("appId",appId);
		return modelAndView;
	}
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd(@ModelAttribute("appAccounts") Accounts appAccounts) {
		ModelAndView modelAndView=new ModelAndView("/accounts/appAccountsAdd");
		//Applications  app= appsService.get(appAccounts.getAppId());
		//appAccounts.setAppName(app.getName());
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
		accountsService.insert(appAccounts);
		return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);
		
	}
	
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("/accounts/appAccountsUpdate");
		Accounts appAccounts =accountsService.get(id);
		
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
		accountsService.update(appAccounts);
		return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);
		
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("appAccounts") Accounts appAccounts ) {
		
		_logger.debug("-delete  AppAccounts :" + appAccounts);
		
		String[] appAccountsds=appAccounts.getId().split(",");
		for(int i=0;i<appAccountsds.length;i++){
			accountsService.remove(appAccountsds[i]);
		}
		
		return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.success);
		
		
	}
	
}
