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
 

package org.maxkey.web.permissions.contorller;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.entity.Message;
import org.maxkey.entity.RoleMember;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.RoleMemberService;
import org.maxkey.persistence.service.RolesService;
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
@RequestMapping(value={"/permissions/rolemembers"})
public class RoleMemberController {
	final static Logger _logger = LoggerFactory.getLogger(RoleMemberController.class);
	
	@Autowired
	RoleMemberService roleMemberService;

	@Autowired
	RolesService rolesService;
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> fetch(
			@ModelAttribute("roleMember") RoleMember roleMember,
			@CurrentUser UserInfo currentUser) {
		if(roleMember.getRoleId()==null||roleMember.getRoleId().equals("")){
			return null;
		}
		roleMember.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<RoleMember>>(
				roleMemberService.queryPageResults(roleMember)).buildResponse();
	}
	
	@RequestMapping(value = { "/memberInRole" })
	@ResponseBody
	public  ResponseEntity<?> memberInRole(@ModelAttribute  RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		_logger.debug("roleMember : "+roleMember);
		roleMember.setInstId(currentUser.getInstId());
		if(roleMember.getRoleId()==null||roleMember.getRoleId().equals("")||roleMember.getRoleId().equals("ALL_USER_ROLE")){
			return new Message<JpaPageResults<RoleMember>>(
					roleMemberService.queryPageResults("allMemberInRole",roleMember)).buildResponse();
		}else{
			return new Message<JpaPageResults<RoleMember>>(
					roleMemberService.queryPageResults("memberInRole",roleMember)).buildResponse();
		}
	}

	@RequestMapping(value = { "/memberNotInRole" })
	@ResponseBody
	public ResponseEntity<?> memberNotInRole(@ModelAttribute  RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		roleMember.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<RoleMember>>(
				roleMemberService.queryPageResults("memberNotInRole",roleMember)).buildResponse();
	}
	
	@RequestMapping(value = {"/add"})
	@ResponseBody
	public ResponseEntity<?> add(@RequestBody RoleMember roleMember,@CurrentUser UserInfo currentUser) {
		if (roleMember == null || roleMember.getRoleId() == null) {
			return new Message<RoleMember>(Message.FAIL).buildResponse();
		}
		String groupId = roleMember.getRoleId();
		
		boolean result = true;
		String memberIds = roleMember.getMemberId();
		String memberNames = roleMember.getMemberName();
		if (memberIds != null) {
			String[] arrMemberIds = memberIds.split(",");
			String[] arrMemberNames = memberNames.split(",");
			
			for (int i = 0; i < arrMemberIds.length; i++) {
				RoleMember newRoleMember = 
						new RoleMember(
								groupId,
								roleMember.getRoleName(), 
								arrMemberIds[i], 
								arrMemberNames[i],
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
