package com.xmlrest.api.b2c.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api")
@Log4j2
public class XmlServiceController {

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/payload")
    public String getPayload() {
        log.info("/api/payload");
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><COMMAND>\n" +
                "    <TYPE>B2C</TYPE>\n" +
                "    <CUSTOMERMSISDN>786812555</CUSTOMERMSISDN>\n" +
                "    <MERCHANTMSISDN>000000794</MERCHANTMSISDN>\n" +
                "    <AMOUNT>10</AMOUNT>\n" +
                "    <PIN>1234</PIN>\n" +
                "    <REFERENCE>xxtest2</REFERENCE>\n" +
                "    <USERNAME>test</USERNAME>\n" +
                "    <PASSWORD>test@123</PASSWORD>\n" +
                "    <REFERENCE1>reference1</REFERENCE1>\n" +
                "</COMMAND>";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        httpHeaders.setCacheControl(CacheControl.noCache());

        HttpEntity<?> httpEntity = new HttpEntity<>(xmlString, httpHeaders);

        String url=  "https://172.23.115.140:7178/service/b2c";

        log.info("Headers"+httpEntity.getHeaders());
        log.info("Body"+httpEntity.getBody());

        ;
        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        log.info("Response code"+response.getStatusCode());
        log.info("Response Body"+response.getBody());
        return response.getBody();
    }



}
