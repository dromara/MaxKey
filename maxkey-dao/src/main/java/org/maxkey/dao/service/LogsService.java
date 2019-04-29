package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.LogsMapper;
import org.maxkey.domain.Logs;
import org.springframework.stereotype.Service;

@Service
public class LogsService  extends JpaBaseService<Logs>{

	public LogsService() {
		super(LogsMapper.class);
		
	}
	
	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public LogsMapper getMapper() {
		// TODO Auto-generated method stub
		return (LogsMapper)super.getMapper();
	}
}
