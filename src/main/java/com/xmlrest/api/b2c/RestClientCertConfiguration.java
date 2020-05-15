package com.xmlrest.api.b2c;

import org.apache.commons.io.FileUtils;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;

@Configuration
public class RestClientCertConfiguration {

    private final char[] serverKeyPass= "secret".toCharArray();
    private final char[] clientPassword = "secret".toCharArray();

    @Value("classpath:serveridentity.jks")
    private Resource serverIdentity;

    @Value("classpath:clienttruststore.jks")
    private Resource clientStoreResource;

    @Bean
    public SSLContext serverSSLContext() throws Exception {
        return SSLContextBuilder
                .create()
                .loadKeyMaterial(inStream2File(serverIdentity), serverKeyPass, serverKeyPass)
                .loadTrustMaterial(inStream2File(clientStoreResource), clientPassword)
                .build();
    }
    private File inStream2File(Resource resource) {
        try {
            File tempFile = File.createTempFile("file", ".tmp");
            tempFile.deleteOnExit();
            FileUtils.copyInputStreamToFile(resource.getInputStream(), tempFile);
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException("Problems loading Keystores", e);
        }
    }
}
