/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.SocialsProvider;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.SocialsProviderService;
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
@RequestMapping(value={"/config/socialsprovider"})
public class SocialsProviderController {
	static final  Logger logger = LoggerFactory.getLogger(SocialsProviderController.class);
	
	@Autowired
	SocialsProviderService socialsProviderService;
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Message<?> fetch(@ModelAttribute SocialsProvider socialsProvider,@CurrentUser UserInfo currentUser) {
		logger.debug("fetch {}" ,socialsProvider);
		socialsProvider.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<SocialsProvider>>(
				socialsProviderService.fetchPageResults(socialsProvider));
	}

	@ResponseBody
	@RequestMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> query(@ModelAttribute SocialsProvider socialsProvider,@CurrentUser UserInfo currentUser) {
		logger.debug("-query  : {}" , socialsProvider);
		socialsProvider.setInstId(currentUser.getInstId());
		if (socialsProviderService.query(socialsProvider)!=null) {
			 return new Message<SocialsProvider>(Message.SUCCESS);
		} else {
			 return new Message<SocialsProvider>(Message.SUCCESS);
		}
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> get(@PathVariable("id") String id) {
		SocialsProvider socialsProvider=socialsProviderService.get(id);
		socialsProvider.setClientSecret(PasswordReciprocal.getInstance().decoder(socialsProvider.getClientSecret()));
		return new Message<SocialsProvider>(socialsProvider);
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> insert(@RequestBody  SocialsProvider socialsProvider,@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  : {}" , socialsProvider);
		socialsProvider.setInstId(currentUser.getInstId());
		socialsProvider.setClientSecret(PasswordReciprocal.getInstance().encode(socialsProvider.getClientSecret()));
		if (socialsProviderService.insert(socialsProvider)) {
			return new Message<SocialsProvider>(Message.SUCCESS);
		} else {
			return new Message<SocialsProvider>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> update(@RequestBody  SocialsProvider socialsProvider,@CurrentUser UserInfo currentUser) {
		logger.debug("-update  : {}" , socialsProvider);
		socialsProvider.setInstId(currentUser.getInstId());
		socialsProvider.setClientSecret(PasswordReciprocal.getInstance().encode(socialsProvider.getClientSecret()));
		if (socialsProviderService.update(socialsProvider)) {
		    return new Message<SocialsProvider>(Message.SUCCESS);
		} else {
			return new Message<SocialsProvider>(Message.FAIL);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> delete(@RequestParam("ids") List<String> ids,@CurrentUser UserInfo currentUser) {
		logger.debug("-delete  ids : {} " , ids);
		if (socialsProviderService.deleteBatch(ids)) {
			 return new Message<SocialsProvider>(Message.SUCCESS);
		} else {
			return new Message<SocialsProvider>(Message.FAIL);
		}
	}

}
