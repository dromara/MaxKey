package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.GroupsMapper;
import org.maxkey.domain.Groups;
import org.springframework.stereotype.Service;

@Service
public class GroupsService  extends JpaBaseService<Groups>{
	
	public GroupsService() {
		super(GroupsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public GroupsMapper getMapper() {
		// TODO Auto-generated method stub
		return (GroupsMapper)super.getMapper();
	}
}
