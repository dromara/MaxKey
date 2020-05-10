package org.maxkey.web.tag;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * 获取主题标签 .<@theme/>
 * 
 * @author Crystal.Sea
 *
 */

@FreemarkerTag("theme")
public class ThemeTagDirective implements TemplateDirectiveModel {
    private static final Logger _logger = LoggerFactory.getLogger(ThemeTagDirective.class);
    @Autowired
    private HttpServletRequest request;

    @SuppressWarnings("rawtypes")
    @Override
    public void execute(Environment env, 
            Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String theme = null;
        if (null != WebContext.getUserInfo()) {
            theme =  WebContext.getUserInfo().getTheme();
            _logger.trace("read theme form login user session , theme is " + theme);
        }
        
        if (null == theme) {
            Cookie  themeCookie = 
                    WebContext.readCookieByName(request, WebConstants.THEME_COOKIE_NAME);
            if (themeCookie != null) {
                theme = themeCookie.getValue();
                _logger.trace("read theme form cookie , theme is " + theme);
            }
        }
        env.getOut().append(theme == null ? "default" : theme);
    }

}
