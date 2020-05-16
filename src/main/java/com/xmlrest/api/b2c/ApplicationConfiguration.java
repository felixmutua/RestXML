package com.xmlrest.api.b2c;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.Objects;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder)
            throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, IOException, CertificateException {

        KeyStore trustStore= KeyStore.getInstance(KeyStore.getDefaultType());
        File file = new File(Objects.requireNonNull(XmlRestApplication.class.getClassLoader().getResource("ca-truststore.jks")).getFile());
        FileInputStream stream = new FileInputStream(file);
        trustStore.load(stream, "secret".toCharArray());

        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                .build();


        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslcontext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        HttpComponentsClientHttpRequestFactory customRequestFactory = new HttpComponentsClientHttpRequestFactory();
        customRequestFactory.setHttpClient(httpClient);

        return restTemplateBuilder
                .requestFactory(() -> customRequestFactory)
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
    }
}