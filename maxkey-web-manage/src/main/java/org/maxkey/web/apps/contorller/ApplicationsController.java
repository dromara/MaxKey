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
import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.ExtraAttr;
import org.maxkey.domain.ExtraAttrs;
import org.maxkey.domain.apps.Apps;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/apps"})
public class ApplicationsController extends BaseAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(ApplicationsController.class);
	
	@RequestMapping(value={"/list"})
	public ModelAndView applicationsList(){
		return new ModelAndView("apps/appsList");
	}
	
	@RequestMapping(value={"/select"})
	public ModelAndView select(){
		return new ModelAndView("apps/selectAppsList");
	}
	
	
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<Apps> queryDataGrid(@ModelAttribute("applications") Apps applications) {
		JpaPageResults<Apps> jqGridApp=appsService.queryPageResults(applications);
		if(jqGridApp!=null&&jqGridApp.getRows()!=null){
			for (Apps app : jqGridApp.getRows()){
				WebContext.setAttribute(app.getId(), app.getIcon());
			}
		}
		return jqGridApp;
	}
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		return new ModelAndView("apps/appAdd");
	}
	
	
	@ResponseBody
	@RequestMapping(value={"/add"})
	public Message insert(@ModelAttribute("application") Apps application) {
		_logger.debug("-Add  :" + application);
		
		transform(application);
		
		if (appsService.insert(application)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		
	}
	
	@RequestMapping(value = { "/forwardAppsExtendAttr/{id}" })
	public ModelAndView forwardExtendAttr(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/appsExtendAttr");
		modelAndView.addObject("model",appsService.get(id));
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/updateExtendAttr" })
	public Message updateExtendAttr(@ModelAttribute("application") Apps application,@ModelAttribute("extraAttrs") ExtraAttr extraAttr) {
		if(extraAttr.getAttr()!=null){
			String []attributes=extraAttr.getAttr().split(",");
			String []attributeType=extraAttr.getType().split(",");
			String []attributeValue=extraAttr.getValue().split(",");
			ExtraAttrs extraAttrs=new ExtraAttrs();
			for(int i=0;i<attributes.length;i++){
				extraAttrs.put(attributes[i],attributeType[i], attributeValue[i]);
			}
			application.setExtendAttr(extraAttrs.toJsonString());
		}
		
		if (appsService.updateExtendAttr(application)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_ERROR),MessageType.error);
		}
	}
	
	/**
	 * query
	 * @param application
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/query"}) 
	public Message query(@ModelAttribute("application") Apps application) {
		_logger.debug("-query  :" + application);
		if (appsService.load(application)!=null) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_ERROR),MessageType.error);
		}
		
	}
	
	/**
	 * modify
	 * @param application
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/update"})  
	public Message update(@ModelAttribute("application") Apps application) {
		_logger.debug("-update  application :" + application);
		if (appsService.update(application)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("application") Apps application) {
		_logger.debug("-delete  application :" + application);
		if (appsService.delete(application)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value = { "/generate/secret/{type}" })
	public String generateSecret(@PathVariable("type") String type) {
		String secret="";
		type=type.toLowerCase();
		if(type.equals("des")){
			secret=ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.DES);
		}else if(type.equals("desede")){
			secret=ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.DESede);
		}else if(type.equals("aes")){
			secret=ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.AES);
		}else if(type.equals("blowfish")){
			secret=ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.Blowfish);
		}else{
			secret=ReciprocalUtils.generateKey("");
		}
		
		return secret;
	}
	
	
}
