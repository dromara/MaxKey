package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.PLATFORMROLE;
import org.maxkey.dao.persistence.RoleUserMapper;
import org.maxkey.domain.RoleUser;
import org.maxkey.domain.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class RoleUserService extends JpaBaseService<RoleUser> {

	public RoleUserService() {
		super(RoleUserMapper.class);

	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public RoleUserMapper getMapper() {
		// TODO Auto-generated method stub
		return (RoleUserMapper)super.getMapper();
	}

	
	public boolean insertTenantAdmin(String uid) {
		RoleUser roleUser =new RoleUser(PLATFORMROLE.TANANT_ADMIN,uid);
		roleUser.setId(roleUser.generateId());
		return insert(roleUser);
	}
	
	
	
	public boolean deleteTenantAdmin(String uid) {
		RoleUser roleUser =new RoleUser(PLATFORMROLE.TANANT_ADMIN,uid);
		return  delete(roleUser);
	}
	

	public JpaPageResults<UserInfo> gridAllUserInfoInRole(RoleUser roleUser) {
		Integer totalCount = parseCount(getMapper().countAllUserInfoInRole(roleUser));
		if(totalCount == 0) {
			return new JpaPageResults<UserInfo>();
		}
		
		int totalPage = calculateTotalPage(roleUser,totalCount);
		
		if(totalPage == 0) {
			return new JpaPageResults<UserInfo>();
		}
		
		if(totalPage < roleUser.getPage()) {
			roleUser.setPage(totalPage);
			roleUser.setStartRow(calculateStartRow(totalPage,roleUser.getPageResults()));
		}
		return new JpaPageResults<UserInfo>(roleUser.getPage(),roleUser.getPageResults(),totalCount,getMapper().gridAllUserInfoInRole(roleUser));
	}
	
	public JpaPageResults<UserInfo> gridUserInfoInRole(RoleUser roleUser) {
		Integer totalCount = parseCount(getMapper().countUserInfoInRole(roleUser));
		if(totalCount == 0) {
			return new JpaPageResults<UserInfo>();
		}
		
		int totalPage = calculateTotalPage(roleUser,totalCount);
		
		if(totalPage == 0) {
			return new JpaPageResults<UserInfo>();
		}
		
		if(totalPage < roleUser.getPage()) {
			roleUser.setPage(totalPage);
			roleUser.setStartRow(calculateStartRow(totalPage,roleUser.getPageResults()));
		}
		return new JpaPageResults<UserInfo>(roleUser.getPage(),roleUser.getPageResults(),totalCount,getMapper().gridUserInfoInRole(roleUser));
	}
	
	public JpaPageResults<UserInfo> gridUserInfoNotInRole(RoleUser roleUser) {
		Integer totalCount = parseCount(getMapper().countUserInfoNotInRole(roleUser));
		int totalPage = calculateTotalPage(roleUser,totalCount);
		if(totalPage == 0) {
			return new JpaPageResults<UserInfo>();
		}
		if(totalPage < roleUser.getPage()) {
			roleUser.setPage(totalPage);
			roleUser.setStartRow(calculateStartRow(totalPage,roleUser.getPageResults()));
		}
		return new JpaPageResults<UserInfo>(roleUser.getPage(),roleUser.getPageResults(),totalCount,getMapper().gridUserInfoNotInRole(roleUser));
	}
}
