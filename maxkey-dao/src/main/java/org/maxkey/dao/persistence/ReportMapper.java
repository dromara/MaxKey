/**
 * 
 */
package org.maxkey.dao.persistence;

import java.util.List;
import java.util.Map;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.apache.mybatis.jpa.persistence.JpaBaseDomain;


/**
 * @author Crystal.sea
 *
 */
public  interface ReportMapper extends IJpaBaseMapper<JpaBaseDomain> {
	
	public List<Map<String,Object>> analysisDay(String reportDate);
	
	public List<Map<String,Object>> analysisMonth(String reportDate);
	
	public List<Map<String,Object>> analysisYear(Integer reportYear);
	
	public List<Map<String,Object>> analysisBrowser(Map<String,Object> reportDate);
	
	public List<Map<String,Object>> analysisApp(Map<String,Object> reportDate);
	
	public List<Map<String,Object>> analysisYears();
	
	
}
