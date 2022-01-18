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
import org.maxkey.entity.Notices;
import org.maxkey.persistence.service.NoticesService;
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
@RequestMapping(value={"/notices"})
public class NoticesController {
	final static Logger _logger = LoggerFactory.getLogger(NoticesController.class);
	
	@Autowired
	@Qualifier("noticesService")
	NoticesService noticesService;

	@RequestMapping(value={"/list"})
	public ModelAndView noticesList(){
		return new ModelAndView("notices/noticesList");
	}

	
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<Notices> queryDataGrid(@ModelAttribute("notices") Notices notice) {
		_logger.debug(""+notice);
		notice.setInstId(WebContext.getUserInfo().getInstId());
		return noticesService.queryPageResults(notice);
	}

	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		return new ModelAndView("notices/noticeAdd");
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("notices/noticeUpdate");
		Notices notice=noticesService.get(id);
		modelAndView.addObject("model",notice);
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"})
	public Message insert(@ModelAttribute("notice")Notices notice) {
		_logger.debug("-Add  :" + notice);
		notice.setInstId(WebContext.getUserInfo().getInstId());
		if (noticesService.insert(notice)) {
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
	public Message query(@ModelAttribute("notice")Notices notice) {
		_logger.debug("-query  :" + notice);
		notice.setInstId(WebContext.getUserInfo().getInstId());
		if (noticesService.load(notice)!=null) {
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
	public Message update(@ModelAttribute("notice")Notices notice) {
		_logger.debug("-update  notice :" + notice);
		notice.setInstId(WebContext.getUserInfo().getInstId());
		if (noticesService.update(notice)) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("notice")Notices notice) {
		_logger.debug("-delete  notice :" + notice);
		
		if (noticesService.deleteBatch(notice.getId())) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
		
	}
}
