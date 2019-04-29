package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.Saml20DetailsMapper;
import org.maxkey.domain.apps.SAML20Details;
import org.springframework.stereotype.Service;

@Service
public class Saml20DetailsService  extends JpaBaseService<SAML20Details>{

	public Saml20DetailsService() {
		super(Saml20DetailsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public Saml20DetailsMapper getMapper() {
		// TODO Auto-generated method stub
		return (Saml20DetailsMapper)super.getMapper();
	}
	
	public  SAML20Details  getSassTemplet(String id){
		return getMapper().getSassTemplet(id);
	}
}
