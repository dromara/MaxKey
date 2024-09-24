/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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
 



package org.dromara.maxkey.web.permissions.contorller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.entity.permissions.RoleMember;
import org.dromara.maxkey.entity.permissions.Roles;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.RoleMemberService;
import org.dromara.maxkey.persistence.service.RolesService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.web.WebContext;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value={"/permissions/rolemembers"})
public class RoleMemberController {
	static final Logger _logger = LoggerFactory.getLogger(RoleMemberController.class);
	
	@Autowired
	RoleMemberService roleMemberService;

	@Autowired
	RolesService rolesService;
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	HistorySystemLogsService systemLog;
	
	@GetMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<JpaPageResults<RoleMember>> fetch(
			@ModelAttribute RoleMember roleMember,
			@CurrentUser UserInfo currentUser) {
		_logger.debug("fetch {}",roleMember);
		roleMember.setInstId(currentUser.getInstId());
		return new Message<>(roleMemberService.fetchPageResults(roleMember));
	}

	@RequestMapping(value = { "/memberInRole" })
	public Message<JpaPageResults<RoleMember>> memberInRole(@ModelAttribute RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		_logger.debug("roleMember : {}",roleMember);
		roleMember.setInstId(currentUser.getInstId());

		return new Message<>(roleMemberService.fetchPageResults("memberInRole",roleMember));
	}

	
	@RequestMapping(value = { "/memberNotInRole" })
	public Message<JpaPageResults<RoleMember>> memberNotInRole(@ModelAttribute  RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		roleMember.setInstId(currentUser.getInstId());
		return new Message<>(roleMemberService.fetchPageResults("memberNotInRole",roleMember));
	}
	
	@RequestMapping(value = { "/memberPostNotInRole" })
	public Message<JpaPageResults<RoleMember>> memberPostNotInRole(@ModelAttribute  RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		roleMember.setInstId(currentUser.getInstId());
		return new Message<>(roleMemberService.fetchPageResults("memberPostNotInRole",roleMember));
	}
	
	@RequestMapping(value = { "/rolesNoMember" })
	public Message<JpaPageResults<Roles>> rolesNoMember(@ModelAttribute  RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		roleMember.setInstId(currentUser.getInstId());
		return new Message<>(roleMemberService.rolesNoMember(roleMember));
	}
	
	/**
	 * Members add to the Role
	 * @param roleMember
	 * @param currentUser
	 * @return
	 */
	@PostMapping(value = {"/add"})
	@ResponseBody
	public Message<RoleMember> addRoleMember(@RequestBody RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		if (roleMember == null || roleMember.getRoleId() == null) {
			return new Message<>(Message.FAIL);
		}
		String roleId = roleMember.getRoleId();
		
		
		boolean result = true;
		String memberIds = roleMember.getMemberId();
		String memberNames = roleMember.getMemberName();
		if (memberIds != null) {
			String[] arrMemberIds = memberIds.split(",");
			String[] arrMemberNames = memberNames.split(",");
			
			for (int i = 0; i < arrMemberIds.length; i++) {
				RoleMember newRoleMember = 
						new RoleMember(
								roleId,
							roleMember.getRoleName(), 
							arrMemberIds[i], 
							arrMemberNames[i],
							roleMember.getType(),
							currentUser.getId(),
							currentUser.getInstId());
				newRoleMember.setId(WebContext.genId());
				result = roleMemberService.insert(newRoleMember);
			}
			if(result) {
				return new Message<>(Message.SUCCESS);
			}
		}
		return new Message<>(Message.FAIL);
	}
	
	
	/**
	 * Member add to Roles
	 * @param roleMember
	 * @param currentUser
	 * @return
	 */
	@RequestMapping(value = {"/addMember2Roles"})
	public Message<RoleMember> addMember2Roles(@RequestBody RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		if (roleMember == null || StringUtils.isBlank(roleMember.getUsername())) {
			return new Message<>(Message.FAIL);
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
							currentUser.getId(),
							currentUser.getInstId());
				newRoleMember.setId(WebContext.genId());
				result = roleMemberService.insert(newRoleMember);
			}
			if(result) {
				return new Message<>(Message.SUCCESS);
			}
		}
		return new Message<>(Message.FAIL);
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<RoleMember> delete(@RequestParam("ids") List<String> ids,@CurrentUser UserInfo currentUser) {
		_logger.debug("-delete ids : {}" , ids);
		if (roleMemberService.deleteBatch(ids)) {
			 return new Message<>(Message.SUCCESS);
		} else {
			return new Message<>(Message.FAIL);
		}
	}
}
