package com.xmlrest.api.b2c.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
public class ServerClientImpl implements ServerClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String callServer(String xmlPayload) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        httpHeaders.setCacheControl(CacheControl.noCache());

        HttpEntity<?> httpEntity = new HttpEntity<>(xmlPayload, httpHeaders);

        log.info("Headers"+httpEntity.getHeaders());
        log.info("Body"+httpEntity.getBody());

        return restTemplate.getForObject("https://172.23.115.140:7178/service/b2c", String.class);
    }

}
