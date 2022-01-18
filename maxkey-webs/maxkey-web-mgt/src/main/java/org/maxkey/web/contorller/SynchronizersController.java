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

import java.util.List;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.ConstsOperateMessage;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.Synchronizers;
import org.maxkey.persistence.service.SynchronizersService;
import org.maxkey.synchronizer.ISynchronizerService;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/synchronizers"})
public class SynchronizersController {
	final static Logger _logger = LoggerFactory.getLogger(SynchronizersController.class);
	
	@Autowired
	@Qualifier("synchronizersService")
	SynchronizersService synchronizersService;
	
	@RequestMapping(value={"/list"})
	public ModelAndView groupsList(){
		return new ModelAndView("synchronizers/synchronizersList");
	}
	
	
	
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<Synchronizers> queryDataGrid(@ModelAttribute("synchronizers") Synchronizers synchronizers) {
		_logger.debug(""+synchronizers);
		synchronizers.setInstId(WebContext.getUserInfo().getInstId());
		return synchronizersService.queryPageResults(synchronizers);
	}

	

	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("synchronizers/synchronizerUpdate");
		Synchronizers synchronizers=synchronizersService.get(id);
		synchronizers.setCredentials(PasswordReciprocal.getInstance().decoder(synchronizers.getCredentials()));
		modelAndView.addObject("model",synchronizers);
		return modelAndView;
	}
	

	
	/**
	 * 修改
	 * @param group
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/update"})  
	public Message update(@ModelAttribute("synchronizers") Synchronizers synchronizers) {
		_logger.debug("-update  synchronizers :" + synchronizers);
		synchronizers.setInstId(WebContext.getUserInfo().getInstId());
		synchronizers.setCredentials(PasswordReciprocal.getInstance().encode(synchronizers.getCredentials()));
		if (synchronizersService.update(synchronizers)) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		
	}
	
	/**
	 * 修改
	 * @param group
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/sync"})  
	public Message sync(@RequestParam("id") String id) {
		_logger.debug("-update  synchronizers ids :" + id);
		
		List<String> ids = StringUtils.string2List(id, ",");
		try {
			for(String sysId : ids) {
				Synchronizers  synchronizer  = synchronizersService.get(sysId);
				synchronizer.setCredentials(PasswordReciprocal.getInstance().decoder(synchronizer.getCredentials()));
				_logger.debug("synchronizer " + synchronizer);
				ISynchronizerService synchronizerService = WebContext.getBean(synchronizer.getService(),ISynchronizerService.class);
				synchronizerService.setSynchronizer(synchronizer);
				synchronizerService.sync();
			}
		}catch(Exception e) {
			_logger.error("synchronizer Exception " , e);
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		return new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
	}

}
