package org.maxkey.web.tag;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
    *   获取应用上下文标签
 *   <@genId/>
 * @author Crystal.Sea
 *
 */

@FreemarkerTag("genId")
public class GenIdTagDirective implements TemplateDirectiveModel {
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		env.getOut().append(UUID.randomUUID().toString().toLowerCase());
		

	}

}
