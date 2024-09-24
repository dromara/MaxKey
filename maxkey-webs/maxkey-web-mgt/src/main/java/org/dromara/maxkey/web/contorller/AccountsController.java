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
 

package org.dromara.maxkey.web.contorller;

import java.util.List;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.constants.ConstsEntryType;
import org.dromara.maxkey.constants.ConstsAct;
import org.dromara.maxkey.constants.ConstsActResult;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.AccountsStrategy;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.AccountsService;
import org.dromara.maxkey.persistence.service.AccountsStrategyService;
import org.dromara.maxkey.persistence.service.AppsService;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value={"/accounts"})
public class AccountsController {
	static final  Logger _logger = LoggerFactory.getLogger(AccountsController.class);

	@Autowired
	AccountsService accountsService;
	
	@Autowired
	AccountsStrategyService accountsStrategyService;
	
	@Autowired
	AppsService appsService;
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	HistorySystemLogsService systemLog;
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Message<?> fetch(@ModelAttribute Accounts accounts,@CurrentUser UserInfo currentUser) {
		_logger.debug("fetch {}" , accounts);
		accounts.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<Accounts>>(
				accountsService.fetchPageResults(accounts));
	}

	@ResponseBody
	@RequestMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> query(@ModelAttribute Accounts account,@CurrentUser UserInfo currentUser) {
		_logger.debug("-query  : {}" , account);
		account.setInstId(currentUser.getInstId());
		if (accountsService.query(account)!=null) {
			 return new Message<Accounts>(Message.SUCCESS);
		} else {
			 return new Message<Accounts>(Message.SUCCESS);
		}
	}

	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> get(@PathVariable("id") String id) {
		Accounts account=accountsService.get(id);
		account.setRelatedPassword(PasswordReciprocal.getInstance().decoder(account.getRelatedPassword()));
		return new Message<Accounts>(account);
	}

	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> insert(@RequestBody  Accounts account,@CurrentUser UserInfo currentUser) {
		_logger.debug("-Add  : {}" , account);
		account.setInstId(currentUser.getInstId());
		account.setRelatedPassword(PasswordReciprocal.getInstance().encode(account.getRelatedPassword()));
		if (accountsService.insert(account)) {
			systemLog.insert(
					ConstsEntryType.ACCOUNT, 
					account, 
					ConstsAct.CREATE, 
					ConstsActResult.SUCCESS, 
					currentUser);
		    return new Message<Accounts>(Message.SUCCESS);
		} else {
			return new Message<Accounts>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> update(@RequestBody  Accounts account,@CurrentUser UserInfo currentUser) {
		_logger.debug("-update  : {}" , account);
		account.setInstId(currentUser.getInstId());
		account.setRelatedPassword(PasswordReciprocal.getInstance().encode(account.getRelatedPassword()));
		if (accountsService.update(account)) {
			systemLog.insert(
					ConstsEntryType.ACCOUNT, 
					account, 
					ConstsAct.UPDATE, 
					ConstsActResult.SUCCESS, 
					currentUser);
		    return new Message<Accounts>(Message.SUCCESS);
		} else {
			return new Message<Accounts>(Message.FAIL);
		}
	}
	
	
	@RequestMapping(value = { "/updateStatus" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Message<?> updateStatus(@ModelAttribute Accounts accounts,@CurrentUser UserInfo currentUser) {
		_logger.debug("accounts : {}" , accounts);
		Accounts loadAccount = accountsService.get(accounts.getId());
		accounts.setInstId(currentUser.getInstId());
		accounts.setAppId(loadAccount.getAppId());
		accounts.setAppName(loadAccount.getAppName());
		accounts.setUserId(loadAccount.getUserId());
		accounts.setUsername(loadAccount.getUsername());
		accounts.setDisplayName(loadAccount.getDisplayName());
		accounts.setRelatedUsername(loadAccount.getRelatedUsername());
		if (accountsService.updateStatus(accounts)) {
			systemLog.insert(
					ConstsEntryType.ACCOUNT, 
					accounts, 
					ConstsAct.statusActon.get(accounts.getStatus()), 
					ConstsActResult.SUCCESS, 
					currentUser);
		    return new Message<Accounts>(Message.SUCCESS);
		} else {
			return new Message<Accounts>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> delete(@RequestParam("ids") List<String> ids,@CurrentUser UserInfo currentUser) {
		_logger.debug("-delete ids : {} " , ids);
		
		if (accountsService.deleteBatch(ids)) {
			systemLog.insert(
					ConstsEntryType.ACCOUNT, 
					ids, 
					ConstsAct.DELETE, 
					ConstsActResult.SUCCESS, 
					currentUser);
			 return new Message<Accounts>(Message.SUCCESS);
		} else {
			return new Message<Accounts>(Message.FAIL);
		}
		
	}
	
    @ResponseBody
    @RequestMapping(value = "/generate")
    public Message<?> generate(@ModelAttribute Accounts account) {
    	AccountsStrategy accountsStrategy = accountsStrategyService.get(account.getStrategyId());
       	UserInfo  userInfo  = userInfoService.get(account.getUserId());
        return new Message<Object>(
        		Message.SUCCESS,
        		(Object)accountsService.generateAccount(userInfo,accountsStrategy)
        	);
    }

}
