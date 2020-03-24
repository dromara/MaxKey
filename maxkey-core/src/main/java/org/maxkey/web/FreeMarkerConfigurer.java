package org.maxkey.web;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.maxkey.web.tag.FreemarkerTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Component
public class FreeMarkerConfigurer  implements ApplicationContextAware {
	private static final Logger _logger = LoggerFactory.getLogger(FreeMarkerConfigurer.class);
	ApplicationContext applicationContext ;
	
	@Autowired 
	Configuration configuration; 

 
	@PostConstruct // 在项目启动时执行方法
	public void setSharedVariable() throws IOException, TemplateException {
		// 根据注解FreemarkerTag获取bean ,key is bean name ,value is bean object
		Map<String, Object> map = this.applicationContext.getBeansWithAnnotation(FreemarkerTag.class);
		for (String key : map.keySet()) {
			configuration.setSharedVariable(key, map.get(key));
			_logger.debug("FreeMarker Template "+key);
		}

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}

}
