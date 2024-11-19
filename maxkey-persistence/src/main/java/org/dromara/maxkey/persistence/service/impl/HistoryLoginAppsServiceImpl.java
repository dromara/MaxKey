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

import org.dromara.maxkey.entity.history.HistoryLoginApps;
import org.dromara.maxkey.persistence.mapper.HistoryLoginAppsMapper;
import org.dromara.maxkey.persistence.service.HistoryLoginAppsService;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryLoginAppsServiceImpl  extends JpaServiceImpl<HistoryLoginAppsMapper,HistoryLoginApps> implements HistoryLoginAppsService{

	@Override
	public boolean  insert(HistoryLoginApps loginAppsHistory){
		//new Thread insert login app history
		new Thread(new HistoryLoginAppsRunnable(getMapper(),loginAppsHistory)).start();
		return true;
	}
	
	public class HistoryLoginAppsRunnable implements Runnable{

		HistoryLoginAppsMapper historyLoginAppsMapper;
		
		HistoryLoginApps loginAppsHistory;
		
		public HistoryLoginAppsRunnable(HistoryLoginAppsMapper historyLoginAppsMapper,
				HistoryLoginApps loginAppsHistory) {
			super();
			this.historyLoginAppsMapper = historyLoginAppsMapper;
			this.loginAppsHistory = loginAppsHistory;
		}

		@Override
		public void run() {
			historyLoginAppsMapper.insert(loginAppsHistory);
		}
		
	}
}
