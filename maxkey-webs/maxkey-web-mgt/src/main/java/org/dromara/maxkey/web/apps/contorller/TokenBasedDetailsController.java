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
 

package org.dromara.maxkey.web.apps.contorller;

import java.util.List;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.constants.ConstsProtocols;
import org.dromara.maxkey.crypto.ReciprocalUtils;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.apps.AppsJwtDetails;
import org.dromara.maxkey.entity.apps.AppsTokenBasedDetails;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.AppsTokenBasedDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value={"/apps/tokenbased"})
public class TokenBasedDetailsController  extends BaseAppContorller {
	static final  Logger logger = LoggerFactory.getLogger(TokenBasedDetailsController.class);
	
	@Autowired
	AppsTokenBasedDetailsService tokenBasedDetailsService;
	
	@RequestMapping(value = { "/init" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> init() {
		AppsTokenBasedDetails tokenBasedDetails =new AppsTokenBasedDetails();
		tokenBasedDetails.setId(tokenBasedDetails.generateId());
		tokenBasedDetails.setProtocol(ConstsProtocols.TOKENBASED);
		tokenBasedDetails.setSecret(ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.AES));
		tokenBasedDetails.setAlgorithmKey(tokenBasedDetails.getSecret());
		tokenBasedDetails.setUserPropertys("userPropertys");
		return new Message<AppsTokenBasedDetails>(tokenBasedDetails);
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> get(@PathVariable("id") String id) {
		AppsTokenBasedDetails tokenBasedDetails=tokenBasedDetailsService.getAppDetails(id , false);
		decoderSecret(tokenBasedDetails);
		String algorithmKey=passwordReciprocal.decoder(tokenBasedDetails.getAlgorithmKey());
		tokenBasedDetails.setAlgorithmKey(algorithmKey);
		tokenBasedDetails.transIconBase64();
		return new Message<AppsTokenBasedDetails>(tokenBasedDetails);
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> add(
			@RequestBody AppsTokenBasedDetails tokenBasedDetails,
			@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  : {}" , tokenBasedDetails);
		
		transform(tokenBasedDetails);
		
		tokenBasedDetails.setAlgorithmKey(tokenBasedDetails.getSecret());
		tokenBasedDetails.setInstId(currentUser.getInstId());
		if (tokenBasedDetailsService.insert(tokenBasedDetails)
				&&appsService.insertApp(tokenBasedDetails)) {
			return new Message<AppsJwtDetails>(Message.SUCCESS);
		} else {
			return new Message<AppsJwtDetails>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> update(
			@RequestBody AppsTokenBasedDetails tokenBasedDetails,
			@CurrentUser UserInfo currentUser) {
		logger.debug("-update  : {}" , tokenBasedDetails);
		transform(tokenBasedDetails);
		tokenBasedDetails.setAlgorithmKey(tokenBasedDetails.getSecret());
		tokenBasedDetails.setInstId(currentUser.getInstId());
		if (tokenBasedDetailsService.update(tokenBasedDetails)
				&&appsService.updateApp(tokenBasedDetails)) {
		    return new Message<AppsJwtDetails>(Message.SUCCESS);
		} else {
			return new Message<AppsJwtDetails>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> delete(
			@RequestParam("ids") List<String> ids,
			@CurrentUser UserInfo currentUser) {
		logger.debug("-delete  ids : {} " , ids);
		if (tokenBasedDetailsService.deleteBatch(ids)&&appsService.deleteBatch(ids)) {
			 return new Message<AppsJwtDetails>(Message.SUCCESS);
		} else {
			return new Message<AppsJwtDetails>(Message.FAIL);
		}
	}
	
}
