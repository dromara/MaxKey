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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.domain.HistoryLoginApps;
import org.maxkey.domain.HistoryLogin;
import org.maxkey.domain.HistoryLogs;
import org.maxkey.persistence.service.HistoryLoginAppsService;
import org.maxkey.persistence.service.HistoryLoginService;
import org.maxkey.persistence.service.HistoryLogsService;
import org.maxkey.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录日志和操作日志查询
 * 
 * @author Crystal.sea
 *
 */

@Controller
@RequestMapping(value={"/logs"})
public class LogsController {
final static Logger _logger = LoggerFactory.getLogger(LogsController.class);
	
	@Autowired
	HistoryLoginService loginHistoryService;
	
	@Autowired
  	protected HistoryLoginAppsService loginAppsHistoryService;
	
	@Autowired
	HistoryLogsService logsService;
	
	/**
	 * 查询操作日志
	 * @param logs
	 * @return
	 */
	@RequestMapping(value={"/grid"})
	@ResponseBody
	public JpaPageResults<HistoryLogs> logsDataGrid(@ModelAttribute("logs") HistoryLogs logs){
		_logger.debug("logs/datagrid/ logsGrid() "+logs);
		return logsService.queryPageResults(logs);
	}
	
	
	@RequestMapping(value={"/list"})
	public String List(){
		return "logs/logsList";
	}
	
	@RequestMapping(value={"/loginHistoryList"})
	public String loginHistoryList(){
		return "logs/loginHistoryList";
	}
	
	/**
	 * @param HistoryLogin
	 * @return
	 */
	@RequestMapping(value={"/loginHistory/grid"})
	@ResponseBody
	public JpaPageResults<HistoryLogin> logAuthsGrid(@ModelAttribute("loginHistory") HistoryLogin loginHistory){
		_logger.debug("logs/loginHistory/datagrid/ logsGrid() "+loginHistory);
		return loginHistoryService.queryPageResults(loginHistory);
	}
	

	@RequestMapping(value={"/loginAppsHistoryList"})
	public String loginAppsHistoryList(){
		return "logs/loginAppsHistoryList";
	}
	

	
	/**
	 * @param loginAppsHistory
	 * @return
	 */
	@RequestMapping(value={"/loginAppsHistory/grid"})
	@ResponseBody
	public JpaPageResults<HistoryLoginApps> loginAppsHistoryGrid(@ModelAttribute("loginAppsHistory") HistoryLoginApps loginAppsHistory){
		_logger.debug("logs/loginAppsHistory/datagrid/ logsGrid() "+loginAppsHistory);
		loginAppsHistory.setId(null);
		return loginAppsHistoryService.queryPageResults(loginAppsHistory);
	}
	
	
	@RequestMapping(value={"/provisioningEventList"})
	public String provisioningEntryList(){
		return "logs/provisioningEventList";
	}

	

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_DATE_HH_MM_SS);
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
