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
 

package org.maxkey.web.access.contorller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.authn.online.OnlineTicketService;
import org.maxkey.entity.HistoryLogin;
import org.maxkey.entity.Message;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.HistoryLoginService;
import org.maxkey.util.DateUtils;
import org.maxkey.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录会话管理.
 * 
 * @author Crystal.sea
 *
 */

@Controller
@RequestMapping(value = { "/access/session" })
public class LoginSessionController {
    static final Logger _logger = LoggerFactory.getLogger(LoginSessionController.class);

    @Autowired
    HistoryLoginService historyLoginService;
    
    @Autowired
    OnlineTicketService onlineTicketService;

    /**
     * 查询登录日志.
     * 
     * @param logsAuth
     * @return
     */
    @RequestMapping(value = { "/fetch" })
    @ResponseBody
    public ResponseEntity<?> fetch(
    			@ModelAttribute("historyLogin") HistoryLogin historyLogin,
    			@CurrentUser UserInfo currentUser) {
        _logger.debug("history/session/fetch {}" , historyLogin);
        historyLogin.setUserId(currentUser.getId());
        historyLogin.setInstId(currentUser.getInstId());
        return new Message<JpaPageResults<HistoryLogin>>(
        			historyLoginService.queryOnlineSession(historyLogin)
        		).buildResponse();
    }

    @ResponseBody
    @RequestMapping(value="/terminate")  
    public ResponseEntity<?> terminate(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
        _logger.debug(ids);
        boolean isTerminated = false;
        try {
            for(String sessionId : StringUtils.string2List(ids, ",")) {
                _logger.trace("terminate session Id {} ",sessionId);
                if(currentUser.getOnlineTicket().contains(sessionId)) {
                    continue;//skip current session
                }
                
                onlineTicketService.terminate(
                		sessionId,
                		currentUser.getId(),
                		currentUser.getUsername());
            }
            isTerminated = true;
        }catch(Exception e) {
            _logger.debug("terminate Exception .",e);
        }
        
        if(isTerminated) {
        	return new Message<HistoryLogin>(Message.SUCCESS).buildResponse();
        } else {
        	return new Message<HistoryLogin>(Message.ERROR).buildResponse();
        }
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_DATE_HH_MM_SS);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
