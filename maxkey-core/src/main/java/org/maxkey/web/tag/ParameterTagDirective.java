package org.maxkey.web.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
    *   获取应用上下文标签
 *   <@parameter/>
 * @author Crystal.Sea
 *
 */

@FreemarkerTag("parameter")
public class ParameterTagDirective implements TemplateDirectiveModel {
	@Autowired
    private HttpServletRequest request;
	
	private String name;
	

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
			name=params.get("name").toString();
			env.getOut().append(request.getParameter(name));
	}

}
