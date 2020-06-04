package org.maxkey.constants;

import org.junit.Test;

public class ConstantsProperties {

    public static final String applicationPropertySource = 
            "classpath:/application.properties";
    
    public static final String maxKeyPropertySource      = 
            "classpath:/maxkey.properties";
    
    public static final String kaptchaPropertySource      = 
            "classpath:/kaptcha.properties";
    
    public static String classPathResource(String propertySource) {
        return propertySource.replaceAll("classpath:","");
    }
    
    @Test
    public void classPathResourceTest() {
        System.out.println(classPathResource(maxKeyPropertySource));
    }
}
