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

import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.constants.ConstantsProtocols;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.apps.AppsTokenBasedDetails;
import org.maxkey.persistence.service.AppsTokenBasedDetailsService;
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
@RequestMapping(value={"/apps/tokenbased"})
public class TokenBasedDetailsController  extends BaseAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(TokenBasedDetailsController.class);
	
	@Autowired
	AppsTokenBasedDetailsService tokenBasedDetailsService;
	
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		ModelAndView modelAndView=new ModelAndView("apps/tokenbased/appAdd");
		AppsTokenBasedDetails tokenBasedDetails =new AppsTokenBasedDetails();
		tokenBasedDetails.setId(tokenBasedDetails.generateId());
		tokenBasedDetails.setProtocol(ConstantsProtocols.TOKENBASED);
		tokenBasedDetails.setSecret(ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.AES));
		tokenBasedDetails.setAlgorithmKey(tokenBasedDetails.getSecret());
		tokenBasedDetails.setUserPropertys("userPropertys");
		modelAndView.addObject("model",tokenBasedDetails);
		return modelAndView;
	}
	
	
	@RequestMapping(value={"/add"})
	public ModelAndView insert(@ModelAttribute("tokenBasedDetails") AppsTokenBasedDetails tokenBasedDetails) {
		_logger.debug("-Add  :" + tokenBasedDetails);
		
		transform(tokenBasedDetails);
		
		tokenBasedDetails.setAlgorithmKey(tokenBasedDetails.getSecret());
		
		if (tokenBasedDetailsService.insert(tokenBasedDetails)&&appsService.insertApp(tokenBasedDetails)) {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+tokenBasedDetails.getId());
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/tokenbased/appUpdate");
		AppsTokenBasedDetails tokenBasedDetails=tokenBasedDetailsService.getAppDetails(id);
		decoderSecret(tokenBasedDetails);
		String algorithmKey=passwordReciprocal.decoder(tokenBasedDetails.getAlgorithmKey());
		tokenBasedDetails.setAlgorithmKey(algorithmKey);
		WebContext.setAttribute(tokenBasedDetails.getId(), tokenBasedDetails.getIcon());

		modelAndView.addObject("model",tokenBasedDetails);
		return modelAndView;
	}
	/**
	 * modify
	 * @param application
	 * @return
	 */
	@RequestMapping(value={"/update"})  
	public ModelAndView update(@ModelAttribute("tokenBasedDetails") AppsTokenBasedDetails tokenBasedDetails) {
		//
		_logger.debug("-update  application :" + tokenBasedDetails);
		transform(tokenBasedDetails);
		tokenBasedDetails.setAlgorithmKey(tokenBasedDetails.getSecret());
		if (tokenBasedDetailsService.update(tokenBasedDetails)&&appsService.updateApp(tokenBasedDetails)) {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+tokenBasedDetails.getId());
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete/{id}"})
	public Message delete(@PathVariable("id") String id) {
		_logger.debug("-delete  application :" + id);
		if (tokenBasedDetailsService.remove(id)&&appsService.remove(id)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
	}
	
	
}
