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
import java.util.List;
import java.util.Map;

import org.dromara.maxkey.entity.dto.InstDto;
import org.dromara.maxkey.persistence.mapper.ReportMapper;
import org.dromara.maxkey.persistence.service.ReportService;
import org.dromara.mybatis.jpa.entity.JpaEntity;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class ReportServiceImpl  extends JpaServiceImpl<ReportMapper,JpaEntity,String> implements ReportService{

    @Override
    public Integer analysisDayCount(InstDto inst) {
        return getMapper().analysisDayCount(inst);
    }
    
    @Override
    public Integer analysisNewUsers(InstDto inst) {
        return getMapper().analysisNewUsers(inst);
    }
    
    @Override
    public Integer analysisOnlineUsers(InstDto inst) {
        return getMapper().analysisOnlineUsers(inst);
    }
    
    @Override
    public Integer analysisActiveUsers(InstDto inst) {
        return getMapper().analysisActiveUsers(inst);
    }
    
    @Override
    public Integer totalUsers(InstDto inst) {
        return getMapper().totalUsers(inst);
    }
    
    @Override
    public Integer totalDepts(InstDto inst) {
        return getMapper().totalDepts(inst);
    }
    
    @Override
    public Integer totalApps(InstDto inst) {
        return getMapper().totalApps(inst);
    }
    
    @Override
    public List<Map<String,Object>> analysisDayHour(InstDto inst){
        return getMapper().analysisDayHour(inst);
    }
    
    @Override
    public List<Map<String,Object>> analysisMonth(InstDto inst){
        return getMapper().analysisMonth(inst);
    }
    
    
    @Override
    public List<Map<String,Object>> analysisBrowser(InstDto inst){
        return getMapper().analysisBrowser(inst);
    }
    
    @Override
    public List<Map<String,Object>> analysisApp(InstDto inst){
        return getMapper().analysisApp(inst);
    }
    
    @Override
    public List<Map<String,Object>> analysisProvince(InstDto inst){
        List<Map<String,Object>> maps = getMapper().analysisProvince(inst);
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
    
    @Override
    public List<Map<String,Object>> analysisCountry(InstDto inst){
        return getMapper().analysisCountry(inst);
    }

	@Override
	public Integer analysisMonthCount(InstDto inst) {
		return getMapper().analysisMonthCount(inst);
	}

	@Override
	public Integer totalGroups(InstDto inst) {
		return getMapper().totalGroups(inst);
	}
    
}
