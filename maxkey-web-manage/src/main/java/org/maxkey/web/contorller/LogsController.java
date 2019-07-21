package org.maxkey.web.contorller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.dao.service.LoginAppsHistoryService;
import org.maxkey.dao.service.LoginHistoryService;
import org.maxkey.dao.service.LogsService;
import org.maxkey.domain.LoginAppsHistory;
import org.maxkey.domain.LoginHistory;
import org.maxkey.domain.Logs;
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
	LoginHistoryService loginHistoryService;
	
	@Autowired
  	protected LoginAppsHistoryService loginAppsHistoryService;
	
	@Autowired
	LogsService logsService;
	
	/**
	 * 查询操作日志
	 * @param logs
	 * @return
	 */
	@RequestMapping(value={"/grid"})
	@ResponseBody
	public JpaPageResults<Logs> logsDataGrid(@ModelAttribute("logs") Logs logs){
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
	 * @param LoginHistory
	 * @return
	 */
	@RequestMapping(value={"/loginHistory/grid"})
	@ResponseBody
	public JpaPageResults<LoginHistory> logAuthsGrid(@ModelAttribute("loginHistory") LoginHistory loginHistory){
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
	public JpaPageResults<LoginAppsHistory> loginAppsHistoryGrid(@ModelAttribute("loginAppsHistory") LoginAppsHistory loginAppsHistory){
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
