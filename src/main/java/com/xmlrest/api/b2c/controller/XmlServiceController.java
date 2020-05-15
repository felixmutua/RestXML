package com.xmlrest.api.b2c.controller;

import com.xmlrest.api.b2c.client.ServerClient;
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
    private ServerClient serverClient;

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

        String serverResponse = serverClient.callServer(xmlString);
        return String.format("Server called - Response '%s' ", serverResponse);


    }



}
