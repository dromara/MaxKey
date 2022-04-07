package org.maxkey.authn.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AuthEntryPoint {
	private static final Logger _logger = LoggerFactory.getLogger(AuthEntryPoint.class);
	
 	@RequestMapping(value={"/auth/entrypoint"})
	public void entryPoint(
			HttpServletRequest request, HttpServletResponse response) 
					throws StreamWriteException, DatabindException, IOException {
 		_logger.trace("AuthEntryPoint /entrypoint.");
 		 response.setContentType(MediaType.APPLICATION_JSON_VALUE);
 	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

 	    final Map<String, Object> body = new HashMap<>();
 	    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
 	    body.put("error", "Unauthorized");
 	    body.put("message", "Unauthorized");
 	    body.put("path", request.getServletPath());

 	    final ObjectMapper mapper = new ObjectMapper();
 	    mapper.writeValue(response.getOutputStream(), body);
 	}	
}
