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
 *   <@browser name=""></@browser>
 * @author Crystal.Sea
 *
 */

@FreemarkerTag("browser")
public class BrowserTagDirective implements TemplateDirectiveModel {
	@Autowired
    private HttpServletRequest request;
	
	@Override
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
