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
import org.maxkey.domain.RoleMember;
import org.maxkey.domain.Roles;
import org.maxkey.persistence.service.RoleMemberService;
import org.maxkey.persistence.service.RolesService;
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
@RequestMapping(value={"/rolemembers"})
public class RoleMemberController {
	final static Logger _logger = LoggerFactory.getLogger(RoleMemberController.class);
	
	@Autowired
	@Qualifier("roleMemberService")
	RoleMemberService roleMemberService;

	@Autowired
	@Qualifier("rolesService")
	RolesService rolesService;
	
	
	
	@RequestMapping(value={"/list"})
	public ModelAndView groupsList(){
		return new ModelAndView("roleusers/roleUsersList");
	}
	
	
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<RoleMember> grid(@ModelAttribute("roleMember") RoleMember roleMember) {
		if(roleMember.getRoleId()==null||roleMember.getRoleId().equals("")){
			return null;
		}
		return roleMemberService.queryPageResults(roleMember);
	}
	
	
	@RequestMapping(value = { "/queryMemberInRole" })
	@ResponseBody
	public JpaPageResults<RoleMember> queryMemberInRole(@ModelAttribute("roleMember")  RoleMember roleMember) {
		_logger.debug("roleMember : "+roleMember);
		if(roleMember.getRoleId()==null||roleMember.getRoleId().equals("")||roleMember.getRoleId().equals("ALL_USER_ROLE")){
			return roleMemberService.queryPageResults("allMemberInRole",roleMember);
		}else{
			return roleMemberService.queryPageResults("memberInRole",roleMember);
		}
	}
	
	
	@RequestMapping(value={"/addRoleAppsList/{roleId}"})
	public ModelAndView addGroupAppsList(@PathVariable("roleId") String roleId){
		ModelAndView modelAndView=new ModelAndView("roleusers/addRoleUsersList");
		Roles role=rolesService.get(roleId);
		modelAndView.addObject("role", role);
		return modelAndView;
	}
	
	@RequestMapping(value = { "/queryMemberNotInRole" })
	@ResponseBody
	public JpaPageResults<RoleMember> queryMemberNotInGroupGrid(@ModelAttribute("roleMember")  RoleMember roleMember) {
			return roleMemberService.queryPageResults("memberNotInRole",roleMember);
	}
	
	
	@RequestMapping(value = {"/insert"})
	@ResponseBody
	public Message insertRoleUsers(@ModelAttribute("roleMember")  RoleMember roleMember) {
		if (roleMember == null || roleMember.getRoleId() == null) {
			return  new Message("传入参数为空",MessageType.error);
		}
		String groupId = roleMember.getRoleId();
		
		
		boolean result = true;
		String memberIds = roleMember.getMemberId();
		String memberNames = roleMember.getMemberName();
		if (memberIds != null) {
			String[] arrMemberIds = memberIds.split(",");
			String[] arrMemberNames = memberNames.split(",");
			
			for (int i = 0; i < arrMemberIds.length; i++) {
				RoleMember newRoleMember = new RoleMember(groupId,roleMember.getRoleName(), arrMemberIds[i], arrMemberNames[i],"USER");
				newRoleMember.setId(newRoleMember.generateId());
				result = roleMemberService.insert(newRoleMember);
			}
			if(!result) {
				return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_ERROR),MessageType.error);
			}
			
		}
		return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.info);
	}
	
	@RequestMapping(value = {"/delete"})
	@ResponseBody
	public Message deleteGroupMember(@ModelAttribute("roleMember")  RoleMember roleMember) {
		_logger.debug("roleMember : "+roleMember);
		
		if (roleMember == null || roleMember.getId() == null) {
			return  new Message("传入参数为空",MessageType.error);
		}
		boolean result = true;
		String roleMemberIds = roleMember.getId();
		if (roleMemberIds != null) {
			String[] arrMemberIds = roleMemberIds.split(",");
			for (int i = 0; i < arrMemberIds.length; i++) {
				roleMemberService.remove(arrMemberIds[i]);
			}
			if(!result) {
				return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_ERROR),MessageType.error);
			}
			
		}
		return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.info);
	}
}
