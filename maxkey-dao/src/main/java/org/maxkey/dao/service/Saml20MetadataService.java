package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.Saml20MetadataMapper;
import org.maxkey.domain.Saml20Metadata;
import org.springframework.stereotype.Service;

@Service
public class Saml20MetadataService  extends JpaBaseService<Saml20Metadata>{

	public Saml20MetadataService() {
		super(Saml20MetadataMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public Saml20MetadataMapper getMapper() {
		// TODO Auto-generated method stub
		return (Saml20MetadataMapper)super.getMapper();
	}
	
}
