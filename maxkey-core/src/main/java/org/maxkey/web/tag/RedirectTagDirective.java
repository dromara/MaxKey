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
 

package org.maxkey.web.tag;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
    *   获取应用上下文标签
 *   <@locale/>
 * @author Crystal.Sea
 *
 */

@FreemarkerTag("redirect")
public class RedirectTagDirective implements TemplateDirectiveModel {
	@Autowired
    private HttpServletRequest request;
	private HttpServletResponse response;
	
	private String basePath = null;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
			String location=params.get("url").toString();

			basePath = request.getScheme()+"://"+request.getServerName();
			int port=request.getServerPort();
			//Ignore 443 or 80 port
			if((port==443 && request.getScheme().equalsIgnoreCase("https"))
					||(port==80 && request.getScheme().equalsIgnoreCase("http"))){
			}else{
				basePath	+=	":"+port;
			}
			basePath += request.getContextPath()+"";
			
			response.sendRedirect(basePath+"/"+location);
	}

}
