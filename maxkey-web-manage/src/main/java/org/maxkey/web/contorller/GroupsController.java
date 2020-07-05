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
import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.domain.Groups;
import org.maxkey.persistence.service.GroupsService;
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
@RequestMapping(value={"/groups"})
public class GroupsController {
	final static Logger _logger = LoggerFactory.getLogger(GroupsController.class);
	
	@Autowired
	@Qualifier("groupsService")
	GroupsService groupsService;

	
	
	@RequestMapping(value={"/list"})
	public ModelAndView groupsList(){
		return new ModelAndView("groups/groupsList");
	}
	
	@RequestMapping(value={"/selectGroupsList"})
	public ModelAndView selectGroupsList(){
		return new ModelAndView("groups/selectGroupsList");
	}
	
	
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<Groups> queryDataGrid(@ModelAttribute("groups") Groups groups) {
		_logger.debug(""+groups);
		return groupsService.queryPageResults(groups);
	}

	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		return new ModelAndView("groups/groupAdd");
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("groups/groupUpdate");
		Groups group=groupsService.get(id);
		modelAndView.addObject("model",group);
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"})
	public Message insert(@ModelAttribute("group") Groups group) {
		_logger.debug("-Add  :" + group);
		
		if (groupsService.insert(group)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		
	}
	
	/**
	 * 查询
	 * @param group
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/query"}) 
	public Message query(@ModelAttribute("group") Groups group) {
		_logger.debug("-query  :" + group);
		if (groupsService.load(group)!=null) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_ERROR),MessageType.error);
		}
		
	}
	
	/**
	 * 修改
	 * @param group
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/update"})  
	public Message update(@ModelAttribute("group") Groups group) {
		_logger.debug("-update  group :" + group);
		
		if (groupsService.update(group)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("group") Groups group) {
		_logger.debug("-delete  group :" + group);
		
		if (groupsService.remove(group.getId())) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
		
	}
}
