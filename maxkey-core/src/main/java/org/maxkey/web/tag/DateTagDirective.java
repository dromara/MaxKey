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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
    *   获取应用上下文标签
 *   <@date format="" value=""></@date>
 * @author Crystal.Sea
 *
 */

@FreemarkerTag("date")
public class DateTagDirective implements TemplateDirectiveModel {
	@Autowired
    private HttpServletRequest request;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String dateValue = params.get("value").toString();
		String format = params.get("format").toString();
		String dateString="";
		if(dateValue==null) {
			if(format==null) {
				dateString=DateUtils.getCurrentDateAsString(DateUtils.FORMAT_DATE_YYYY_MM_DD);
			}else {
				dateString=DateUtils.getCurrentDateAsString(format);
			}
		}else {
			if(format==null) {
				dateString=DateUtils.format(DateUtils.tryParse(dateValue),DateUtils.FORMAT_DATE_YYYY_MM_DD);
			}else {
				dateString=DateUtils.format(DateUtils.tryParse(dateValue),format);
			}
		}
		
		env.getOut().append(dateString);
		
	}

}
