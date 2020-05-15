package com.xmlrest.api.b2c.controller;

import com.xmlrest.api.b2c.service.RestService_I;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class XmlServiceController {

    private final RestService_I restService_i;

    public XmlServiceController(RestService_I restService_i) {
        this.restService_i = restService_i;
    }

    @PostMapping("/payload")
    public String getPayload() {
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
        return restService_i.post(xmlString);
    }



}
