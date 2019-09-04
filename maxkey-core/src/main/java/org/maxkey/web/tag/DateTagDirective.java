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
 *   <@date format="" value=""></@date>
 * @author Crystal.Sea
 *
 */

@FreemarkerTag("date")
public class DateTagDirective implements TemplateDirectiveModel {
	@Autowired
    private HttpServletRequest request;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String dateValue = params.get("value").toString();
		String format = params.get("format").toString();
		String dateString="";
		if(dateValue==null) {
			if(format==null) {
				dateString=DateUtils.getCurrentDateAsString(DateUtils.FORMAT_DATE_YYYY_MM_DD);
			}else {
				dateString=DateUtils.getCurrentDateAsString(format);
			}
		}else {
			if(format==null) {
				dateString=DateUtils.format(DateUtils.tryParse(dateValue),DateUtils.FORMAT_DATE_YYYY_MM_DD);
			}else {
				dateString=DateUtils.format(DateUtils.tryParse(dateValue),format);
			}
		}
		
		env.getOut().append(dateString);
		
	}

}
