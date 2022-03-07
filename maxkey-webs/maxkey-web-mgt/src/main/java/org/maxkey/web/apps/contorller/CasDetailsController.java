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

import org.maxkey.constants.ConstsOperateMessage;
import org.maxkey.constants.ConstsProtocols;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.entity.apps.AppsCasDetails;
import org.maxkey.persistence.service.AppsCasDetailsService;
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
@RequestMapping(value={"/apps/cas"})
public class CasDetailsController  extends BaseAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(CasDetailsController.class);
	
	@Autowired
	AppsCasDetailsService casDetailsService;
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		ModelAndView modelAndView=new ModelAndView("apps/cas/appAdd");
		AppsCasDetails casDetails =new AppsCasDetails();
		casDetails.setId(casDetails.generateId());
		casDetails.setProtocol(ConstsProtocols.CAS);
		casDetails.setSecret(ReciprocalUtils.generateKey(""));
		modelAndView.addObject("model",casDetails);
		return modelAndView;
	}
	
	
	@RequestMapping(value={"/add"})
	public ModelAndView insert(@ModelAttribute("casDetails") AppsCasDetails casDetails) {
		_logger.debug("-Add  :" + casDetails);

		transform(casDetails);
		casDetails.setInstId(WebContext.getUserInfo().getInstId());
		if (casDetailsService.insert(casDetails)&&appsService.insertApp(casDetails)) {
			  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+casDetails.getId());
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/cas/appUpdate");
		AppsCasDetails casDetails=casDetailsService.getAppDetails(id , false);
		super.decoderSecret(casDetails);
		casDetails.transIconBase64();
		
		modelAndView.addObject("model",casDetails);
		return modelAndView;
	}
	
	/**
	 * modify
	 * @param application
	 * @return
	 */
	@RequestMapping(value={"/update"})  
	public ModelAndView update(@ModelAttribute("casDetails") AppsCasDetails casDetails) {
		//
		_logger.debug("-update  application :" + casDetails);
		transform(casDetails);
		casDetails.setInstId(WebContext.getUserInfo().getInstId());
		if (casDetailsService.update(casDetails)&&appsService.updateApp(casDetails)) {
			  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+casDetails.getId());
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete/{id}"})
	public Message delete(@PathVariable("id") String id) {
		_logger.debug("-delete  application :" + id);
		if (casDetailsService.remove(id)&&appsService.remove(id)) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
	}
	
	
}
