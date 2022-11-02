package org.openjfx.model.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjfx.Config;

import java.io.IOException;
import java.net.URL;

public class OpenWeatherApi {

    private String apiKey = Config.apiKey;
    private String countryCode = "pl";
    public CityData getCoordinates(String cityName) {
        String urlString = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "," +countryCode +"&limit=1&appid=" + apiKey;

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(urlString);
            JsonNode apiResponse = objectMapper.readTree(url);

            double latitude = apiResponse.findValue("lat").asDouble();
            double longitude = apiResponse.findValue("lon").asDouble();

            return new CityData(latitude,longitude);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
