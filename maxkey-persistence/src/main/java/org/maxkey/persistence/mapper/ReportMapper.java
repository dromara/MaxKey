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
