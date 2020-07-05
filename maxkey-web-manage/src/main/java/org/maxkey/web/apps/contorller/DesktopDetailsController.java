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

import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.constants.ConstantsProtocols;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.apps.AppsDesktopDetails;
import org.maxkey.persistence.service.AppsDesktopDetailsService;
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
@RequestMapping(value={"/apps/desktop"})
public class DesktopDetailsController  extends BaseAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(DesktopDetailsController.class);
	
	@Autowired
	AppsDesktopDetailsService desktopDetailsService;
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		ModelAndView modelAndView=new ModelAndView("apps/desktop/appAdd");
		AppsDesktopDetails desktopDetails=new AppsDesktopDetails();
		desktopDetails.setId(desktopDetails.generateId());
		desktopDetails.setProtocol(ConstantsProtocols.DESKTOP);
		desktopDetails.setSecret(ReciprocalUtils.generateKey(""));

		modelAndView.addObject("model",desktopDetails);
		return modelAndView;
	}
	
	

	@RequestMapping(value={"/add"})
	public ModelAndView insert(@ModelAttribute("desktopDetails") AppsDesktopDetails desktopDetails) {
		_logger.debug("-Add  :" + desktopDetails);
		
		transform(desktopDetails);
		desktopDetailsService.insert(desktopDetails);
		if (appsService.insertApp(desktopDetails)) {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+desktopDetails.getId());
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/desktop/appUpdate");
		AppsDesktopDetails desktopDetails=desktopDetailsService.getAppDetails(id);
		decoderSecret(desktopDetails);
		decoderSharedPassword(desktopDetails);
		WebContext.setAttribute(desktopDetails.getId(), desktopDetails.getIcon());

		modelAndView.addObject("model",desktopDetails);
		return modelAndView;
	}
	/**
	 * modify
	 * @param application
	 * @return
	 */

	@RequestMapping(value={"/update"})  
	public ModelAndView update(@ModelAttribute("desktopDetails") AppsDesktopDetails desktopDetails) {
		//
		_logger.debug("-update  application :" + desktopDetails);
		transform(desktopDetails);

		if (desktopDetailsService.update(desktopDetails)&&appsService.updateApp(desktopDetails)) {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+desktopDetails.getId());
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete/{id}"})
	public Message delete(@PathVariable("id") String id) {
		_logger.debug("-delete  application :" + id);
		if (desktopDetailsService.remove(id)&&appsService.remove(id)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
	}
	
	
}
