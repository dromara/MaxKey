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
 

package org.dromara.maxkey.web.tag;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import jakarta.servlet.http.HttpServletRequest;
/**
    *   获取应用上下文标签
 *   <@browser name=""></@browser>
 * @author Crystal.Sea
 *
 */

@FreemarkerTag("browser")
public class BrowserTagDirective implements TemplateDirectiveModel {
	@Autowired
    private HttpServletRequest request;
	
	@Override
	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String browser = params.get("name").toString();
		String userAgent = request.getHeader("User-Agent");
		env.getOut().append("<!--<div style='display:none'>"+userAgent+"</div>-->");
		
		if(userAgent.indexOf(browser)>0){
			body.render(env.getOut());
		}
	}

}
