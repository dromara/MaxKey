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
 

package org.maxkey.web.apps.contorller;

import java.util.List;

import org.maxkey.authz.oauth2.provider.client.JdbcClientDetailsService;
import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.constants.ConstantsProtocols;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.AppsOAuth20Details;
import org.maxkey.domain.apps.oauth2.provider.client.BaseClientDetails;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/apps/oauth20"})
public class OAuth20DetailsController  extends BaseAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(OAuth20DetailsController.class);
	
	@Autowired
	JdbcClientDetailsService oauth20JdbcClientDetailsService;

	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		ModelAndView modelAndView=new ModelAndView("apps/oauth20/appAdd");
		AppsOAuth20Details oauth20Details=new AppsOAuth20Details();
		oauth20Details.setId(oauth20Details.generateId());
		oauth20Details.setSecret(ReciprocalUtils.generateKey(""));
		oauth20Details.setClientId(oauth20Details.getId());
		oauth20Details.setClientSecret(oauth20Details.getSecret());
		oauth20Details.setProtocol(ConstantsProtocols.OAUTH20);
		modelAndView.addObject("model",oauth20Details);
		return modelAndView;
	}
	
	
	@RequestMapping(value={"/add"})
	public ModelAndView insert(@ModelAttribute("oauth20Details") AppsOAuth20Details oauth20Details ) {
		_logger.debug("-Add  :" + oauth20Details);
		
		transform(oauth20Details);

		oauth20Details.setClientSecret(oauth20Details.getSecret());
		
		oauth20JdbcClientDetailsService.addClientDetails(oauth20Details.clientDetailsRowMapper());
		if (appsService.insertApp(oauth20Details)) {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+oauth20Details.getId());
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/oauth20/appUpdate");
		BaseClientDetails baseClientDetails=(BaseClientDetails)oauth20JdbcClientDetailsService.loadClientByClientId(id);
		Apps application=appsService.get(id);//
		decoderSecret(application);
		AppsOAuth20Details oauth20Details=new AppsOAuth20Details(application,baseClientDetails);
		oauth20Details.setSecret(application.getSecret());
		oauth20Details.setClientSecret(application.getSecret());
		_logger.debug("forwardUpdate "+oauth20Details);
		WebContext.setAttribute(oauth20Details.getId(), oauth20Details.getIcon());
		modelAndView.addObject("model",oauth20Details);
		return modelAndView;
	}
	/**
	 * modify
	 * @param application
	 * @return
	 */
	@RequestMapping(value={"/update"})  
	public ModelAndView update( @ModelAttribute("oauth20Details") AppsOAuth20Details oauth20Details) {
		//
		_logger.debug("-update  application :" + oauth20Details);
		_logger.debug("-update  oauth20Details use oauth20JdbcClientDetails" );
		oauth20Details.setClientSecret(oauth20Details.getSecret());
        oauth20JdbcClientDetailsService.updateClientDetails(oauth20Details.clientDetailsRowMapper());
        oauth20JdbcClientDetailsService.updateClientSecret(oauth20Details.getClientId(), oauth20Details.getClientSecret());
        
		transform(oauth20Details);
		
		if (appsService.updateApp(oauth20Details)) {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);
		} else {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+oauth20Details.getId());
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete/{id}"})
	public Message delete(@PathVariable("id") String id) {
		_logger.debug("-delete  application :" + id);
		oauth20JdbcClientDetailsService.removeClientDetails(id);
		if (appsService.remove(id)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
	}
	
	
	
	
	
}
