/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.dromara.maxkey.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dromara.maxkey.persistence.mapper.ReportMapper;
import org.dromara.mybatis.jpa.JpaService;
import org.dromara.mybatis.jpa.entity.JpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ReportService  extends JpaService<JpaEntity>{

	public Integer analysisDay(HashMap<String,Object> reportParameter) {
		return getMapper().analysisDay(reportParameter);
	};
	
	public Integer analysisNewUsers(HashMap<String,Object> reportParameter) {
		return getMapper().analysisNewUsers(reportParameter);
	};
	
	public Integer analysisOnlineUsers(HashMap<String,Object> reportParameter) {
		return getMapper().analysisOnlineUsers(reportParameter);
	};
	
	public Integer analysisActiveUsers(HashMap<String,Object> reportParameter) {
		return getMapper().analysisActiveUsers(reportParameter);
	};
	
	public List<Map<String,Object>> analysisDayHour(HashMap<String,Object> reportParameter){
		return getMapper().analysisDayHour(reportParameter);
	}
	
	public List<Map<String,Object>> analysisMonth(HashMap<String,Object> reportParameter){
		return getMapper().analysisMonth(reportParameter);
	}
	
	
	public List<Map<String,Object>> analysisBrowser(HashMap<String,Object> reportParameter){
		return getMapper().analysisBrowser(reportParameter);
	}
	
	public List<Map<String,Object>> analysisApp(HashMap<String,Object> reportParameter){
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
		return (ReportMapper)super.getMapper();
	}
}
