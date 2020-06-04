package org.maxkey.autoconfigure;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import java.io.IOException;
import java.util.Properties;
import org.maxkey.constants.ConstantsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


@Configuration
public class KaptchaAutoConfiguration  implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(KaptchaAutoConfiguration.class);
    
    /**
     * Captcha Producer  Config .
     * @return Producer
     * @throws IOException kaptcha.properties is null
     */
    @Bean (name = "captchaProducer")
    public Producer captchaProducer() throws IOException {
        Resource resource = new ClassPathResource(
                ConstantsProperties.classPathResource(ConstantsProperties.kaptchaPropertySource));
        _logger.debug("Kaptcha config file " + resource.getURL());
        DefaultKaptcha  kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.load(resource.getInputStream());
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
