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
 

package org.maxkey.web.historys.contorller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.entity.HistoryConnector;
import org.maxkey.persistence.service.HistoryConnectorService;
import org.maxkey.util.DateUtils;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 连接器日志查询
 * 
 * @author Crystal.sea
 *
 */

@Controller
@RequestMapping(value={"/historys"})
public class ConnectorHistoryController {
final static Logger _logger = LoggerFactory.getLogger(ConnectorHistoryController.class);

	@Autowired
    @Qualifier("historyConnectorService")
	HistoryConnectorService historyConnectorService;
	
	
	@RequestMapping(value={"/connectorHistoryList"})
    public String historySynchronizerList(){
        return "historys/connectorHistoryList";
    }
	
	/**
     * @param historySynchronizer
     * @return
     */
    @RequestMapping(value={"/connectorHistoryList/grid"})
    @ResponseBody
    public JpaPageResults<HistoryConnector> historySynchronizerGrid(@ModelAttribute("historyConnector") HistoryConnector historyConnector){
        _logger.debug("historys/historyConnector/grid/ "+historyConnector);
        historyConnector.setInstId(WebContext.getUserInfo().getInstId());
        return historyConnectorService.queryPageResults(historyConnector);
    }


	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_DATE_HH_MM_SS);
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
