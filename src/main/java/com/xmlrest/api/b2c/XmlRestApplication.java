package com.xmlrest.api.b2c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.HttpsURLConnection;
import java.util.Objects;


@SpringBootApplication
public class XmlRestApplication {

    private final static String KEYSTORE_PASSWORD = "secret";

    static {
        System.setProperty("javax.net.ssl.trustStore", Objects.requireNonNull(XmlRestApplication.class.getClassLoader().getResource("ca-truststore.jks")).getFile());
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASSWORD);
        System.setProperty("javax.net.ssl.keyStore", Objects.requireNonNull(XmlRestApplication.class.getClassLoader().getResource("ca-truststore.jks")).getFile());
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);

        HttpsURLConnection.setDefaultHostnameVerifier(
                (hostname, sslSession) -> hostname.equals("localhost"));
    }
    public static void main (String[] args)  {

        SpringApplication.run(XmlRestApplication.class, args);


    }

}
