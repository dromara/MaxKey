package org.maxkey.crypto.password.opt.impl;

import java.io.IOException;
import java.util.Properties;
import org.maxkey.constants.ConstantsProperties;
import org.maxkey.crypto.password.opt.AbstractOptAuthn;
import org.maxkey.domain.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class SmsOtpAuthn extends AbstractOptAuthn {
    private static final  Logger logger = LoggerFactory.getLogger(SmsOtpAuthn.class);
    
    protected Properties properties;
    
    
    @Override
    public boolean produce(UserInfo userInfo) {
        String token = this.genToken(userInfo);
        // TODO:You must add send sms code here
        logger.debug("send sms code" + token);
        return true;
    }

    @Override
    public boolean validate(UserInfo userInfo, String token) {
        return true;
    }
    
    protected void loadProperties() throws IOException {
        Resource resource = new ClassPathResource(
                ConstantsProperties.classPathResource(
                        ConstantsProperties.classPathResource(
                                ConstantsProperties.maxKeyPropertySource)));
        properties = new Properties();
        properties.load(resource.getInputStream());
    }
    
    public void initPropertys() {
        
    }

}
