package org.maxkey.dao.service;

import java.util.List;
import java.util.Map;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;
import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.ReportMapper;
import org.springframework.stereotype.Service;

@Service
public class ReportService  extends JpaBaseService<JpaBaseDomain>{

	public Integer analysisDay(String reportParameter) {
		return getMapper().analysisDay(reportParameter);
	};
	
	public Integer analysisNewUsers(String reportParameter) {
		return getMapper().analysisNewUsers(reportParameter);
	};
	
	public Integer analysisOnlineUsers(String reportParameter) {
		return getMapper().analysisOnlineUsers(reportParameter);
	};
	
	public Integer analysisActiveUsers(String reportParameter) {
		return getMapper().analysisActiveUsers(reportParameter);
	};
	
	public List<Map<String,Object>> analysisDayHour(String reportParameter){
		return getMapper().analysisDayHour(reportParameter);
	}
	
	public List<Map<String,Object>> analysisMonth(String reportParameter){
		return getMapper().analysisMonth(reportParameter);
	}
	
	
	public List<Map<String,Object>> analysisBrowser(Map<String,Object> reportParameter){
		return getMapper().analysisBrowser(reportParameter);
	}
	
	public List<Map<String,Object>> analysisApp(Map<String,Object> reportParameter){
		return getMapper().analysisApp(reportParameter);
	}
	
	
	
	public ReportService() {
		super(ReportMapper.class);
		
	}
	
	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public ReportMapper getMapper() {
		// TODO Auto-generated method stub
		return (ReportMapper)super.getMapper();
	}
}
