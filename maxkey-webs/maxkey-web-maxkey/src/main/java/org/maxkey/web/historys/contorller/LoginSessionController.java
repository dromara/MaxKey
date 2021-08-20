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
import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authn.online.OnlineTicketServices;
import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.entity.HistoryLogin;
import org.maxkey.entity.HistoryLoginApps;
import org.maxkey.entity.HistoryLogs;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.db.LoginHistoryService;
import org.maxkey.persistence.db.LoginService;
import org.maxkey.persistence.service.HistoryLoginAppsService;
import org.maxkey.persistence.service.HistoryLoginService;
import org.maxkey.persistence.service.HistorySystemLogsService;
import org.maxkey.util.DateUtils;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录日志查询.
 * 
 * @author Crystal.sea
 *
 */

@Controller
@RequestMapping(value = { "/loginsession" })
public class LoginSessionController {
    static final Logger _logger = LoggerFactory.getLogger(LoginSessionController.class);

    @Autowired
    HistoryLoginService historyLoginService;
    @Autowired
    LoginService loginService;
    
    @Autowired
    LoginHistoryService loginHistoryService;
    
    @Autowired
    OnlineTicketServices onlineTicketServices;
    
    @RequestMapping(value = { "/loginSessionList" })
    public String authList() {
        return "historys/loginSessionList";
    }

    /**
     * 查询登录日志.
     * 
     * @param logsAuth
     * @return
     */
    @RequestMapping(value = { "/loginSessionList/grid" })
    @ResponseBody
    public JpaPageResults<HistoryLogin> loginSessionListGrid(@ModelAttribute("historyLogin") HistoryLogin historyLogin) {
        _logger.debug("history/loginsession/ loginSessionListGrid() " + historyLogin);
        historyLogin.setUserId(WebContext.getUserInfo().getId());
        return historyLoginService.queryOnlineSession(historyLogin);
    }


    
    @ResponseBody
    @RequestMapping(value="/terminate")  
    public Message deleteUsersById(@RequestParam("id") String ids) {
        _logger.debug(ids);
        boolean isTerminated = false;
        try {
            OnlineTicket onlineTicket = null;
            Authentication authentication = WebContext.getAuthentication();
            if(authentication.getPrincipal() instanceof SigninPrincipal) {
                SigninPrincipal signinPrincipal = (SigninPrincipal)authentication.getPrincipal();
                //onlineTicket 
                onlineTicket = signinPrincipal.getOnlineTicket();
                
            }
            for(String sessionId : StringUtils.string2List(ids, ",")) {
                _logger.trace("terminate session Id {} ",sessionId);
                if(onlineTicket.getTicketId().contains(sessionId)) {
                    //skip current session
                    continue;
                }
                UserInfo userInfo = WebContext.getUserInfo();
                String lastLogoffTime = DateUtils.formatDateTime(new Date());
                loginService.setLastLogoffInfo(userInfo);
                loginHistoryService.logoff(lastLogoffTime, sessionId);
                onlineTicketServices.remove("OT-" + sessionId);
            }
            isTerminated = true;
        }catch(Exception e) {
            _logger.debug("terminate Exception .",e);
        }
        
        if(isTerminated) {
            return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.success);
        } else {
            return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_ERROR),MessageType.error);
        }
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_DATE_HH_MM_SS);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
