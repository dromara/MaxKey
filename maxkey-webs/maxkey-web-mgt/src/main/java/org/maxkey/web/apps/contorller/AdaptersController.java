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

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.ConstsOperateMessage;
import org.maxkey.entity.apps.AppsAdapters;
import org.maxkey.persistence.service.AppsAdaptersService;
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
@RequestMapping(value={"/apps/adapters"})
public class AdaptersController {
	final static Logger _logger = LoggerFactory.getLogger(AdaptersController.class);
	
	@Autowired
	@Qualifier("appsAdaptersService")
	AppsAdaptersService appsAdaptersService;

	
	
	@RequestMapping(value={"/list"})
	public ModelAndView rolesList(){
		return new ModelAndView("apps/adapters/adaptersList");
	}
	
	@RequestMapping(value={"/selectAdaptersList"})
	public ModelAndView selectAdaptersList(@RequestParam(name="protocol",required=false) String protocol){
		ModelAndView modelAndView=new ModelAndView("apps/adapters/selectAdaptersList");
		modelAndView.addObject("protocol", protocol);
		return modelAndView;
	}
	
	
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<AppsAdapters> queryDataGrid(@ModelAttribute("appsAdapter") AppsAdapters appsAdapter) {
		_logger.debug(""+appsAdapter);
		return appsAdaptersService.queryPageResults(appsAdapter);
	}

	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		return new ModelAndView("apps/adapters/adapterAdd");
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/adapters/adapterUpdate");
		AppsAdapters appsAdapter=appsAdaptersService.get(id);
		modelAndView.addObject("model",appsAdapter);
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"})
	public Message insert(@ModelAttribute("appsAdapter") AppsAdapters appsAdapter) {
		_logger.debug("-Add  :" + appsAdapter);
		
		if (appsAdaptersService.insert(appsAdapter)) {
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
	public Message query(@ModelAttribute("appsAdapter") AppsAdapters appsAdapter) {
		_logger.debug("-query  :" + appsAdapter);
		if (appsAdaptersService.load(appsAdapter)!=null) {
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
	public Message update(@ModelAttribute("appsAdapter") AppsAdapters appsAdapter) {
		_logger.debug("-update  appsAdapter :" + appsAdapter);
		
		if (appsAdaptersService.update(appsAdapter)) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("appsAdapter") AppsAdapters appsAdapter) {
		_logger.debug("-delete  appsAdapter :" + appsAdapter);
		
		if (appsAdaptersService.deleteBatch(appsAdapter.getId())) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
		
	}
}
