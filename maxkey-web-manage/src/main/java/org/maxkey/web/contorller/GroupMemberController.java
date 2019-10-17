package org.maxkey.web.contorller;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.dao.service.GroupMemberService;
import org.maxkey.dao.service.GroupsService;
import org.maxkey.domain.GroupMember;
import org.maxkey.domain.Groups;
import org.maxkey.domain.UserInfo;
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
@RequestMapping(value={"/groupMember"})
public class GroupMemberController {
	final static Logger _logger = LoggerFactory.getLogger(GroupMemberController.class);
	
	@Autowired
	@Qualifier("groupMemberService")
	GroupMemberService groupMemberService;

	@Autowired
	@Qualifier("groupsService")
	GroupsService groupsService;
	
	
	
	@RequestMapping(value={"/list"})
	public ModelAndView groupsList(){
		return new ModelAndView("groupuser/groupUsersList");
	}
	
	
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<GroupMember> grid(@ModelAttribute("groupMember") GroupMember groupMember) {
		if(groupMember.getGroupId()==null||groupMember.getGroupId().equals("")){
			return null;
		}
		return groupMemberService.queryPageResults(groupMember);
	}
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		return new ModelAndView("groups/groupAdd");
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("groups/groupUpdate");
		GroupMember groupMember=groupMemberService.get(id);
		modelAndView.addObject("model",groupMember);
		return modelAndView;
	}
	
	
	@RequestMapping(value = { "/queryMemberInGroup" })
	@ResponseBody
	public JpaPageResults<GroupMember> queryMemberInGroup(@ModelAttribute("groupMember")  GroupMember groupMember) {
		_logger.debug("groupMember : "+groupMember);
		if(groupMember.getGroupId()==null||groupMember.getGroupId().equals("")||groupMember.getGroupId().equals("ALL_USER_GROUP")){
			return groupMemberService.queryPageResults("allMemberInGroup",groupMember);
		}else{
			return groupMemberService.queryPageResults("memberInGroup",groupMember);
		}
	}
	
	
	@RequestMapping(value={"/addGroupAppsList/{groupId}"})
	public ModelAndView addGroupAppsList(@PathVariable("groupId") String groupId){
		ModelAndView modelAndView=new ModelAndView("groupuser/addGroupUsersList");
		Groups group=groupsService.get(groupId);
		modelAndView.addObject("group", group);
		return modelAndView;
	}
	
	@RequestMapping(value = { "/queryMemberNotInGroup" })
	@ResponseBody
	public JpaPageResults<GroupMember> queryMemberNotInGroupGrid(@ModelAttribute("groupMember")  GroupMember groupMember) {
			return groupMemberService.queryPageResults("memberNotInGroup",groupMember);
	}
	
	
	@RequestMapping(value = {"/insert"})
	@ResponseBody
	public Message insertGroupUser(@ModelAttribute("groupMember") GroupMember groupMember) {
		if (groupMember == null || groupMember.getGroupId() == null) {
			return  new Message("传入参数为空",MessageType.error);
		}
		String groupId = groupMember.getGroupId();
		
		
		boolean result = true;
		String memberIds = groupMember.getMemberId();
		String memberNames = groupMember.getMemberName();
		if (memberIds != null) {
			String[] arrMemberIds = memberIds.split(",");
			String[] arrMemberNames = memberNames.split(",");
			
			for (int i = 0; i < arrMemberIds.length; i++) {
				GroupMember newGroupMember = new GroupMember(groupId,groupMember.getGroupName(), arrMemberIds[i], arrMemberNames[i],"USER");
				newGroupMember.setId(newGroupMember.generateId());
				result = groupMemberService.insert(newGroupMember);
			}
			if(!result) {
				return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_ERROR),MessageType.error);
			}
			
		}
		return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.info);
	}
	
	@RequestMapping(value = {"/delete"})
	@ResponseBody
	public Message deleteGroupMember(@ModelAttribute("groupMember") GroupMember groupMember) {
		_logger.debug("groupMember : "+groupMember);
		
		if (groupMember == null || groupMember.getId() == null) {
			return  new Message("传入参数为空",MessageType.error);
		}
		boolean result = true;
		String groupMemberIds = groupMember.getId();
		if (groupMemberIds != null) {
			String[] arrMemberIds = groupMemberIds.split(",");
			for (int i = 0; i < arrMemberIds.length; i++) {
				groupMemberService.remove(arrMemberIds[i]);
			}
			if(!result) {
				return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_ERROR),MessageType.error);
			}
			
		}
		return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.info);
	}
}
