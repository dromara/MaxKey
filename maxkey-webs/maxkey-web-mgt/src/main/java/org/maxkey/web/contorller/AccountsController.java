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
import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.Accounts;
import org.maxkey.entity.AccountsStrategy;
import org.maxkey.entity.Message;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.AccountsService;
import org.maxkey.persistence.service.AccountsStrategyService;
import org.maxkey.persistence.service.AppsService;
import org.maxkey.persistence.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value={"/accounts"})
public class AccountsController {
	final static Logger _logger = LoggerFactory.getLogger(AccountsController.class);

	@Autowired
	AccountsService accountsService;
	
	@Autowired
	AccountsStrategyService accountsStrategyService;
	
	@Autowired
	AppsService appsService;
	
	@Autowired
	UserInfoService userInfoService;
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> fetch(@ModelAttribute Accounts accounts,@CurrentUser UserInfo currentUser) {
		_logger.debug(""+accounts);
		accounts.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<Accounts>>(
				accountsService.queryPageResults(accounts)).buildResponse();
	}

	@ResponseBody
	@RequestMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> query(@ModelAttribute Accounts account,@CurrentUser UserInfo currentUser) {
		_logger.debug("-query  :" + account);
		account.setInstId(currentUser.getInstId());
		if (accountsService.load(account)!=null) {
			 return new Message<Accounts>(Message.SUCCESS).buildResponse();
		} else {
			 return new Message<Accounts>(Message.SUCCESS).buildResponse();
		}
	}

	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		Accounts account=accountsService.get(id);
		account.setRelatedPassword(PasswordReciprocal.getInstance().decoder(account.getRelatedPassword()));
		return new Message<Accounts>(account).buildResponse();
	}

	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> insert(@RequestBody  Accounts account,@CurrentUser UserInfo currentUser) {
		_logger.debug("-Add  :" + account);
		account.setInstId(currentUser.getInstId());
		account.setRelatedPassword(PasswordReciprocal.getInstance().encode(account.getRelatedPassword()));
		if (accountsService.insert(account)) {
		    return new Message<Accounts>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Accounts>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> update(@RequestBody  Accounts account,@CurrentUser UserInfo currentUser) {
		_logger.debug("-update  :" + account);
		account.setInstId(currentUser.getInstId());
		account.setRelatedPassword(PasswordReciprocal.getInstance().encode(account.getRelatedPassword()));
		if (accountsService.update(account)) {
		    return new Message<Accounts>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Accounts>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> delete(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
		_logger.debug("-delete ids : {} " , ids);
		
		if (accountsService.deleteBatch(ids)) {
			 return new Message<Accounts>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Accounts>(Message.FAIL).buildResponse();
		}
		
	}
	
    @ResponseBody
    @RequestMapping(value = "/generate")
    public ResponseEntity<?> generate(@ModelAttribute Accounts account) {
    	AccountsStrategy accountsStrategy = accountsStrategyService.get(account.getStrategyId());
       	UserInfo  userInfo  = userInfoService.get(account.getUserId());
        return new Message<Object>(
        		Message.SUCCESS,
        		(Object)accountsService.generateAccount(userInfo,accountsStrategy)
        	).buildResponse();
    }

}
