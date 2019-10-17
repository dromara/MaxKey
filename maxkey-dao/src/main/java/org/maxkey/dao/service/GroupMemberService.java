package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.dao.persistence.GroupMemberMapper;
import org.maxkey.domain.GroupMember;
import org.maxkey.domain.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class GroupMemberService  extends JpaBaseService<GroupMember>{
	
	public GroupMemberService() {
		super(GroupMemberMapper.class);
	}
	
	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public GroupMemberMapper getMapper() {
		// TODO Auto-generated method stub
		return (GroupMemberMapper)super.getMapper();
	}
}
