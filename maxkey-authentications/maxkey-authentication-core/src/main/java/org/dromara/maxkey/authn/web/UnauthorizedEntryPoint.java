/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.authn.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/auth")
public class UnauthorizedEntryPoint {
	private static final Logger _logger = LoggerFactory.getLogger(UnauthorizedEntryPoint.class);
	
 	@RequestMapping(value={"/entrypoint"})
	public void entryPoint(
			HttpServletRequest request, HttpServletResponse response) 
					throws StreamWriteException, DatabindException, IOException {
 		_logger.trace("UnauthorizedEntryPoint /entrypoint.");
 		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
 	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

 	    final Map<String, Object> responseBody = new HashMap<>();
 	    responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
 	    responseBody.put("error", "Unauthorized");
 	    responseBody.put("message", "Unauthorized");
 	    responseBody.put("path", request.getServletPath());

 	    final ObjectMapper mapper = new ObjectMapper();
 	    mapper.writeValue(response.getOutputStream(), responseBody);
 	}	
}
