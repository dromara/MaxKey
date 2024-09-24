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
import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.dromara.maxkey.authz.oauth2.provider.client.JdbcClientDetailsService;
import org.dromara.maxkey.constants.ConstsProtocols;
import org.dromara.maxkey.crypto.ReciprocalUtils;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.apps.AppsOAuth20Details;
import org.dromara.maxkey.entity.apps.oauth2.provider.client.BaseClientDetails;
import org.dromara.maxkey.entity.idm.UserInfo;
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
@RequestMapping(value={"/apps/oauth20"})
public class OAuth20DetailsController  extends BaseAppContorller {
	static final  Logger logger = LoggerFactory.getLogger(OAuth20DetailsController.class);
	
	@Autowired
	JdbcClientDetailsService oauth20JdbcClientDetailsService;

	@RequestMapping(value = { "/init" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> init() {
		AppsOAuth20Details oauth20Details=new AppsOAuth20Details();
		oauth20Details.setId(oauth20Details.generateId());
		oauth20Details.setSecret(ReciprocalUtils.generateKey(""));
		oauth20Details.setClientId(oauth20Details.getId());
		oauth20Details.setClientSecret(oauth20Details.getSecret());
		oauth20Details.setProtocol(ConstsProtocols.OAUTH20);
		return new Message<AppsOAuth20Details>(oauth20Details);
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> get(@PathVariable("id") String id) {
		BaseClientDetails baseClientDetails=(BaseClientDetails)oauth20JdbcClientDetailsService.loadClientByClientId(id,false);
		Apps application=appsService.get(id);//
		decoderSecret(application);
		AppsOAuth20Details oauth20Details=new AppsOAuth20Details(application,baseClientDetails);
		oauth20Details.setSecret(application.getSecret());
		oauth20Details.setClientSecret(application.getSecret());
		logger.debug("forwardUpdate {}" , oauth20Details);
		oauth20Details.transIconBase64();
		return new Message<AppsOAuth20Details>(oauth20Details);
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> add(
			@RequestBody  AppsOAuth20Details oauth20Details,
			@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  : {}", oauth20Details);
		
		if(oauth20Details.getProtocol().equalsIgnoreCase(ConstsProtocols.OAUTH21)) {
		    oauth20Details.setPkce(OAuth2Constants.PKCE_TYPE.PKCE_TYPE_YES);
		}
		transform(oauth20Details);

		oauth20Details.setClientSecret(oauth20Details.getSecret());
		oauth20Details.setInstId(currentUser.getInstId());
		
		oauth20JdbcClientDetailsService.addClientDetails(oauth20Details.clientDetailsRowMapper());
		if (appsService.insertApp(oauth20Details)) {
			return new Message<AppsOAuth20Details>(Message.SUCCESS);
		} else {
			return new Message<AppsOAuth20Details>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> update(
			@RequestBody  AppsOAuth20Details oauth20Details,
			@CurrentUser UserInfo currentUser) {
		logger.debug("-update  : {}" , oauth20Details);
		logger.debug("-update  application {}" , oauth20Details);
		logger.debug("-update  oauth20Details use oauth20JdbcClientDetails" );
		if(oauth20Details.getProtocol().equalsIgnoreCase(ConstsProtocols.OAUTH21)) {
            oauth20Details.setPkce(OAuth2Constants.PKCE_TYPE.PKCE_TYPE_YES);
        }
		
		transform(oauth20Details);
		oauth20Details.setClientSecret(oauth20Details.getSecret());
		oauth20Details.setInstId(currentUser.getInstId());
        oauth20JdbcClientDetailsService.updateClientDetails(oauth20Details.clientDetailsRowMapper());
        oauth20JdbcClientDetailsService.updateClientSecret(oauth20Details.getClientId(), oauth20Details.getClientSecret());
        
		if (appsService.updateApp(oauth20Details)) {
		    return new Message<AppsOAuth20Details>(Message.SUCCESS);
		} else {
			return new Message<AppsOAuth20Details>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> delete(
			@RequestParam("ids") List<String> ids,
			@CurrentUser UserInfo currentUser) {
		logger.debug("-delete  ids : {} " , ids);
		for (String id : ids){
			oauth20JdbcClientDetailsService.removeClientDetails(id);
		}
		if (appsService.deleteBatch(ids)) {
			 return new Message<AppsOAuth20Details>(Message.SUCCESS);
		} else {
			return new Message<AppsOAuth20Details>(Message.FAIL);
		}
	}
	
}
