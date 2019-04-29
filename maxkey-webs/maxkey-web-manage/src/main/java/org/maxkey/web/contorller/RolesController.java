package org.maxkey.web.contorller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.dao.service.NavigationsService;
import org.maxkey.dao.service.RolesService;
import org.maxkey.domain.Roles;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统角色操作管理
 * @author Crystal.Sea
 *
 */
@Controller
@RequestMapping(value = { "/roles" })
public class RolesController {
	final static Logger _logger = LoggerFactory.getLogger(RolesController.class);
	
	@Autowired
	@Qualifier("rolesService")
	RolesService rolesService;

	@Autowired
	@Qualifier("navigationsService")
	NavigationsService navigationsService;
	
	

	@RequestMapping(value = { "/list" })
	public ModelAndView usersList() {
		return new ModelAndView("roles/rolesList");
	}
	
	@RequestMapping(value = { "/selectRolesList" })
	public ModelAndView selectRolesList() {
		return new ModelAndView("roles/selectRolesList");
	}
	
	/**
	 * 获取角色列表
	 * @param role
	 * @return
	 */
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<Roles> queryDataGrid(@ModelAttribute("role") Roles role) {
		_logger.debug("role : "+role);
		role.setStatus(1);
		return rolesService.queryPageResults(role);
	}
	
	

	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		return new ModelAndView("roles/roleAdd");
	}
	
	/**
	 * 新增
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Message addRole(@ModelAttribute("role") Roles role) {
		role.setId(role.generateId());
		_logger.debug("addRole roleUser : "+role);
		if(rolesService.insert(role)){
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.success);
		}else{
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_ERROR),MessageType.error);
		}
	}
	
	@RequestMapping(value = { "/forwardUpdate/{roleId}" })
	public ModelAndView forwardUpdate(@PathVariable("roleId") String roleId) {
		Roles role=rolesService.get(roleId);
		return new ModelAndView("roles/roleUpdate","role",role);
	}

	/**
	 * 修改
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.PUT)
	@ResponseBody
	public Message updateRole(@ModelAttribute("role") Roles role) {
		_logger.debug("updateRole roleUser : "+role);
		if(rolesService.update(role)){
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
		}else{
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
		}
	}
	
	
	/**
	 * 删除
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = { "/delete" })
	public Message deleteRole(@RequestParam("id") String id) {
		_logger.debug("id	:"	+id);
		if(rolesService.batchDelete(StringUtils.string2List(id, ","))){
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.success);
		}else{
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_ERROR),MessageType.error);
		}
	}
	
	
	  @InitBinder  
	  public void initBinder(WebDataBinder binder) {  
	      binder.setIgnoreInvalidFields(true);  

	      DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	      binder.registerCustomEditor(Date.class, "createdate",  
	              new CustomDateEditor(format, true));  
	  } 
	  
	 
}
