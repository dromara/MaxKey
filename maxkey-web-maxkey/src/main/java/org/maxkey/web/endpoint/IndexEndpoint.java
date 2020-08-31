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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.configuration.ApplicationConfig;
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
  	@Qualifier("applicationConfig")
  	ApplicationConfig applicationConfig;
	@RequestMapping(value={"/forwardindex"})
	public ModelAndView forwardindex(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		_logger.debug("IndexEndpoint /forwardindex.");
        String defaultUri = applicationConfig.getLoginConfig().getDefaultUri();
        if (defaultUri != null && !defaultUri.equals("")) {
            _logger.debug("defaultUri " + defaultUri);
            return WebContext.redirect(applicationConfig.getLoginConfig().getDefaultUri());
        }
        _logger.debug("Uri /appList");
		return  new ModelAndView("/appList");
	}
	
	@RequestMapping(value={"/index"})
	public ModelAndView home(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		_logger.debug("home /index.");
		return  new ModelAndView("index");
	}
	
	@RequestMapping(value={"/"})
	public ModelAndView index() {
		_logger.debug("IndexEndpoint /.");
		return  new ModelAndView("index");
		
	}
}
