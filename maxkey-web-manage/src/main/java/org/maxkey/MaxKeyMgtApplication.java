package org.maxkey;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations={"classpath:spring/maxkey-mgt.xml"})
@ComponentScan(basePackages = {
		"org.maxkey.MaxKeyConfig"
	}
)
public class MaxKeyMgtApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MaxKeyMgtApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MaxKeyMgtApplication.class);
	}

}
