package org.maxkey;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations={"classpath:spring/maxkey.xml"})
public class MaxKeyConfig {

}
