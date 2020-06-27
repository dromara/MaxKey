package org.maxkey.web.endpoint;

import org.maxkey.persistence.service.ReportService;
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
		mainMView.addObject("rptDayCount", reportService.analysisDay(""));
		mainMView.addObject("rptNewUsers", reportService.analysisNewUsers(""));
		mainMView.addObject("rptOnlineUsers", reportService.analysisOnlineUsers(""));
		mainMView.addObject("rptActiveUsers", reportService.analysisActiveUsers(""));
		
		mainMView.addObject("rptMonth", reportService.analysisMonth(""));
		mainMView.addObject("rptDayHour", reportService.analysisDayHour(""));
		mainMView.addObject("rptBrowser", reportService.analysisBrowser(null));
		mainMView.addObject("rptApp", reportService.analysisApp(null));
		return  mainMView;
	}
	
	@RequestMapping(value={"/"})
	public ModelAndView index() {
		_logger.debug("IndexController /.");
		return  new ModelAndView("index");
		
	}
}