package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.ResourcesMapper;
import org.maxkey.domain.Resources;
import org.springframework.stereotype.Service;

@Service
public class ResourcesService  extends JpaBaseService<Resources>{
	
	public ResourcesService() {
		super(ResourcesMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public ResourcesMapper getMapper() {
		// TODO Auto-generated method stub
		return (ResourcesMapper)super.getMapper();
	}
}
