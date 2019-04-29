package org.maxkey.dao.service;

import java.util.List;
import java.util.Map;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;
import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.ReportMapper;
import org.springframework.stereotype.Service;

@Service
public class ReportService  extends JpaBaseService<JpaBaseDomain>{

	
	public List<Map<String,Object>> analysisDay(String reportDate){
		return getMapper().analysisDay(reportDate);
	}
	
	public List<Map<String,Object>> analysisMonth(String reportDate){
		return getMapper().analysisMonth(reportDate);
	}
	
	public List<Map<String,Object>> analysisYear(Integer reportYear){
		return getMapper().analysisYear(reportYear);
	}
	
	public List<Map<String,Object>> analysisBrowser(Map<String,Object> reportDate){
		return getMapper().analysisBrowser(reportDate);
	}
	
	public List<Map<String,Object>> analysisApp(Map<String,Object> reportDate){
		return getMapper().analysisApp(reportDate);
	}
	
	public List<Map<String,Object>> analysisYears(){
		return getMapper().analysisYears();
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
