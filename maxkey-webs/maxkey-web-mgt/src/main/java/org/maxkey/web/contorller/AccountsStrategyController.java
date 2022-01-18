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
import org.maxkey.entity.AccountsStrategy;
import org.maxkey.entity.Roles;
import org.maxkey.persistence.service.AccountsService;
import org.maxkey.persistence.service.AccountsStrategyService;
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
@RequestMapping(value={"/accountsstrategy"})
public class AccountsStrategyController {
	final static Logger _logger = LoggerFactory.getLogger(AccountsStrategyController.class);
	
	@Autowired
	@Qualifier("accountsStrategyService")
	AccountsStrategyService accountsStrategyService;
	
	@Autowired
	AccountsService accountsService;

	
	
	@RequestMapping(value={"/list"})
	public ModelAndView rolesList(){
		return new ModelAndView("accountsstrategy/accountsStrategyList");
	}

	@RequestMapping(value={"/select"})
	public ModelAndView selectAccountsStrategyList(){
		return new ModelAndView("accountsstrategy/selectAccountsStrategy");
	}
	
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<AccountsStrategy> queryDataGrid(@ModelAttribute("accountsStrategy") AccountsStrategy accountsStrategy) {
		_logger.debug(""+accountsStrategy);
		accountsStrategy.setInstId(WebContext.getUserInfo().getInstId());
		JpaPageResults<AccountsStrategy> accountsStrategyList =accountsStrategyService.queryPageResults(accountsStrategy);
		for (AccountsStrategy strategy : accountsStrategyList.getRows()){
			WebContext.setAttribute(strategy.getId(), strategy.getAppIcon());
		}
		return accountsStrategyList;
	}

	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		return new ModelAndView("accountsstrategy/accountsStrategyAdd");
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("accountsstrategy/accountsStrategyUpdate");
		AccountsStrategy accountsStrategy=accountsStrategyService.get(id);
		modelAndView.addObject("model",accountsStrategy);
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"})
	public Message insert(@ModelAttribute("accountsStrategy") AccountsStrategy accountsStrategy) {
		_logger.debug("-Add  :" + accountsStrategy);
		accountsStrategy.setInstId(WebContext.getUserInfo().getInstId());
		if (accountsStrategyService.insert(accountsStrategy)) {
		    accountsService.refreshByStrategy(accountsStrategy);
		    //rolesService.refreshDynamicRoles(role);
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		
	}
	
	/**
	 * 查询
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/query"}) 
	public Message query(@ModelAttribute("accountsStrategy") AccountsStrategy accountsStrategy) {
		_logger.debug("-query  :" + accountsStrategy);
		accountsStrategy.setInstId(WebContext.getUserInfo().getInstId());
		if (accountsStrategyService.load(accountsStrategy)!=null) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_ERROR),MessageType.error);
		}
		
	}
	
	/**
	 * 修改
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/update"})  
	public Message update(@ModelAttribute("accountsStrategy") AccountsStrategy accountsStrategy) {
		_logger.debug("-update  AccountsStrategy :" + accountsStrategy);
		accountsStrategy.setInstId(WebContext.getUserInfo().getInstId());
		if (accountsStrategyService.update(accountsStrategy)) {
		   // rolesService.refreshDynamicRoles(role);
		    accountsService.refreshByStrategy(accountsStrategy);
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("role") Roles role) {
		_logger.debug("-delete  role :" + role);
		
		if (accountsStrategyService.deleteById(role.getId())) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
		
	}
}
