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
public class UnauthorizedEntryPoint {
	private static final Logger _logger = LoggerFactory.getLogger(UnauthorizedEntryPoint.class);
	
 	@RequestMapping(value={"/auth/entrypoint"})
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
