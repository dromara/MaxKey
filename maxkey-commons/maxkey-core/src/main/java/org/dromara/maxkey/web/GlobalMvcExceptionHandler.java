/*
 * Copyright [2026] [MaxKey of copyright http://www.maxkey.top]
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
 

 

package org.dromara.maxkey.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * MVC全局异常处理器,可以定义多个@ExceptionHandler处理不同的错误类型
 *
 */
@ControllerAdvice
public class GlobalMvcExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalMvcExceptionHandler.class);

    /**
     * mvcExceptionHandler
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e) {
    	logger.error("System Exception", e);
        ModelAndView modelAndView = new ModelAndView("exception/500");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
}
