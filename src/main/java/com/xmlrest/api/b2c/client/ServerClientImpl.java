package com.xmlrest.api.b2c.client;

import lombok.extern.log4j.Log4j2;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Component
@Log4j2
public class ServerClientImpl implements ServerClient {


    @Autowired
    private SSLContext serverSSLContext;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String callServer(String xmlPayload) {
        restTemplate.setRequestFactory(createHttpComponentsClientHttpRequestFactory(serverSSLContext));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        httpHeaders.setCacheControl(CacheControl.noCache());

        HttpEntity<?> httpEntity = new HttpEntity<>(xmlPayload, httpHeaders);

        log.info("Headers"+httpEntity.getHeaders());
        log.info("Body"+httpEntity.getBody());

        return restTemplate.getForObject("https://172.23.115.140:7178/service/b2c", String.class);
    }


    private HttpComponentsClientHttpRequestFactory createHttpComponentsClientHttpRequestFactory(SSLContext sslContext) {
        HttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .build();

        return new HttpComponentsClientHttpRequestFactory(client);
    }
}
