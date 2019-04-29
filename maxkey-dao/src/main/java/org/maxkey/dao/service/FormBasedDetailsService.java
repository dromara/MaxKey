package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.FormBasedDetailsMapper;
import org.maxkey.domain.apps.FormBasedDetails;
import org.springframework.stereotype.Service;

@Service
public class FormBasedDetailsService  extends JpaBaseService<FormBasedDetails>{

	public FormBasedDetailsService() {
		super(FormBasedDetailsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public FormBasedDetailsMapper getMapper() {
		return (FormBasedDetailsMapper)super.getMapper();
	}
	
	public FormBasedDetails getSassTemplet(String id){
		return getMapper().getSassTemplet(id);
	}
}
