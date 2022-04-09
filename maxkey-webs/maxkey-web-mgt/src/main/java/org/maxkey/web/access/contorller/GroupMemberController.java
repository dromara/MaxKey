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
 

package org.maxkey.web.access.contorller;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.entity.GroupMember;
import org.maxkey.entity.Message;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.GroupMemberService;
import org.maxkey.persistence.service.GroupsService;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value={"/access/groupmembers"})
public class GroupMemberController {
	final static Logger _logger = LoggerFactory.getLogger(GroupMemberController.class);
	
	@Autowired
	@Qualifier("groupMemberService")
	GroupMemberService groupMemberService;

	@Autowired
	@Qualifier("groupsService")
	GroupsService groupsService;
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> fetch(
			@ModelAttribute GroupMember groupMember,
			@CurrentUser UserInfo currentUser) {
		_logger.debug("fetch "+groupMember);
		groupMember.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<GroupMember>>(
				groupMemberService.queryPageResults(groupMember)).buildResponse();
	}

	@RequestMapping(value = { "/memberInGroup" })
	@ResponseBody
	public ResponseEntity<?> memberInGroup(@ModelAttribute GroupMember groupMember,@CurrentUser UserInfo currentUser) {
		_logger.debug("groupMember : "+groupMember);
		groupMember.setInstId(currentUser.getInstId());
		if(groupMember.getGroupId()==null||groupMember.getGroupId().equals("")||groupMember.getGroupId().equals("ROLE_ALL_USER")){
			return new Message<JpaPageResults<GroupMember>>(
					groupMemberService.queryPageResults("allMemberInGroup",groupMember)).buildResponse();
		}else{
			return new Message<JpaPageResults<GroupMember>>(
					groupMemberService.queryPageResults("memberInGroup",groupMember)).buildResponse();
		}
	}

	
	@RequestMapping(value = { "/memberNotInGroup" })
	@ResponseBody
	public ResponseEntity<?> memberNotInGroup(@ModelAttribute  GroupMember groupMember,@CurrentUser UserInfo currentUser) {
		groupMember.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<GroupMember>>(
				groupMemberService.queryPageResults("memberNotInGroup",groupMember)).buildResponse();
	}
	
	
	@RequestMapping(value = {"/add"})
	@ResponseBody
	public ResponseEntity<?> addGroupMember(@RequestBody GroupMember groupMember,@CurrentUser UserInfo currentUser) {
		if (groupMember == null || groupMember.getGroupId() == null) {
			return new Message<GroupMember>(Message.FAIL).buildResponse();
		}
		String groupId = groupMember.getGroupId();
		
		
		boolean result = true;
		String memberIds = groupMember.getMemberId();
		String memberNames = groupMember.getMemberName();
		if (memberIds != null) {
			String[] arrMemberIds = memberIds.split(",");
			String[] arrMemberNames = memberNames.split(",");
			
			for (int i = 0; i < arrMemberIds.length; i++) {
				GroupMember newGroupMember = 
						new GroupMember(
							groupId,
							groupMember.getGroupName(), 
							arrMemberIds[i], 
							arrMemberNames[i],
							"USER",
							currentUser.getInstId());
				newGroupMember.setId(WebContext.genId());
				result = groupMemberService.insert(newGroupMember);
			}
			if(result) {
				return new Message<GroupMember>(Message.SUCCESS).buildResponse();
			}
		}
		return new Message<GroupMember>(Message.FAIL).buildResponse();
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> delete(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
		_logger.debug("-delete ids : {}" , ids);
		if (groupMemberService.deleteBatch(ids)) {
			 return new Message<GroupMember>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<GroupMember>(Message.FAIL).buildResponse();
		}
	}
}
