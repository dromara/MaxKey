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

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.apps.UserApps;
import org.dromara.maxkey.persistence.mapper.AppsMapper;
import org.dromara.maxkey.persistence.service.AppsService;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.springframework.stereotype.Repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@Repository
public class AppsServiceImpl extends JpaServiceImpl<AppsMapper,Apps> implements AppsService{
	//maxkey-mgt
	public static final  	String MGT_APP_ID 		= "622076759805923328";
	
	public static final  	String DETAIL_SUFFIX	=	"_detail";
	
	protected static final   Cache<String, Apps> detailsCacheStore = 
										Caffeine.newBuilder()
							                .expireAfterWrite(30, TimeUnit.MINUTES)
							                .build();
	
	public boolean insertApp(Apps app) {
		return ((AppsMapper)super.getMapper()).insertApp(app)>0;
	};
	public boolean updateApp(Apps app) {
		return ((AppsMapper)super.getMapper()).updateApp(app)>0;
	};
	
	public boolean updateExtendAttr(Apps app) {
		return ((AppsMapper)super.getMapper()).updateExtendAttr(app)>0;
	}
	
    public List<UserApps> queryMyApps(UserApps userApplications){
        return getMapper().queryMyApps(userApplications);
    }

    //cache for running
    public void put(String appId, Apps appDetails) {
    	detailsCacheStore.put(appId + DETAIL_SUFFIX, appDetails);
	}
	
    public Apps get(String appId, boolean cached) {
    	appId = appId.equalsIgnoreCase("maxkey_mgt") ? MGT_APP_ID : appId;
    	Apps appDetails = null;
    	if(cached) {
    		appDetails = detailsCacheStore.getIfPresent(appId + DETAIL_SUFFIX); 
    		if(appDetails == null) {
    			appDetails = this.get(appId);
    			detailsCacheStore.put(appId, appDetails);
    		}
    	}else {
    		appDetails = this.get(appId);
    	}
        return appDetails;
    }
    
}
