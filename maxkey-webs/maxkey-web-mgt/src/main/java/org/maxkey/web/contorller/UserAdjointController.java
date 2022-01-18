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
 

package org.maxkey.web.contorller;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.ConstsOperateMessage;
import org.maxkey.entity.UserInfoAdjoint;
import org.maxkey.persistence.service.UserInfoAdjointService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/useradjoint"})
public class UserAdjointController {
	final static Logger _logger = LoggerFactory.getLogger(UserAdjointController.class);
	
	@Autowired
	@Qualifier("userInfoAdjointService")
	UserInfoAdjointService userInfoAdjointService;

	
	@RequestMapping(value={"/list/{userId}"})
	public ModelAndView userinfoAdjointList(@PathVariable("userId") String userId){
	    ModelAndView modelAndView=new ModelAndView("/userinfo/userinfoAdjointList");
	    modelAndView.addObject("userId", userId);
		return modelAndView;
	}
	
	
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<UserInfoAdjoint> queryDataGrid(@ModelAttribute("userInfoAdjoint") UserInfoAdjoint userInfoAdjoint) {
		_logger.debug(""+userInfoAdjoint);
		userInfoAdjoint.setInstId(WebContext.getUserInfo().getInstId());
		return userInfoAdjointService.queryPageResults(userInfoAdjoint);
	}

	
	@RequestMapping(value = { "/forwardAdd/{userId}" })
	public ModelAndView forwardAdd(@PathVariable("userId") String userId) {
	    ModelAndView modelAndView=new ModelAndView("/userinfo/userinfoAdjointAdd");
        modelAndView.addObject("userId", userId);
        return modelAndView;
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("/userinfo/userinfoAdjointUpdate");
		UserInfoAdjoint userInfoAdjoint=userInfoAdjointService.get(id);
		modelAndView.addObject("model",userInfoAdjoint);
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"})
	public Message insert(@ModelAttribute("userInfoAdjoint") UserInfoAdjoint userInfoAdjoint) {
		_logger.debug("-Add  :" + userInfoAdjoint);
		userInfoAdjoint.setInstId(WebContext.getUserInfo().getInstId());
		if (userInfoAdjointService.insert(userInfoAdjoint)) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		
	}
	
	/**
	 * 查询
	 * @param group
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/query"}) 
	public Message query(@ModelAttribute("userInfoAdjoint") UserInfoAdjoint userInfoAdjoint) {
		_logger.debug("-query  :" + userInfoAdjoint);
		userInfoAdjoint.setInstId(WebContext.getUserInfo().getInstId());
		if (userInfoAdjointService.load(userInfoAdjoint)!=null) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_ERROR),MessageType.error);
		}
		
	}
	
	/**
	 * 修改
	 * @param group
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/update"})  
	public Message update(@ModelAttribute("userInfoAdjoint") UserInfoAdjoint userInfoAdjoint) {
		_logger.debug("-update  userInfoAdjoint :" + userInfoAdjoint);
		userInfoAdjoint.setInstId(WebContext.getUserInfo().getInstId());
		if (userInfoAdjointService.update(userInfoAdjoint)) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("userInfoAdjoint") UserInfoAdjoint userInfoAdjoint) {
		_logger.debug("-delete  group :" + userInfoAdjoint);
		
		if (userInfoAdjointService.deleteBatch(userInfoAdjoint.getId())) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.success);
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
		
	}
}
