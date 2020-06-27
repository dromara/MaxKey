package org.maxkey.persistence.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.domain.GroupMember;
import org.maxkey.persistence.mapper.GroupMemberMapper;
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
