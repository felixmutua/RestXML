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
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.Duration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder)
            throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, KeyManagementException {

        KeyStore trustStore= KeyStore.getInstance(KeyStore.getDefaultType());

        FileInputStream stream = new FileInputStream("/home/ec2-user/certs/cert.p12");
        trustStore.load(stream, "1234567890".toCharArray());

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