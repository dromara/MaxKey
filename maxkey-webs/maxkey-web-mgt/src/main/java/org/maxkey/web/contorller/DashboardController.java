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
 

package org.maxkey.web.contorller;

import java.util.HashMap;

import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.entity.Message;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Index
 * @author Crystal.Sea
 *
 */
@Controller
public class DashboardController {
	
	private static Logger _logger = LoggerFactory.getLogger(DashboardController.class);
	@Autowired
	@Qualifier("reportService")
	ReportService reportService;

	@RequestMapping(value={"/dashboard"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> dashboard(@CurrentUser UserInfo currentUser) {
		_logger.debug("IndexController /dashboard.");
		HashMap<String,Object> reportParameter = new HashMap<String,Object>();
		reportParameter.put("instId", currentUser.getInstId());
		
		reportParameter.put("dayCount", reportService.analysisDay(reportParameter));
		reportParameter.put("newUsers", reportService.analysisNewUsers(reportParameter));
		
		reportParameter.put("onlineUsers", reportService.analysisOnlineUsers(reportParameter));
		reportParameter.put("activeUsers", reportService.analysisActiveUsers(reportParameter));
		
		reportParameter.put("reportMonth", reportService.analysisMonth(reportParameter));
		reportParameter.put("reportDayHour", reportService.analysisDayHour(reportParameter));
		
		reportParameter.put("reportBrowser", reportService.analysisBrowser(reportParameter));
		reportParameter.put("reportApp", reportService.analysisApp(reportParameter));
		return new Message<HashMap<?,?>>(reportParameter).buildResponse();
	}

}
