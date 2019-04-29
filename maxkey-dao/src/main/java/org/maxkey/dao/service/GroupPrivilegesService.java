package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.dao.persistence.GroupPrivilegesMapper;
import org.maxkey.domain.GroupPrivileges;
import org.maxkey.domain.apps.Applications;
import org.springframework.stereotype.Service;

@Service
public class GroupPrivilegesService  extends JpaBaseService<GroupPrivileges>{
	
	public GroupPrivilegesService() {
		super(GroupPrivilegesMapper.class);
	}
	

	
	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public GroupPrivilegesMapper getMapper() {
		// TODO Auto-generated method stub
		return (GroupPrivilegesMapper)super.getMapper();
	}



	public JpaPageResults<Applications> gridAppsInGroup(GroupPrivileges entity) {
		Integer totalCount = parseCount(getMapper().countAppsInGroup(entity));
		if(totalCount == 0) {
			return new JpaPageResults<Applications>();
		}
		
		int totalPage = calculateTotalPage(entity,totalCount);
		
		if(totalPage == 0) {
			return new JpaPageResults<Applications>();
		}
		
		if(totalPage < entity.getPage()) {
			entity.setPage(totalPage);
			entity.setStartRow(calculateStartRow(totalPage ,entity.getPageResults()));
		}
		entity.setPageable(true);
		return new JpaPageResults<Applications>(entity.getPage(),entity.getPageResults(),totalCount,getMapper().gridAppsInGroup(entity));
	}
	
	public JpaPageResults<Applications> gridAppsNotInGroupGrid(GroupPrivileges entity) {
		Integer totalCount = parseCount(getMapper().countAppsNotInGroup(entity));
		if(totalCount == 0) {
			return new JpaPageResults<Applications>();
		}
		
		int totalPage = calculateTotalPage(entity,totalCount);
		
		if(totalPage == 0) {
			return new JpaPageResults<Applications>();
		}
		
		if(totalPage < entity.getPage()) {
			entity.setPage(totalPage);
			entity.setStartRow(calculateStartRow(totalPage ,entity.getPageResults()));
		}
		entity.setPageable(true);
		return new JpaPageResults<Applications>(entity.getPage(),entity.getPageResults(),totalCount,getMapper().gridAppsNotInGroup(entity));
	}
	

}
