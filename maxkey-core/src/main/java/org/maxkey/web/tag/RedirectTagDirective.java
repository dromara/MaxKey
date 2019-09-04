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
