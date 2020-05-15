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


    private final static String KEYSTORE_PASSWORD = "Admin123#@!";

    static {
        System.setProperty("javax.net.ssl.trustStore", Objects.requireNonNull(XmlRestApplication.class.getClassLoader().getResource("keystore.p12")).getFile());
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASSWORD);
        System.setProperty("javax.net.ssl.keyStore", Objects.requireNonNull(XmlRestApplication.class.getClassLoader().getResource("keystore.p12")).getFile());
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);

        HttpsURLConnection.setDefaultHostnameVerifier(
                new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession sslSession) {
                        return hostname.equals("localhost");
                    }
                });
    }
    public static void main(String[] args) {
        SpringApplication.run(XmlRestApplication.class, args);


    }

}
