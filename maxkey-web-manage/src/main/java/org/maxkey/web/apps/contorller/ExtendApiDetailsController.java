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
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.AppsExtendApiDetails;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/apps/extendapi"})
public class ExtendApiDetailsController  extends BaseAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(ExtendApiDetailsController.class);

	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		ModelAndView modelAndView=new ModelAndView("apps/extendapi/appAdd");
		AppsExtendApiDetails extendApiDetails=new AppsExtendApiDetails();
		extendApiDetails.setId(extendApiDetails.generateId());
		extendApiDetails.setProtocol(ConstantsProtocols.EXTEND_API);
		extendApiDetails.setSecret(ReciprocalUtils.generateKey(""));

		modelAndView.addObject("model",extendApiDetails);
		return modelAndView;
	}
	
	@RequestMapping(value={"/add"})
	public ModelAndView insert(@ModelAttribute("extendApiDetails") AppsExtendApiDetails extendApiDetails) {
		_logger.debug("-Add  :" + extendApiDetails);
		
		transform(extendApiDetails);
		
		if (appsService.insert(extendApiDetails)) {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+extendApiDetails.getId());
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/extendapi/appUpdate");
		Apps application= appsService.get(id);
		super.decoderSecret(application);
		AppsExtendApiDetails extendApiDetails=new AppsExtendApiDetails();
		BeanUtils.copyProperties(application, extendApiDetails);
		
		WebContext.setAttribute(extendApiDetails.getId(), extendApiDetails.getIcon());

		modelAndView.addObject("model",extendApiDetails);
		return modelAndView;
	}
	
	/**
	 * modify
	 * @param extendApiDetails
	 * @return
	 */
	@RequestMapping(value={"/update"})  
	public ModelAndView update(@ModelAttribute("extendApiDetails") AppsExtendApiDetails extendApiDetails) {
		_logger.debug("-update  extendApiDetails :" + extendApiDetails);
		transform(extendApiDetails);
		
		if (appsService.update(extendApiDetails)) {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+extendApiDetails.getId());
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete/{id}"})
	public Message delete(@PathVariable("id") String id) {
		_logger.debug("-delete  application :" + id);
		if (appsService.remove(id)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
	}

}
