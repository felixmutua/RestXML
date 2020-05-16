package com.xmlrest.api.b2c.controller;

import com.xmlrest.api.b2c.client.ServerClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Log4j2
public class ClientController {

    @Autowired
    private ServerClient serverClient;

    @PostMapping("/payload")
    public String getPayload() {

        log.info("/api/payload");
        String serverResponse = serverClient.callServer();
        log.info("Server called - Response"+serverResponse);
        return String.format("Server called - Response '%s' ", serverResponse);


    }



}
