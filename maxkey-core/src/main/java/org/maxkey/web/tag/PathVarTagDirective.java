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
 *   <@pathVar/>
 * @author Crystal.Sea
 *
 */

@FreemarkerTag("pathVar")
public class PathVarTagDirective implements TemplateDirectiveModel {
	@Autowired
    private HttpServletRequest request;
	
	private int index;
	String pathVariable;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		
		index=Integer.parseInt(params.get("index").toString());
		String[] pathVariables=request.getAttribute(org.springframework.web.util.WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE).toString().split("/");
		
		if(pathVariables==null){
			pathVariables=request.getRequestURI().split("/");
		}
		
		if(index==0){
			pathVariable=pathVariables[pathVariables.length-1];
		}else{
			pathVariable=pathVariables[index+1];
		}
			env.getOut().append(request.getParameter(pathVariable));
	}

}
