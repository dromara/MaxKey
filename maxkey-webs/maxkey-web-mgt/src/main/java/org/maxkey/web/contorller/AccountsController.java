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
import org.maxkey.constants.ConstsOperateMessage;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.Accounts;
import org.maxkey.entity.AccountsStrategy;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.AccountsService;
import org.maxkey.persistence.service.AccountsStrategyService;
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
@RequestMapping(value={"/accounts"})
public class AccountsController {
	final static Logger _logger = LoggerFactory.getLogger(AccountsController.class);

	@Autowired
	@Qualifier("accountsService")
	AccountsService accountsService;
	
	@Autowired
	@Qualifier("accountsStrategyService")
	AccountsStrategyService accountsStrategyService;
	
	@Autowired
	@Qualifier("appsService")
	protected AppsService appsService;
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@RequestMapping(value={"/list"})
	public ModelAndView appAccountsList(){
		ModelAndView modelAndView=new ModelAndView("/accounts/accountsList");
		return modelAndView;
	}

	@RequestMapping(value={"/grid"})
	@ResponseBody
	public JpaPageResults<Accounts> grid(@ModelAttribute("appAccounts") Accounts appAccounts){
		appAccounts.setInstId(WebContext.getUserInfo().getInstId());
		return accountsService.queryPageResults(appAccounts);
		
	}
	
	@RequestMapping(value = { "/forwardSelect/{appId}" })
	public ModelAndView forwardSelect(@PathVariable("appId") String appId) {
		ModelAndView modelAndView=new ModelAndView("/accounts/accountsAddSelect");
		modelAndView.addObject("appId",appId);
		return modelAndView;
	}
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd(@ModelAttribute("appAccounts") Accounts appAccounts) {
		ModelAndView modelAndView=new ModelAndView("/accounts/accountsAdd");
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
		appAccounts.setInstId(WebContext.getUserInfo().getInstId());
		appAccounts.setRelatedPassword(PasswordReciprocal.getInstance().encode(appAccounts.getRelatedPassword()));
		accountsService.insert(appAccounts);
		return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
		
	}
	
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("/accounts/accountsUpdate");
		Accounts appAccounts =accountsService.get(id);
		
		appAccounts.setRelatedPassword(PasswordReciprocal.getInstance().decoder(appAccounts.getRelatedPassword()));
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
		appAccounts.setInstId(WebContext.getUserInfo().getInstId());
		appAccounts.setRelatedPassword(PasswordReciprocal.getInstance().encode(appAccounts.getRelatedPassword()));
		accountsService.update(appAccounts);
		return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
		
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("appAccounts") Accounts appAccounts ) {
		
		_logger.debug("-delete  AppAccounts :" + appAccounts);
		
		accountsService.deleteBatch(appAccounts.getId());
		
		return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.success);
		
		
	}
	
    @ResponseBody
    @RequestMapping(value = "/generate")
    public String generate(@ModelAttribute("appAccounts") Accounts appAccounts) {
    	AccountsStrategy accountsStrategy = accountsStrategyService.get(appAccounts.getStrategyId());
       	UserInfo  userInfo  = userInfoService.get(appAccounts.getUserId());
    	return accountsService.generateAccount(userInfo,accountsStrategy);
    }
}
