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
 

package org.maxkey.web.historys.contorller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.entity.HistoryLoginApps;
import org.maxkey.persistence.service.HistoryLoginAppsService;
import org.maxkey.util.DateUtils;
import org.maxkey.web.WebContext;
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
 * 单点登录日志查询
 * 
 * @author Crystal.sea
 *
 */

@Controller
@RequestMapping(value={"/historys"})
public class LoginAppsHistoryController {
final static Logger _logger = LoggerFactory.getLogger(LoginAppsHistoryController.class);
	
	@Autowired
  	protected HistoryLoginAppsService historyLoginAppsService;
	

	@RequestMapping(value={"/loginAppsHistoryList"})
	public String loginAppsHistoryList(){
		return "historys/loginAppsHistoryList";
	}
	
	/**
	 * @param loginAppsHistory
	 * @return
	 */
	@RequestMapping(value={"/loginAppsHistoryList/grid"})
	@ResponseBody
	public JpaPageResults<HistoryLoginApps> loginAppsHistoryGrid(@ModelAttribute("historyLoginApp") HistoryLoginApps historyLoginApp){
		_logger.debug("historys/loginAppsHistory/datagrid/  "+historyLoginApp);
		historyLoginApp.setId(null);
		historyLoginApp.setInstId(WebContext.getUserInfo().getInstId());
		return historyLoginAppsService.queryPageResults(historyLoginApp);
	}


	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_DATE_HH_MM_SS);
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
