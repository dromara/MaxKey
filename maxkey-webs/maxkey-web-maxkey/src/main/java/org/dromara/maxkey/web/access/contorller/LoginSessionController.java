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
 

package org.dromara.maxkey.web.access.contorller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.history.HistoryLogin;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.HistoryLoginService;
import org.dromara.maxkey.util.DateUtils;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录会话管理.
 * 
 * @author Crystal.sea
 *
 */

@RestController
@RequestMapping(value = { "/access/session" })
public class LoginSessionController {
    static final Logger logger = LoggerFactory.getLogger(LoginSessionController.class);

    @Autowired
    HistoryLoginService historyLoginService;
    
    @Autowired
    SessionManager sessionManager;

    /**
     * 查询登录日志.
     * 
     * @param logsAuth
     * @return
     */
    @GetMapping(value = { "/fetch" })
    public Message<JpaPageResults<HistoryLogin>> fetch(
    			@ModelAttribute("historyLogin") HistoryLogin historyLogin,
    			@CurrentUser UserInfo currentUser) {
        logger.debug("history/session/fetch {}" , historyLogin);
        historyLogin.setUserId(currentUser.getId());
        historyLogin.setInstId(currentUser.getInstId());
        return new Message<JpaPageResults<HistoryLogin>>(
        			historyLoginService.queryOnlineSession(historyLogin)
        		);
    }

    @DeleteMapping(value="/terminate")  
    public Message<HistoryLogin> terminate(@RequestParam("ids") List<String> ids,@CurrentUser UserInfo currentUser) {
        logger.debug("ids {}",ids);
        boolean isTerminated = false;
        try {
            for(String sessionId : ids) {
                logger.trace("terminate session Id {} ",sessionId);
                if(currentUser.getSessionId().contains(sessionId)) {
                    continue;//skip current session
                }
                
                sessionManager.terminate(
                		sessionId,
                		currentUser.getId(),
                		currentUser.getUsername());
            }
            isTerminated = true;
        }catch(Exception e) {
            logger.debug("terminate Exception .",e);
        }
        
        if(isTerminated) {
        	return new Message<>(Message.SUCCESS);
        } else {
        	return new Message<>(Message.ERROR);
        }
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_DATE_HH_MM_SS);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
