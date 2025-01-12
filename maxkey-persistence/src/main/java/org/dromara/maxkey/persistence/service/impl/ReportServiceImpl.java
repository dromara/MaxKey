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
 

package org.dromara.maxkey.persistence.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dromara.maxkey.persistence.mapper.ReportMapper;
import org.dromara.maxkey.persistence.service.ReportService;
import org.dromara.mybatis.jpa.entity.JpaEntity;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class ReportServiceImpl  extends JpaServiceImpl<ReportMapper,JpaEntity> implements ReportService{

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
	
	public List<Map<String,Object>> analysisProvince(HashMap<String,Object> reportParameter){
		List<Map<String,Object>> maps = getMapper().analysisProvince(reportParameter);
		if(null == maps) {
			return new ArrayList<>();
		}
		for(Map<String,Object> map : maps) {
			if(map.containsKey("reportstring")){
				String name = map.get("reportstring").toString();
				if (name.endsWith("省")
						|| name.endsWith("市")
						|| name.endsWith("特别行政区")
						|| name.endsWith("自治区")) {
					name = name.replace("省","")
							.replace("市","")
							.replace("特别行政区","")
							.replace("自治区","");
				}
				map.put("name",name);
			}
		}
		return maps;
	}
	
	public List<Map<String,Object>> analysisCountry(HashMap<String,Object> reportParameter){
		return getMapper().analysisCountry(reportParameter);
	}
	
}
