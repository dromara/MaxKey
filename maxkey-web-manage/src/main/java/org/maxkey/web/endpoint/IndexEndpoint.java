package org.maxkey.web.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  	

	@RequestMapping(value={"/main"})
	public ModelAndView home() {
		_logger.debug("IndexController /main.");
		
		return  new ModelAndView("main");
	}
	
	@RequestMapping(value={"/"})
	public ModelAndView index() {
		_logger.debug("IndexController /.");
		return  new ModelAndView("index");
		
	}
}