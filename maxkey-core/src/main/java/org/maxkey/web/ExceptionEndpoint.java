package org.maxkey.web;

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
public class ExceptionEndpoint {
	private static Logger _logger = LoggerFactory.getLogger(ExceptionEndpoint.class);
	
	@RequestMapping(value={"/exception/error/400"})
	public ModelAndView error400() {
		_logger.debug("exception/400.");
		return  new ModelAndView("exception/400");
	}
	
	@RequestMapping(value={"/exception/error/404"})
	public ModelAndView error404() {
		_logger.debug("exception/404.");
		return  new ModelAndView("exception/404");
	}
	@RequestMapping(value={"/exception/error/500"})
	public ModelAndView error500() {
		_logger.debug("exception/500.");
		return  new ModelAndView("exception/500");
	}
	@RequestMapping(value={"/exception/accessdeny"})
	public ModelAndView accessdeny() {
		_logger.debug("exception/accessdeny.");
		return  new ModelAndView("exception/accessdeny");
	}
}