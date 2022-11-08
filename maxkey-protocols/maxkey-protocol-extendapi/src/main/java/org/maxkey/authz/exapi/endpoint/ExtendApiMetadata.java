package org.maxkey.authz.exapi.endpoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authz.exapi.endpoint.adapter.netease.NeteaseRSATool;
import org.maxkey.pretty.impl.JsonPretty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "2-8-ExtendApi接口文档模块-元数据")
@Controller
public class ExtendApiMetadata {
	final static Logger _logger = LoggerFactory.getLogger(ExtendApiMetadata.class);
	
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
