package org.maxkey.web.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
  	

	@RequestMapping(value={"/index"})
	public ModelAndView home() {
		_logger.debug("IndexController /index.");
		
		return  new ModelAndView("index");
	}
	
	@RequestMapping(value={"/"})
	public ModelAndView index() {
		_logger.debug("IndexController /.");
		return  new ModelAndView("index");
		
	}
	
	@RequestMapping(value={"/layout/top"})
	public ModelAndView top() {
		_logger.debug("IndexController /layout/top.");
		return  new ModelAndView("layout/top");
	}
	
	@RequestMapping(value={"/layout/nologintop"})
	public ModelAndView nologintop() {
		_logger.debug("IndexController /layout/nologintop.");
		return  new ModelAndView("layout/nologintop");
	}
	
	
	@RequestMapping(value={"/layout/left"})
	public ModelAndView left() {
		_logger.debug("IndexController /layout/left.");
        return  new ModelAndView("layout/left");
	}
	
	@RequestMapping(value={"/layout/main"})
	public ModelAndView main() {
		_logger.debug("IndexController /layout/main.");
		return  new ModelAndView("layout/main");
	}
	
	@RequestMapping(value={"/layout/bottom"})
	public ModelAndView bottom() {
		_logger.debug("IndexController /layout/bottom.");
		return  new ModelAndView("layout/bottom");
	}
	
	@RequestMapping(value={"/accessdeny"})
	public ModelAndView accessdeny() {
		_logger.debug("exception/accessdeny.");
		return  new ModelAndView("exception/accessdeny");
	}
}