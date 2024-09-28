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
 

package org.dromara.maxkey.web.config.contorller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.AccountsStrategy;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.AccountsService;
import org.dromara.maxkey.persistence.service.AccountsStrategyService;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value={"/config/accountsstrategy"})
public class AccountsStrategyController {
	static final  Logger logger = LoggerFactory.getLogger(AccountsStrategyController.class);
	
	@Autowired
	AccountsStrategyService accountsStrategyService;
	
	@Autowired
	AccountsService accountsService;
	
	@GetMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<JpaPageResults<AccountsStrategy>> fetch(@ModelAttribute AccountsStrategy accountsStrategy,@CurrentUser UserInfo currentUser) {
		accountsStrategy.setInstId(currentUser.getInstId());
		JpaPageResults<AccountsStrategy> accountsStrategyList =accountsStrategyService.fetchPageResults(accountsStrategy);
		for (AccountsStrategy strategy : accountsStrategyList.getRows()){
			strategy.transIconBase64();
		}
		logger.debug("Accounts Strategy {}" , accountsStrategyList);
		return new Message<>(accountsStrategyList);
	}

	@GetMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<AccountsStrategy> query(@ModelAttribute AccountsStrategy accountsStrategy,@CurrentUser UserInfo currentUser) {
		logger.debug("-query  : {}" , accountsStrategy);
		if (CollectionUtils.isNotEmpty(accountsStrategyService.query(accountsStrategy))) {
			 return new Message<>(Message.SUCCESS);
		} else {
			 return new Message<>(Message.FAIL);
		}
	}
	
	@GetMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<AccountsStrategy> get(@PathVariable("id") String id) {
		AccountsStrategy accountsStrategy = accountsStrategyService.get(id);
		return new Message<>(accountsStrategy);
	}
	
	@PostMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<AccountsStrategy> insert(@RequestBody AccountsStrategy accountsStrategy,@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  : {}" , accountsStrategy);
		
		if (accountsStrategyService.insert(accountsStrategy)) {
			accountsService.refreshByStrategy(accountsStrategy);
			return new Message<>(Message.SUCCESS);
		} else {
			return new Message<>(Message.FAIL);
		}
	}
	
	@PutMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<AccountsStrategy> update(@RequestBody  AccountsStrategy accountsStrategy,@CurrentUser UserInfo currentUser) {
		logger.debug("-update  : {}" , accountsStrategy);
		if (accountsStrategyService.update(accountsStrategy)) {
			accountsService.refreshByStrategy(accountsStrategy);
		    return new Message<>(Message.SUCCESS);
		} else {
			return new Message<>(Message.FAIL);
		}
	}
	
	@DeleteMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<AccountsStrategy> delete(@RequestParam("ids") List<String> ids,@CurrentUser UserInfo currentUser) {
		logger.debug("-delete  ids : {} " , ids);
		if (accountsStrategyService.deleteBatch(ids)) {
			 return new Message<>(Message.SUCCESS);
		} else {
			return new Message<>(Message.FAIL);
		}
	}
}
