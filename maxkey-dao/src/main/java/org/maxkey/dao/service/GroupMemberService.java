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
	
	public JpaPageResults<UserInfo> gridMemberInGroup(GroupMember entity) {
		Integer totalCount = parseCount(getMapper().countUserMemberInGroup(entity));
		if(totalCount == 0) {
			return new JpaPageResults<UserInfo>();
		}
		
		int totalPage = calculateTotalPage(entity,totalCount);
		
		if(totalPage == 0) {
			return new JpaPageResults<UserInfo>();
		}
		
		if(totalPage < entity.getPage()) {
			entity.setPage(totalPage);
			entity.setStartRow(calculateStartRow(totalPage ,entity.getPageResults()));
		}
		entity.setPageable(true);
		return new JpaPageResults<UserInfo>(entity.getPage(),entity.getPageResults(),totalCount,getMapper().gridUserMemberInGroup(entity));
	}
	
	public JpaPageResults<UserInfo> gridAllMemberInGroup(GroupMember entity) {
		Integer totalCount = parseCount(getMapper().countAllUserMemberInGroup(entity));
		if(totalCount == 0) {
			return new JpaPageResults<UserInfo>();
		}
		
		int totalPage = calculateTotalPage(entity,totalCount);
		
		if(totalPage == 0) {
			return new JpaPageResults<UserInfo>();
		}
		
		if(totalPage < entity.getPage()) {
			entity.setPage(totalPage);
			entity.setStartRow(calculateStartRow(totalPage ,entity.getPageResults()));
		}
		entity.setPageable(true);
		return new JpaPageResults<UserInfo>(entity.getPage(),entity.getPageResults(),totalCount,getMapper().gridAllUserMemberInGroup(entity));
	}
	
	public JpaPageResults<UserInfo> gridMemberNotInGroup(GroupMember entity) {
		Integer totalCount = parseCount(getMapper().countUserMemberNotInGroup(entity));
		if(totalCount == 0) {
			return new JpaPageResults<UserInfo>();
		}
		
		int totalPage = calculateTotalPage(entity,totalCount);
		
		if(totalPage == 0) {
			return new JpaPageResults<UserInfo>();
		}
		
		if(totalPage < entity.getPage()) {
			entity.setPage(totalPage);
			entity.setStartRow(calculateStartRow(totalPage ,entity.getPageResults()));
		}
		entity.setPageable(true);
		return new JpaPageResults<UserInfo>(entity.getPage(),entity.getPageResults(),totalCount,getMapper().gridUserMemberNotInGroup(entity));
	}
}
