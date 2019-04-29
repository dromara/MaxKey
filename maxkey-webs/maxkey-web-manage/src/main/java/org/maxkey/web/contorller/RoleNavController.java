package org.maxkey.web.contorller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.dao.service.NavigationsService;
import org.maxkey.dao.service.RoleNavService;
import org.maxkey.domain.Navigations;
import org.maxkey.domain.RoleNav;
import org.maxkey.domain.Roles;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统角色操作管理
 * @author Crystal.Sea
 *
 */
@Controller
@RequestMapping(value = { "/roleNav" })
public class RoleNavController {
	final static Logger _logger = LoggerFactory.getLogger(RoleNavController.class);
	
	@Autowired
	@Qualifier("roleNavService")
	RoleNavService roleNavService;

	@Autowired
	@Qualifier("navigationsService")
	NavigationsService navigationsService;
	
	

	@RequestMapping(value = { "/rolesList" })
	public ModelAndView usersList() {
		return new ModelAndView("roles/rolesList");
	}


	

	
	@RequestMapping("/roleNavList")
	public ModelAndView rolemenuList() {
		ModelAndView mv = new ModelAndView();
		List<Navigations> navigationsList = navigationsService.query(new Navigations());
		mv.addObject("navigationsList", navigationsList);
		mv.setViewName("rolenav/roleNavList");

		return mv;
	}

	/**
	 * 根据角色获取菜单列表
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/queryNavs/{roleId}")
	@ResponseBody
	public List<Navigations> queryNavs(@PathVariable("roleId") String roleId) {
		List<Navigations> navs = roleNavService.queryNavs(roleId); 
		return navs;
	}
	


	/**
	 * 增加角色菜单关联关系
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "insert")
	@ResponseBody
	public Message insertRoleNav(@ModelAttribute("role") Roles role) {
		if (role == null || role.getId() == null) {
			return  new Message("传入参数为空",MessageType.error);
		}
		String roleId = role.getId();
		
		roleNavService.delete(roleId);
		
		boolean result = true;
		String munusId = role.getNavsId();
		if (munusId != null) {
			String[] arrMenusId = munusId.split(",");
			List<RoleNav> listRoleNav=new ArrayList<RoleNav>();
			for (int i = 0; i < arrMenusId.length; i++) {
				RoleNav roleMenu = new RoleNav(roleId, arrMenusId[i]);
				roleMenu.setId(roleMenu.generateId());
				listRoleNav.add(roleMenu);
			}
			result = roleNavService.insert(listRoleNav);
			if(!result) {
				return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_ERROR),MessageType.error);
			}
			
		}
		return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.info);
	}
	


	  @InitBinder  
	  public void initBinder(WebDataBinder binder) {  
	      binder.setIgnoreInvalidFields(true);  

	      DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	      binder.registerCustomEditor(Date.class, "createdate",  
	              new CustomDateEditor(format, true));  
	  } 
	  
	
}
