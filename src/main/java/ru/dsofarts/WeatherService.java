package ru.dsofarts;

import java.io.*;
import java.util.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class WeatherService {

    public static void main(String[] args) throws IOException {

        Properties mavenProperties = new Properties();
        InputStream propertiesStream = WeatherService.class.getResourceAsStream("/maven.properties");
        mavenProperties.load(propertiesStream);

        final String API_KEY =  mavenProperties.getProperty("api.key");

        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (SSLSocket socket = (SSLSocket)factory.createSocket("api.weatherapi.com", 443)) {
            socket.startHandshake();
            Writer w = new OutputStreamWriter(socket.getOutputStream());
            w.write("GET /v1/current.json?q=London HTTP/1.1\r\n");
            w.write("Host: api.weatherapi.com\r\n");
            w.write("Key: " + API_KEY + "\r\n");
            w.write("\r\n");
            w.flush();
            InputStream in = socket.getInputStream();
            int b;
            while ((b = in.read()) != -1)
                System.out.write(b);
        }
    }
}
