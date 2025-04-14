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

import org.dromara.maxkey.authn.jwt.AuthJwt;
import org.dromara.maxkey.authn.jwt.AuthRefreshTokenService;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/auth")
public class AuthTokenRefreshPoint {
	private static final  Logger _logger = LoggerFactory.getLogger(AuthTokenRefreshPoint.class);
	
	@Autowired
	AuthTokenService authTokenService;
	
	@Autowired
	AuthRefreshTokenService refreshTokenService;
	
	@Autowired
	SessionManager sessionManager;
	
	@GetMapping(value={"/token/refresh"})
	public ResponseEntity<?> refreshGet(HttpServletRequest request,
			@RequestParam(name = "refresh_token", required = false) String refreshToken) {
		return refresh(request,refreshToken);
	}
	
 	@PostMapping(value={"/token/refresh"})
	public ResponseEntity<?> refresh(HttpServletRequest request,
			@RequestParam(name = "refresh_token", required = false) String refreshToken) {
 		_logger.debug("try to refresh token " );
 		_logger.trace("refresh token {} " , refreshToken);
 		if(_logger.isTraceEnabled()) {WebContext.printRequest(request);}
 		try {
	 		if(refreshTokenService.validateJwtToken(refreshToken)) {
	 			String sessionId = refreshTokenService.resolveJWTID(refreshToken);
	 			_logger.trace("Try to  refresh sessionId [{}]" , sessionId);
		 		Session session = sessionManager.refresh(sessionId);
		 		if(session != null) {
		 			AuthJwt authJwt = authTokenService.genAuthJwt(session.getAuthentication());
		 			_logger.trace("Grant new token {}" , authJwt);
		 			return new Message<AuthJwt>(authJwt).buildResponse();
		 		}else {
		 			_logger.debug("Session is timeout , sessionId [{}]" , sessionId);
		 		}
	 		}else {
	 			_logger.debug("refresh token is not validate .");
	 		}
 		}catch(Exception e) {
 			_logger.error("Refresh Exception !",e);
 		}
 		return new ResponseEntity<>("Refresh Token Fail !", HttpStatus.UNAUTHORIZED);
 	}
}
