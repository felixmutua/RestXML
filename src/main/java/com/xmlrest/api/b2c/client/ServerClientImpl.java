package com.xmlrest.api.b2c.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
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

        ResponseEntity<String> response =
                restTemplate.exchange("https://172.23.115.140:7178/service/b2c", HttpMethod.POST, httpEntity, String.class);

        log.info("Response"+response.getBody());
        log.info("Code"+response.getStatusCode());
        return response.getBody();


    }

}
