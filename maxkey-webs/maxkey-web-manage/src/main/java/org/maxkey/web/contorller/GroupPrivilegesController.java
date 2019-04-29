package org.maxkey.web.contorller;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.dao.service.GroupPrivilegesService;
import org.maxkey.domain.GroupPrivileges;
import org.maxkey.domain.apps.Applications;
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
	@Qualifier("groupAppService")
	GroupPrivilegesService groupAppService;

	
	@RequestMapping(value={"/list"})
	public ModelAndView groupsList(){
		return new ModelAndView("groupapp/groupAppsList");
	}
	
	@RequestMapping(value = { "/gridAppsInGroup" })
	@ResponseBody
	public JpaPageResults<Applications> queryAppsInGroupGrid(@ModelAttribute("groupApp") GroupPrivileges groupApp) {
		
		JpaPageResults<Applications> jqGridApp;
		jqGridApp= groupAppService.gridAppsInGroup(groupApp);

		if(jqGridApp!=null&&jqGridApp.getRows()!=null){
			for (Applications app : jqGridApp.getRows()){
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
	
	
	@RequestMapping(value = { "/appsNotInGroupGrid" })
	@ResponseBody
	public JpaPageResults<Applications> queryAppsNotInGroupGrid(@ModelAttribute("groupApp") GroupPrivileges groupApp) {

		JpaPageResults<Applications> jqGridApp;
		
		jqGridApp= groupAppService.gridAppsNotInGroupGrid(groupApp);

		if(jqGridApp!=null&&jqGridApp.getRows()!=null){
			for (Applications app : jqGridApp.getRows()){
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
				result = groupAppService.insert(newGroupApp);
			}
			if(!result) {
				return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_ERROR),MessageType.error);
			}
			
		}
		return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.info);
	}
	
	@RequestMapping(value = {"/delete"})
	@ResponseBody
	public Message deleteGroupApp(@ModelAttribute("groupApp") GroupPrivileges groupApp) {
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
				result = groupAppService.delete(newGroupApp);
			}
			if(!result) {
				return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_ERROR),MessageType.error);
			}
			
		}
		return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.info);
	}
	
	


}
