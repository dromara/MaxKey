package net.maxsso.cas.demo;

import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCasClient
public class CasClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CasClientDemoApplication.class, args);
    }

}
