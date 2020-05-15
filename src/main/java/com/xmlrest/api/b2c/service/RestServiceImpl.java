package com.xmlrest.api.b2c.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;

@Service
public class RestServiceImpl implements RestService_I {
    @Override
    public String post(String params) {

        try {
            JSONObject response = URLPost("https://172.23.115.140:7178/service/b2c",params );
            assert response != null;
            return response.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private JSONObject URLPost(String link, String params) throws JSONException {

        return isHttps(link) ? HttpsPost(link, params) : null;
    }

    private JSONObject HttpsPost(String link,String params) throws JSONException {
        try {
            StringBuilder sb;
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            URL url = new URL(link);
            HttpsURLConnection urlConn = (HttpsURLConnection) url.openConnection();
            urlConn.setSSLSocketFactory(sslsocketfactory);
            urlConn = this.setHttpsHeaders(urlConn, params);

                if (urlConn != null) {
                    try (InputStreamReader in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset())) {
                        sb = this.readInputStream(in);
                        return sb != null ? new JSONObject(sb.toString().trim()) : null;
                    }
                } else {
                    System.out.println("We have an empty (null) connection to the url");
                    return null;
                }

        } catch (IOException ioe) {
            System.out.println("We have an IO exception" + ioe.getMessage());
            return null;
        }
    }

    private HttpsURLConnection setHttpsHeaders(HttpsURLConnection urlConnection,String parameters) {
        try {

            urlConnection.setRequestProperty("Content-type", "application/xml");
            if (parameters != null) {
                urlConnection.setDoOutput(true);
                try (OutputStream outputStream = urlConnection.getOutputStream()) {
                    outputStream.write(parameters.getBytes());
                    outputStream.flush();
                }
            }

            return urlConnection;
        } catch (ProtocolException pe) {
            System.out.print("We have an error setting up headers for the connection" + pe.getMessage());
        } catch (IOException e) {
            System.out.print("We have an error" + e.getMessage());
        }
        return null;
    }

        private StringBuilder readInputStream(InputStreamReader in) {
        try (BufferedReader bufferedReader = new BufferedReader(in);) {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = bufferedReader.read()) != -1) {
                sb.append((char) cp);
            }
            bufferedReader.close();
            return sb;
        } catch (IOException ioe) {
            System.out.println("Response is null");
            return null;
        }
    }

    private boolean isHttps(String link) {
        return link.trim().substring(0, Math.min(link.length(), 5)).equalsIgnoreCase("HTTPS");
    }
}
