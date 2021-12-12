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
 

package org.maxkey.web.endpoint;

import java.util.HashMap;

import org.maxkey.persistence.service.ReportService;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Index
 * @author Crystal.Sea
 *
 */
@Controller
public class IndexEndpoint {
	
	private static Logger _logger = LoggerFactory.getLogger(IndexEndpoint.class);
	@Autowired
	@Qualifier("reportService")
	ReportService reportService;

	@RequestMapping(value={"/main"})
	public ModelAndView home() {
		_logger.debug("IndexController /main.");
		ModelAndView mainMView=new ModelAndView("main");
		HashMap<String,Object> reportParameter = new HashMap<String,Object>();
		reportParameter.put("instId", WebContext.getUserInfo().getInstId());
		mainMView.addObject("rptDayCount", reportService.analysisDay(reportParameter));
		mainMView.addObject("rptNewUsers", reportService.analysisNewUsers(reportParameter));
		mainMView.addObject("rptOnlineUsers", reportService.analysisOnlineUsers(reportParameter));
		mainMView.addObject("rptActiveUsers", reportService.analysisActiveUsers(reportParameter));
		
		mainMView.addObject("rptMonth", reportService.analysisMonth(reportParameter));
		mainMView.addObject("rptDayHour", reportService.analysisDayHour(reportParameter));
		mainMView.addObject("rptBrowser", reportService.analysisBrowser(reportParameter));
		mainMView.addObject("rptApp", reportService.analysisApp(reportParameter));
		return  mainMView;
	}
	
	@RequestMapping(value={"/"})
	public ModelAndView index() {
		_logger.debug("IndexController /.");
		return  new ModelAndView("index");
		
	}
}
