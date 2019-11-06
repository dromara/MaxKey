package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.HistoryLogsMapper;
import org.maxkey.domain.HistoryLogs;
import org.springframework.stereotype.Service;

@Service
public class HistoryLogsService  extends JpaBaseService<HistoryLogs>{

	public HistoryLogsService() {
		super(HistoryLogsMapper.class);
		
	}
	
	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public HistoryLogsMapper getMapper() {
		// TODO Auto-generated method stub
		return (HistoryLogsMapper)super.getMapper();
	}
}
