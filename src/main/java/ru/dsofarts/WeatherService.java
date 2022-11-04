package ru.dsofarts;

import java.io.*;
import java.util.*;

public class WeatherService {

    public static void main(String[] args) throws IOException {

        Properties mavenProperties = new Properties();
        InputStream in = WeatherService.class.getResourceAsStream("/maven.properties");
        mavenProperties.load(in);

        String api = mavenProperties.getProperty("api.key");
        System.out.println(api);
    }

}
