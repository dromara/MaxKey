/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.maxkey.web.access.contorller;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.entity.RoleMember;
import org.maxkey.entity.Roles;
import org.maxkey.entity.Message;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.RoleMemberService;
import org.maxkey.persistence.service.RolesService;
import org.maxkey.persistence.service.HistorySystemLogsService;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value={"/access/rolemembers"})
public class RoleMemberController {
	final static Logger _logger = LoggerFactory.getLogger(RoleMemberController.class);
	
	@Autowired
	RoleMemberService roleMemberService;

	@Autowired
	RolesService rolesService;
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	HistorySystemLogsService systemLog;
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> fetch(
			@ModelAttribute RoleMember roleMember,
			@CurrentUser UserInfo currentUser) {
		_logger.debug("fetch "+roleMember);
		roleMember.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<RoleMember>>(
				roleMemberService.queryPageResults(roleMember)).buildResponse();
	}

	@RequestMapping(value = { "/memberInRole" })
	@ResponseBody
	public ResponseEntity<?> memberInRole(@ModelAttribute RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		_logger.debug("roleMember : "+roleMember);
		roleMember.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<RoleMember>>(
				roleMemberService.queryPageResults("memberInRole",roleMember)).buildResponse();

	}

	
	@RequestMapping(value = { "/memberNotInRole" })
	@ResponseBody
	public ResponseEntity<?> memberNotInRole(@ModelAttribute  RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		roleMember.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<RoleMember>>(
				roleMemberService.queryPageResults("memberNotInRole",roleMember)).buildResponse();
	}

	@RequestMapping(value = { "/rolesNoMember" })
	@ResponseBody
	public ResponseEntity<?> rolesNoMember(@ModelAttribute  RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		roleMember.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<Roles>>(
				roleMemberService.rolesNoMember(roleMember)).buildResponse();
	}
	
	/**
	 * Members add to the Role
	 * @param roleMember
	 * @param currentUser
	 * @return
	 */
	@RequestMapping(value = {"/add"})
	@ResponseBody
	public ResponseEntity<?> addRoleMember(@RequestBody RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		if (roleMember == null || roleMember.getRoleId() == null) {
			return new Message<RoleMember>(Message.FAIL).buildResponse();
		}
		String roleId = roleMember.getRoleId();
		
		
		boolean result = true;
		String memberIds = roleMember.getMemberId();
		String memberNames = roleMember.getMemberName();
		if (memberIds != null) {
			String[] arrMemberIds = memberIds.split(",");
			String[] arrMemberNames = memberNames.split(",");
			//set default as USER
			if(StringUtils.isBlank(roleMember.getType())) {
				roleMember.setType("USER");
			}
			for (int i = 0; i < arrMemberIds.length; i++) {
				RoleMember newRoleMember = 
						new RoleMember(
								roleId,
							roleMember.getRoleName(), 
							arrMemberIds[i], 
							arrMemberNames[i],
							roleMember.getType(),
							currentUser.getInstId());
				newRoleMember.setId(WebContext.genId());
				result = roleMemberService.insert(newRoleMember);
			}
			if(result) {
				return new Message<RoleMember>(Message.SUCCESS).buildResponse();
			}
		}
		return new Message<RoleMember>(Message.FAIL).buildResponse();
	}
	
	
	/**
	 * Member add to Roles
	 * @param roleMember
	 * @param currentUser
	 * @return
	 */
	@RequestMapping(value = {"/addMember2Roles"})
	@ResponseBody
	public ResponseEntity<?> addMember2Roles(@RequestBody RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		if (roleMember == null || StringUtils.isBlank(roleMember.getUsername())) {
			return new Message<RoleMember>(Message.FAIL).buildResponse();
		}
		UserInfo userInfo = userInfoService.findByUsername(roleMember.getUsername());
		
		boolean result = true;
		String roleIds = roleMember.getRoleId();
		String roleNames = roleMember.getRoleName();
		if (roleIds != null && userInfo != null) {
			String[] arrRoleIds = roleIds.split(",");
			String[] arrRoleNames = roleNames.split(",");
			
			for (int i = 0; i < arrRoleIds.length; i++) {
				RoleMember newRoleMember = 
						new RoleMember(
							arrRoleIds[i],
							arrRoleNames[i], 
							userInfo.getId(), 
							userInfo.getDisplayName(),
							"USER",
							currentUser.getInstId());
				newRoleMember.setId(WebContext.genId());
				result = roleMemberService.insert(newRoleMember);
			}
			if(result) {
				return new Message<RoleMember>(Message.SUCCESS).buildResponse();
			}
		}
		return new Message<RoleMember>(Message.FAIL).buildResponse();
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> delete(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
		_logger.debug("-delete ids : {}" , ids);
		if (roleMemberService.deleteBatch(ids)) {
			 return new Message<RoleMember>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<RoleMember>(Message.FAIL).buildResponse();
		}
	}
}
