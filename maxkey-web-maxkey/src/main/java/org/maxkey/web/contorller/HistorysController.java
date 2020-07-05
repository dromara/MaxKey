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
import org.maxkey.domain.HistoryLogin;
import org.maxkey.domain.HistoryLoginApps;
import org.maxkey.domain.HistoryLogs;
import org.maxkey.persistence.service.HistoryLoginAppsService;
import org.maxkey.persistence.service.HistoryLoginService;
import org.maxkey.persistence.service.HistoryLogsService;
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
 * 登录日志和操作日志查询.
 * 
 * @author Crystal.sea
 *
 */

@Controller
@RequestMapping(value = { "/historys" })
public class HistorysController {
    static final Logger _logger = LoggerFactory.getLogger(HistorysController.class);

    @Autowired
    HistoryLoginService historyLoginService;

    @Autowired
    protected HistoryLoginAppsService historyLoginAppsService;

    @Autowired
    HistoryLogsService historyLogsService;

    @RequestMapping(value = { "/logs" })
    public String List() {
        return "historys/logsList";
    }

    /**
     * 查询操作日志.
     * 
     * @param logs
     * @return
     */
    @RequestMapping(value = { "/logs/grid" })
    @ResponseBody
    public JpaPageResults<HistoryLogs> logsDataGrid(@ModelAttribute("historyLogs") HistoryLogs historyLogs) {
        _logger.debug("history/logs/grid/ logsGrid() " + historyLogs);
        return historyLogsService.queryPageResults(historyLogs);
    }

    @RequestMapping(value = { "/login" })
    public String authList() {
        return "historys/loginList";
    }

    /**
     * 查询登录日志.
     * 
     * @param logsAuth
     * @return
     */
    @RequestMapping(value = { "/login/grid" })
    @ResponseBody
    public JpaPageResults<HistoryLogin> logAuthsGrid(@ModelAttribute("historyLogin") HistoryLogin historyLogin) {
        _logger.debug("history/login/grid/ logsGrid() " + historyLogin);
        historyLogin.setUid(WebContext.getUserInfo().getId());
        return historyLoginService.queryPageResults(historyLogin);
    }

    @RequestMapping(value = { "/loginApps" })
    public String loginAppHistoryList() {
        return "historys/loginAppsList";
    }

    /**
     * 查询单点登录日志.
     * 
     * @param logsSso
     * @return
     */
    @RequestMapping(value = { "/loginApps/grid" })
    @ResponseBody
    public JpaPageResults<HistoryLoginApps> logsSsoGrid(
            @ModelAttribute("historyLoginApps") HistoryLoginApps historyLoginApps) {
        _logger.debug("history/loginApps/grid/ logsGrid() " + historyLoginApps);
        historyLoginApps.setId(null);

        return historyLoginAppsService.queryPageResults(historyLoginApps);

    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_DATE_HH_MM_SS);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
