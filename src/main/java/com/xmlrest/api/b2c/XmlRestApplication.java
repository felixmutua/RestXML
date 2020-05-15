package com.xmlrest.api.b2c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.util.Objects;

@SpringBootApplication
public class XmlRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(XmlRestApplication.class, args);


    }

}
