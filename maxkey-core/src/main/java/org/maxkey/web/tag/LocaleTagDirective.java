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

@FreemarkerTag("locale")
public class LocaleTagDirective implements TemplateDirectiveModel {
	@Autowired
    private HttpServletRequest request;
	

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
			WebApplicationContext  webApplicationContext =RequestContextUtils.findWebApplicationContext(request);
			if(params.get("code")==null) {
				env.getOut().append(RequestContextUtils.getLocale(request).getLanguage());
			}else {
				env.getOut().append(webApplicationContext.getMessage(params.get("code").toString(), null, RequestContextUtils.getLocale(request)));
			}
	}

}
