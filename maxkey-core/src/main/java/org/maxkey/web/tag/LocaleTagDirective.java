package org.maxkey.web.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.context.WebApplicationContext;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 获取应用上下文标签 <@locale/>
 * 
 * @author Crystal.Sea
 *
 */

@FreemarkerTag("locale")
public class LocaleTagDirective implements TemplateDirectiveModel {
    private static final Logger _logger = LoggerFactory.getLogger(LocaleTagDirective.class);
    @Autowired
    private HttpServletRequest request;

    @Override
    public void execute(Environment env, 
            Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        WebApplicationContext webApplicationContext = 
                RequestContextUtils.findWebApplicationContext(request);
        String message = "";
        if (params.get("code") == null) {
            message = RequestContextUtils.getLocale(request).getLanguage();
        } else if (params.get("code").toString().equals("global.application.version")
                || params.get("code").toString().equals("application.version")) {
            message = WebContext.properties.getProperty("application.formatted-version");
        } else {
            _logger.trace("message code " + params.get("code"));
            try {
                message = webApplicationContext.getMessage(
                                params.get("code").toString(), 
                                null,
                                RequestContextUtils.getLocale(request));

            } catch (Exception e) {
                _logger.error("message code " + params.get("code"), e);
            }
        }
        env.getOut().append(message);
    }

}
