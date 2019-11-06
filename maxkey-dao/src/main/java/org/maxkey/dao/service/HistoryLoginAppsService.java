package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.HistoryLoginAppsMapper;
import org.maxkey.domain.HistoryLoginApps;
import org.springframework.stereotype.Service;

@Service
public class HistoryLoginAppsService  extends JpaBaseService<HistoryLoginApps>{

	public HistoryLoginAppsService() {
		super(HistoryLoginAppsMapper.class);
	}


	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public HistoryLoginAppsMapper getMapper() {
		// TODO Auto-generated method stub
		return (HistoryLoginAppsMapper)super.getMapper();
	}
	
	public boolean  insert(HistoryLoginApps loginAppsHistory){
		return getMapper().insert(loginAppsHistory)> 0;
	}
}
