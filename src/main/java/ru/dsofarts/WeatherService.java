package ru.dsofarts;

import java.io.*;
import java.util.*;

public class WeatherService {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        Properties mavenProperties = new Properties();
        InputStream propertiesStream = WeatherService.class.getResourceAsStream("/maven.properties");
        mavenProperties.load(propertiesStream);

        final String API_KEY =  mavenProperties.getProperty("api.key");

        System.out.print("Enter city: ");
        String city = scanner.nextLine();

        try {
            JsonRequest jsonRequest = JsonRequest.jsonWeatherObject(API_KEY, city);
            System.out.println("Temp: " + jsonRequest.getTemp());
            System.out.println("Weather: " + jsonRequest.getWeather());
            System.out.println("Last update: " + jsonRequest.getLastUpdate());
        } catch (IOException e) {
            e.fillInStackTrace();
        }

    }

}
