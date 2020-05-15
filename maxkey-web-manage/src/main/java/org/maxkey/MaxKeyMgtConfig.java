package org.maxkey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/application.properties")
public class MaxKeyMgtConfig {
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyMgtConfig.class);
	@Value("${server.port:8080}")
    private int port;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	

}
