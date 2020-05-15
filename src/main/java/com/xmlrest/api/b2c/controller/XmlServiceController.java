package com.xmlrest.api.b2c.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
public class XmlServiceController {


    private final RestTemplate restTemplate;

    public XmlServiceController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(path = "/")
    public ResponseEntity<String> customerInformation() {

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

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        //Add the String Message converter
        messageConverters.add(new StringHttpMessageConverter());
        //Add the message converters to the restTemplate
        restTemplate.setMessageConverters(messageConverters);

        HttpHeaders headers = new HttpHeaders();
        headers.add("header_name", "header_value");
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> request = new HttpEntity<String>(xmlString, headers);

        log.info("Getting Response"+request.getBody());

        return restTemplate.postForEntity("https://172.23.115.140:7178/service/b2c", request, String.class);


    }
}
