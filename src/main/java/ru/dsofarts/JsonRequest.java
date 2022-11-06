package ru.dsofarts;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

public class JsonRequest {

    private final JsonObject jsonObject;
    private static final JsonParser jsonParser = new JsonParser();

    private JsonRequest(String jsonString) {
        this.jsonObject = (JsonObject) jsonParser.parse(jsonString);
    }

    /**
     * Create JSON object via HTTP request to api.weatherapi.com service
     * @param apiKey API key
     * @param city   City where you can check the weather
     * @return new JsonRequest
     */
    public static JsonRequest jsonWeatherObject(String apiKey, String city) throws IOException {

        StringBuilder c = new StringBuilder();
        city = city.replace(" ", "%20");

        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (SSLSocket socket = (SSLSocket) factory.createSocket("api.weatherapi.com", 443)) {
            socket.startHandshake();
            Writer writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write("GET /v1/current.json?q=" + city + "&aqi=no HTTP/1.1\r\n");
            writer.write("Host: api.weatherapi.com\r\n");
            writer.write("Key: " + apiKey + "\r\n");
            writer.write("\r\n");
            writer.flush();
            InputStream inputStream = socket.getInputStream();
            for (int b; (b = inputStream.read()) != -1;) {
                c.append((char) b);
                if (c.toString().endsWith("}}")) {
                    break;
                }
            }
        }

        // create new JsonRequest
        return new JsonRequest(c.substring(c.indexOf("{\"")));
    }

    /**
     * Get temperature
     * @return temperature
     */
    public String getTemp() {
        return jsonObject.getAsJsonObject("current").get("temp_c").getAsString() + " C";
    }

    /**
     * get weather
     * @return weather
     */
    public String getWeather() {
        return jsonObject.getAsJsonObject("current").getAsJsonObject("condition").get("text")
                .getAsString();
    }

    /**
     * get date and time of the last weather update
     *
     * @return date and time of the last weather update
     */
    public String getLastUpdate() {
        return jsonObject.getAsJsonObject("current").get("last_updated").getAsString();
    }
}
