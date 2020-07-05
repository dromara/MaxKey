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
import org.maxkey.domain.GroupPrivileges;
import org.maxkey.domain.apps.Apps;
import org.maxkey.persistence.service.GroupPrivilegesService;
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
@RequestMapping(value={"/groupPrivileges"})
public class GroupPrivilegesController {
	final static Logger _logger = LoggerFactory.getLogger(GroupPrivilegesController.class);
	
	@Autowired
	@Qualifier("groupPrivilegesService")
	GroupPrivilegesService groupPrivilegesService;

	
	@RequestMapping(value={"/list"})
	public ModelAndView groupsList(){
		return new ModelAndView("groupapp/groupAppsList");
	}
	
	@RequestMapping(value = { "/queryAppsInGroup" })
	@ResponseBody
	public JpaPageResults<GroupPrivileges> queryAppsInGroup(@ModelAttribute("groupApp") GroupPrivileges groupApp) {
		
		JpaPageResults<GroupPrivileges> jqGridApp;
		
		jqGridApp= groupPrivilegesService.queryPageResults("appsInGroup",groupApp);

		if(jqGridApp!=null&&jqGridApp.getRows()!=null){
			for (Apps app : jqGridApp.getRows()){
				WebContext.setAttribute(app.getId(), app.getIcon());
			}
		}
		return jqGridApp;

	}
	
	@RequestMapping(value={"/addGroupAppsList/{groupId}"})
	public ModelAndView appsNotInGroupList(@PathVariable("groupId") String groupId){
		ModelAndView modelAndView=new ModelAndView("groupapp/addGroupAppsList");
		modelAndView.addObject("groupId", groupId);
		return modelAndView;
	}
	
	
	@RequestMapping(value = { "/queryAppsNotInGroup" })
	@ResponseBody
	public JpaPageResults<GroupPrivileges> queryAppsNotInGroup(@ModelAttribute("groupApp") GroupPrivileges groupApp) {
		JpaPageResults<GroupPrivileges> jqGridApp;
		
		jqGridApp= groupPrivilegesService.queryPageResults("appsNotInGroup",groupApp);

		if(jqGridApp!=null&&jqGridApp.getRows()!=null){
			for (Apps app : jqGridApp.getRows()){
				WebContext.setAttribute(app.getId(), app.getIcon());
			}
		}
		return jqGridApp;

	}

	
	@RequestMapping(value = {"/insert"})
	@ResponseBody
	public Message insertGroupApp(@ModelAttribute("groupApp") GroupPrivileges groupApp) {
		if (groupApp == null || groupApp.getGroupId() == null) {
			return  new Message("传入参数为空",MessageType.error);
		}
		String groupId = groupApp.getGroupId();
		
		
		boolean result = true;
		String appIds = groupApp.getAppId();
		if (appIds != null) {
			String[] arrAppIds = appIds.split(",");
			
			for (int i = 0; i < arrAppIds.length; i++) {
				GroupPrivileges newGroupApp = new GroupPrivileges(groupId, arrAppIds[i]);
				newGroupApp.setId(newGroupApp.generateId());
				result = groupPrivilegesService.insert(newGroupApp);
			}
			if(!result) {
				return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_ERROR),MessageType.error);
			}
			
		}
		return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.info);
	}
	
	@RequestMapping(value = {"/delete"})
	@ResponseBody
	public Message deleteGroupApp(@ModelAttribute("groupApp") GroupPrivileges groupApp) {
		if (groupApp == null || groupApp.getId() == null) {
			return  new Message("传入参数为空",MessageType.error);
		}
		String privilegesIds = groupApp.getId();
		
		
		boolean result = true;
		if (privilegesIds != null) {
			String[] arrPrivilegesIds = privilegesIds.split(",");
			
			for (int i = 0; i < arrPrivilegesIds.length; i++) {
				result = groupPrivilegesService.remove(arrPrivilegesIds[i]);
			}
			if(!result) {
				return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_ERROR),MessageType.error);
			}
			
		}
		return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.info);
	}
	
	


}
