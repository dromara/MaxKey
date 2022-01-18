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
 

package org.maxkey.web.contorller;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.ConstsOperateMessage;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.SocialsProvider;
import org.maxkey.persistence.service.SocialsProviderService;
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
@RequestMapping(value={"/socialsprovider"})
public class SocialsProviderController {
	final static Logger _logger = LoggerFactory.getLogger(SocialsProviderController.class);
	
	@Autowired
	SocialsProviderService socialsProviderService;

	@RequestMapping(value={"/list"})
	public ModelAndView noticesList(){
		return new ModelAndView("socialsprovider/socialsProviderList");
	}

	
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<SocialsProvider> queryDataGrid(@ModelAttribute("socialsProvider") SocialsProvider socialsProvider) {
		_logger.debug(""+socialsProvider);
		socialsProvider.setInstId(WebContext.getUserInfo().getInstId());
		return socialsProviderService.queryPageResults(socialsProvider);
	}

	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		return new ModelAndView("socialsprovider/socialsProviderAdd");
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("socialsprovider/socialsProviderUpdate");
		SocialsProvider socialsProvider = socialsProviderService.get(id);
		socialsProvider.setClientSecret(PasswordReciprocal.getInstance().decoder(socialsProvider.getClientSecret()));
		modelAndView.addObject("model",socialsProvider);
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"})
	public Message insert(@ModelAttribute("socialsProvider") SocialsProvider socialsProvider) {
		_logger.debug("-Add  :" + socialsProvider);
		socialsProvider.setInstId(WebContext.getUserInfo().getInstId());
		socialsProvider.setClientSecret(PasswordReciprocal.getInstance().encode(socialsProvider.getClientSecret()));
		if (socialsProviderService.insert(socialsProvider)) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		
	}
	
	/**
	 * 查询
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/query"}) 
	public Message query(@ModelAttribute("socialsProvider") SocialsProvider socialsProvider) {
		_logger.debug("-query  :" + socialsProvider);
		socialsProvider.setInstId(WebContext.getUserInfo().getInstId());
		if (socialsProviderService.load(socialsProvider)!=null) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_ERROR),MessageType.error);
		}
		
	}
	
	/**
	 * 修改
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/update"})  
	public Message update(@ModelAttribute("socialsProvider") SocialsProvider socialsProvider) {
		_logger.debug("-update  socialsProvider :" + socialsProvider);
		socialsProvider.setInstId(WebContext.getUserInfo().getInstId());
		socialsProvider.setClientSecret(PasswordReciprocal.getInstance().encode(socialsProvider.getClientSecret()));
		if (socialsProviderService.update(socialsProvider)) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("socialsProvider") SocialsProvider socialsProvider) {
		_logger.debug("-delete  socialsProvider :" + socialsProvider);
		
		if (socialsProviderService.deleteBatch(socialsProvider.getId())) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
		
	}
}
