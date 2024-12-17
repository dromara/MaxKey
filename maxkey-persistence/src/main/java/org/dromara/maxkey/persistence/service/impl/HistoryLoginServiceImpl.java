/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.entity.history.HistoryLogin;
import org.dromara.maxkey.persistence.mapper.HistoryLoginMapper;
import org.dromara.maxkey.persistence.service.HistoryLoginService;
import org.dromara.maxkey.web.WebContext;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryLoginServiceImpl  extends JpaServiceImpl<HistoryLoginMapper,HistoryLogin> implements HistoryLoginService{
	private static Logger logger = LoggerFactory.getLogger(HistoryLoginServiceImpl.class);
	
	public JpaPageResults<HistoryLogin> queryOnlineSession(HistoryLogin historyLogin) {
	    return this.fetchPageResults("queryOnlineSession",historyLogin);
	}
	
	 public void login(HistoryLogin historyLogin) {
	        historyLogin.setId(WebContext.genId());
	        if(StringUtils.isBlank(historyLogin.getInstId())) {
	        	historyLogin.setInstId("1");
	        }
	        //Thread insert 
	        new Thread(new HistoryLoginRunnable(this,historyLogin)).start();
	    }
	    
		public class HistoryLoginRunnable implements Runnable{
			
			HistoryLoginService historyLoginService;
			
			HistoryLogin historyLogin;
			
			public HistoryLoginRunnable(HistoryLoginService historyLoginService, HistoryLogin historyLogin) {
				super();
				this.historyLoginService = historyLoginService;
				this.historyLogin = historyLogin;
			}

			@Override
		    public void run() {
				logger.debug("History Login {}" , historyLogin);
				this.historyLoginService.insert(historyLogin);
			}
		}
}
