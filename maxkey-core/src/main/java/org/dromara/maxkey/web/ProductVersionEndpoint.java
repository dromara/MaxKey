/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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

import java.io.IOException;

import org.apache.commons.lang.SystemUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ProductVersion
 * @author Crystal.Sea
 *
 */

@Controller
public class ProductVersionEndpoint {
	private static final Logger _logger = LoggerFactory.getLogger(ProductVersionEndpoint.class);
	
	static final String VERSION_STRING ="""
			<!DOCTYPE html>
			<html>
			<head>
			    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
			    <link rel="shortcut icon" type="image/x-icon" href="%s/static/favicon.ico"/>
			    <base href='%s'/> 
			    <title>MaxKey Single Sign-On</title>
			</head>
			<body>
			    <center>
			        <hr>
			        Maxkey  Community Edition <br>
			        Single   Sign  On ( SSO ) <br>
			        Version %s <br>
			        <br>
			        &copy; Copyright 2018 - %d https://www.maxkey.top/<br>
			        Licensed under the Apache License, Version 2.0 <br>
			        .&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			        All rights reserved
			        &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp.<br>
			        <hr>
			        JAVA &nbsp&nbsp : &nbsp&nbsp %s java version %s, class %s<br>
			                %s (build %s, %s)<br>
			        <hr>
			    </center>
			</body>
			</html>
			""";

	@GetMapping(value={"/"})
	public void version(HttpServletRequest request,HttpServletResponse response) throws IOException {
		_logger.debug("ProductVersion /");
		ServletOutputStream out = response.getOutputStream();
		String contextPath = request.getContextPath();
		out.println(
				String.format(
						VERSION_STRING,
						contextPath,
						contextPath,
						WebContext.getProperty("application.formatted-version"),
						new DateTime().getYear(),
						SystemUtils.JAVA_VENDOR,
                        SystemUtils.JAVA_VERSION,
                        SystemUtils.JAVA_CLASS_VERSION,
                        SystemUtils.JAVA_VM_NAME,
                        SystemUtils.JAVA_VM_VERSION,
                        SystemUtils.JAVA_VM_INFO));
		out.close();
	}
	
}
