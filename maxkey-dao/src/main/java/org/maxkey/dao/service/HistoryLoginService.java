package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.HistoryLoginMapper;
import org.maxkey.domain.HistoryLogin;
import org.springframework.stereotype.Service;

@Service
public class HistoryLoginService  extends JpaBaseService<HistoryLogin>{

	public HistoryLoginService() {
		super(HistoryLoginMapper.class);
	}
	
	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public HistoryLoginMapper getMapper() {
		// TODO Auto-generated method stub
		return (HistoryLoginMapper)super.getMapper();
	}
}
