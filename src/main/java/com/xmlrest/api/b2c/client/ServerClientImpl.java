package com.xmlrest.api.b2c.client;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

@Log4j2
@Service
public class ServerClientImpl implements ServerClient {

    @Override
    public String callServer() {
        System.out.println("MagicDude4Eva 2-way / mutual SSL-authentication test");

        try {
            String KEY_ALIAS = "client", CERT_PASSWORD = "secret";

            KeyStore identityKeyStore = KeyStore.getInstance("jks");
            FileInputStream identityKeyStoreFile = new FileInputStream(new File("/home/ec2-user/certs/identity.jks"));
            identityKeyStore.load(identityKeyStoreFile, CERT_PASSWORD.toCharArray());

            KeyStore trustKeyStore = KeyStore.getInstance("jks");
            FileInputStream trustKeyStoreFile = new FileInputStream(new File("/home/ec2-user/truststore.jks"));
            trustKeyStore.load(trustKeyStoreFile, CERT_PASSWORD.toCharArray());

            SSLContext sslContext = SSLContexts.custom()
                    // load identity keystore
                    .loadKeyMaterial(identityKeyStore, CERT_PASSWORD.toCharArray(), (aliases, socket) -> {
                        return KEY_ALIAS; //client
                    })
                    // load trust keystore
                    .loadTrustMaterial(trustKeyStore, null)
                    .build();

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    new String[]{"TLSv1.2", "TLSv1.1"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());

            CloseableHttpClient client = HttpClients.custom()
                    .setSSLSocketFactory(sslConnectionSocketFactory)
                    .build();

            // Call a SSL-endpoint
            return  callEndPoint (client);
        } catch (Exception ex) {
            System.out.println("Boom, we failed: " + ex);
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    private static String callEndPoint(CloseableHttpClient aHTTPClient) {

        try {
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
            System.out.println("Calling URL: " + "https://172.23.115.140:7178/service/b2c");

            HttpPost post = new HttpPost("https://172.23.115.140:7178/service/b2c");
            post.setHeader("Accept", "application/xml");
            post.setHeader("Content-type", "application/xml");

            StringEntity entity = new StringEntity(xmlString);
            post.setEntity(entity);

            System.out.println("**POST** request Url: " + post.getURI());
            System.out.println("Parameters : " + xmlString);

            HttpResponse response = aHTTPClient.execute(post);

            int responseCode = response.getStatusLine().getStatusCode();
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Body : " + response.getEntity().getContent().toString());
            System.out.println("Content:-\n");
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            rd.close();
            return sb.toString();
        } catch (Exception ex) {
            System.out.println("Boom, we failed: " + ex);
            ex.printStackTrace();
            return ex.getMessage();
        }

    }

}
