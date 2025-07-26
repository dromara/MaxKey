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
 

package org.dromara.maxkey.web;

import java.util.Map;


import org.dromara.maxkey.web.tag.FreemarkerTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.annotation.PostConstruct;

@Component
public class ConfigurerFreeMarker  implements ApplicationContextAware {
	private static final Logger _logger = LoggerFactory.getLogger(ConfigurerFreeMarker.class);
	
	ApplicationContext applicationContext ;
	
	@Autowired 
	Configuration configuration; 
 
	@PostConstruct // 在项目启动时执行方法
	public void setSharedVariable() throws TemplateException {
		// 根据注解FreemarkerTag获取bean ,key is bean name ,value is bean object
		Map<String, Object> map = this.applicationContext.getBeansWithAnnotation(FreemarkerTag.class);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			configuration.setSharedVariable(entry.getKey(), entry.getValue());
			_logger.trace("FreeMarker Template {}" , entry.getKey());
		}

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}

}
