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
 

package org.dromara.maxkey.authz.exapi.endpoint;


import org.dromara.maxkey.authz.exapi.endpoint.adapter.netease.NeteaseRSATool;
import org.dromara.maxkey.pretty.impl.JsonPretty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "2-8-ExtendApi接口文档模块-元数据")
@Controller
public class ExtendApiMetadata {
	static final  Logger _logger = LoggerFactory.getLogger(ExtendApiMetadata.class);
	
	@Operation(summary = "netease qiye mail RSA Key", description = "网易企业邮箱RSA Key生成器",method="GET")
	@RequestMapping(
			value = "/metadata/netease/qiye/mail/rsa/gen",
			method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String  metadata(HttpServletRequest request,HttpServletResponse response) {
		NeteaseRSATool neteaseRSATool =new NeteaseRSATool();
		neteaseRSATool.genRSAKeyPair();
		return JsonPretty.getInstance().formatln(neteaseRSATool);
	}
}
