package org.maxkey.persistence.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.domain.GroupPrivileges;
import org.maxkey.persistence.mapper.GroupPrivilegesMapper;
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

}
