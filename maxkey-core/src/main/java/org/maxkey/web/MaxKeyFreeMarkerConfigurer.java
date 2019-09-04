package org.maxkey.web;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.maxkey.web.tag.BaseTagDirective;
import org.maxkey.web.tag.FreemarkerTag;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Component
public class MaxKeyFreeMarkerConfigurer  implements ApplicationContextAware {
	
	ApplicationContext applicationContext ;
	
	@Autowired 
	Configuration configuration; 
 
	@Autowired
	BaseTagDirective baseTagDirective;
 
	@PostConstruct // 在项目启动时执行方法
	public void setSharedVariable() throws IOException, TemplateException {
		// configuration.setSharedVariable("base", baseTagDirective);
		// 根据注解FreemarkerTag获取bean ,key is bean name ,value is bean object
		Map<String, Object> map = this.applicationContext.getBeansWithAnnotation(FreemarkerTag.class);
		for (String key : map.keySet()) {
			configuration.setSharedVariable(key, map.get(key));
		}

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}

}
