/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.web.historys.contorller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.history.HistoryConnector;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.HistoryConnectorService;
import org.dromara.maxkey.util.DateUtils;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * 连接器日志查询
 * 
 * @author Crystal.sea
 *
 */

@RestController
@RequestMapping(value={"/historys"})
public class ConnectorHistoryController {
	static final  Logger logger = LoggerFactory.getLogger(ConnectorHistoryController.class);

	@Autowired
	HistoryConnectorService historyConnectorService;
	
	/**
     * @param historySynchronizer
     * @return
     */
    @GetMapping({"/connectorHistory/fetch"})
    @ResponseBody
    public Message<?> fetch(
    		@ModelAttribute("historyConnector") HistoryConnector historyConnector,
			@CurrentUser UserInfo currentUser){
    	logger.debug("historys/historyConnector/fetch/ {}",historyConnector);
        historyConnector.setInstId(currentUser.getInstId());
        return new Message<JpaPageResults<HistoryConnector>>(
        			historyConnectorService.fetchPageResults(historyConnector)
        		);
    }

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_DATE_HH_MM_SS);
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
