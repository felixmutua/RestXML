package com.xmlrest.api.b2c;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Configuration
public class SSLConfig {
    @PostConstruct
    private void configureSSL() {
        //set to TLSv1.1 or TLSv1.2
        System.setProperty("https.protocols", "TLSv1.2");

        System.setProperty("javax.net.ssl.trustStore", Objects.requireNonNull(SSLConfig.class.getClassLoader().getResource("clientkeystore.jks")).getFile());
        System.setProperty("javax.net.ssl.trustStorePassword","secret" );

    }
}