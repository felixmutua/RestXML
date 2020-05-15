package com.xmlrest.api.b2c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class XmlRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmlRestApplication.class, args);

        System.setProperty("javax.net.ssl.trustStoreType", "jks");
        System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
