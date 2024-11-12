/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.cnf.CnfLdapContext;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.ldap.LdapUtils;
import org.dromara.maxkey.ldap.activedirectory.ActiveDirectoryUtils;
import org.dromara.maxkey.persistence.service.CnfLdapContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value={"/config/ldapcontext"})
public class CnfLdapContextController {
	static final  Logger logger = LoggerFactory.getLogger(CnfLdapContextController.class);
	
	@Autowired
	CnfLdapContextService ldapContextService;

	@GetMapping(value={"/get"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<CnfLdapContext> get(@CurrentUser UserInfo currentUser){
		CnfLdapContext ldapContext = ldapContextService.get(currentUser.getInstId());
		if(ldapContext != null && StringUtils.isNoneBlank(ldapContext.getCredentials())) {
			ldapContext.setCredentials(PasswordReciprocal.getInstance().decoder(ldapContext.getCredentials()));
		}
		return new Message<>(ldapContext);
	}

	@PutMapping({"/update"})
	public Message<CnfLdapContext> update( @RequestBody CnfLdapContext ldapContext,@CurrentUser UserInfo currentUser,BindingResult result) {
		logger.debug("update ldapContext : {}" ,ldapContext);
		ldapContext.setCredentials(PasswordReciprocal.getInstance().encode(ldapContext.getCredentials()));
		ldapContext.setInstId(currentUser.getInstId());
		boolean updateResult = false;
		if(StringUtils.isBlank(ldapContext.getId())) {
			ldapContext.setId(ldapContext.getInstId());
			updateResult = ldapContextService.insert(ldapContext);
		}else {
			updateResult = ldapContextService.update(ldapContext);
		}
		if(updateResult) {
			return new Message<>(Message.SUCCESS);
		} else {
			return new Message<>(Message.FAIL);
		}
	}
	
	
	@GetMapping(value={"/test"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<CnfLdapContext> test(@CurrentUser UserInfo currentUser){
		CnfLdapContext ldapContext = ldapContextService.get(currentUser.getInstId());
		if(ldapContext != null && StringUtils.isNoneBlank(ldapContext.getCredentials())) {
			ldapContext.setCredentials(PasswordReciprocal.getInstance().decoder(ldapContext.getCredentials()));
		}
		
		LdapUtils ldapUtils = null;
		if(ldapContext != null) {
			if(ldapContext.getProduct().equalsIgnoreCase(LdapUtils.Product.ActiveDirectory)) {
				ldapUtils = new ActiveDirectoryUtils(
						ldapContext.getProviderUrl(),
						ldapContext.getPrincipal(),
						ldapContext.getCredentials(),
						ldapContext.getBasedn(),
						ldapContext.getMsadDomain());
			}else if(ldapContext.getProduct().equalsIgnoreCase(LdapUtils.Product.OpenLDAP)) {
				ldapUtils = new LdapUtils(
						ldapContext.getProviderUrl(),
						ldapContext.getPrincipal(),
				        ldapContext.getCredentials(),
				        ldapContext.getBasedn());
			}else if(ldapContext.getProduct().equalsIgnoreCase(LdapUtils.Product.StandardLDAP)) {
				ldapUtils = new LdapUtils(
						ldapContext.getProviderUrl(),
						ldapContext.getPrincipal(),
				        ldapContext.getCredentials(),
				        ldapContext.getBasedn());
			}
			
			if(ldapUtils != null && ldapUtils.openConnection() != null) {
				ldapUtils.close();
				return new Message<>(Message.SUCCESS);
			}
		}
				
		return new Message<>(Message.FAIL);
	}
}
