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
 *   <@dialog url="" title="" text="" width=500 height=100 />
 * @author Crystal.Sea
 *
 */

@FreemarkerTag("dialog")
public class DialogTagDirective implements TemplateDirectiveModel {
	@Autowired
    private HttpServletRequest request;
	
	private String url;
	
	private String title;
	
	private int width=300;
	
	private int height=400;
	
	private String text;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		
		url=params.get("url").toString();
		title=params.get("title").toString();
		text=params.get("text").toString();
		if(params.get("width")!=null) {
			width=Integer.parseInt(params.get("width").toString());
		}
		if(params.get("height")!=null) {
			height=Integer.parseInt(params.get("height").toString());
		}
		
		env.getOut().append("<input  class=\"window button\" type=\"button\"  value=\""+text+"\"  title=\""+title+"\" ");
		env.getOut().append("wurl=\""+request.getContextPath()+url+"\" wwidth=\""+width+"\"  wheight=\""+height+"\" />" );
		
	}

}
