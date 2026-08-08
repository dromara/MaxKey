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
package org.dromara.maxkey.persistence.mapper;

import java.util.List;
import java.util.Map;

import org.dromara.maxkey.entity.dto.InstDto;
import org.dromara.mybatis.jpa.IJpaMapper;
import org.dromara.mybatis.jpa.entity.JpaEntity;


/**
 * @author Crystal.sea
 *
 */
public  interface ReportMapper extends IJpaMapper<JpaEntity,String> {
    
    public Integer analysisDayCount(InstDto inst);
    
    public Integer analysisMonthCount(InstDto inst);
    
    public Integer analysisNewUsers(InstDto inst);
    
    public Integer analysisOnlineUsers(InstDto inst);
    
    public Integer analysisActiveUsers(InstDto inst);
    
    public Integer totalUsers(InstDto inst);
    
    public Integer totalDepts(InstDto inst);
    
    public Integer totalApps(InstDto inst);
    
    public Integer totalGroups(InstDto inst);
    
    public List<Map<String,Object>> analysisDayHour(InstDto inst);
    
    public List<Map<String,Object>> analysisMonth(InstDto inst);
    
    public List<Map<String,Object>> analysisBrowser(InstDto inst);
    
    public List<Map<String,Object>> analysisApp(InstDto inst );
    
    public List<Map<String,Object>> analysisProvince(InstDto inst);
    
    public List<Map<String,Object>> analysisCountry(InstDto inst);
    
}
