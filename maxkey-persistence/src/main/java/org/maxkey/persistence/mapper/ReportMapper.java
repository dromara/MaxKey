/**
 * 
 */
package org.maxkey.persistence.mapper;

import java.util.List;
import java.util.Map;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.apache.mybatis.jpa.persistence.JpaBaseDomain;


/**
 * @author Crystal.sea
 *
 */
public  interface ReportMapper extends IJpaBaseMapper<JpaBaseDomain> {
	
	public Integer analysisDay(String reportParameter);
	public Integer analysisNewUsers(String reportParameter);
	public Integer analysisOnlineUsers(String reportParameter);
	public Integer analysisActiveUsers(String reportParameter);
	
	public List<Map<String,Object>> analysisDayHour(String reportParameter);
	
	public List<Map<String,Object>> analysisMonth(String reportParameter);
	
	public List<Map<String,Object>> analysisBrowser(Map<String,Object> reportParameter);
	
	public List<Map<String,Object>> analysisApp(Map<String,Object> reportParameter );
	
	
}
