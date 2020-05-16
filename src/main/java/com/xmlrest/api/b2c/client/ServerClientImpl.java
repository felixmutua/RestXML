package com.xmlrest.api.b2c.client;

import lombok.extern.log4j.Log4j2;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Log4j2
@Service
public class ServerClientImpl implements ServerClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String callServer(String xmlPayload) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        httpHeaders.setCacheControl(CacheControl.noCache());


        HttpEntity<?> httpEntity = new HttpEntity<>(xmlPayload, httpHeaders);

        log.info("Headers" + httpEntity.getHeaders());

        ResponseEntity<String> response =
                restTemplate.exchange("https://172.23.115.140:7178/service/b2c", HttpMethod.POST, httpEntity, String.class);

        log.info("Response" + response.getBody());
        log.info("Code" + response.getStatusCode());
        return response.getBody();


    }

}


