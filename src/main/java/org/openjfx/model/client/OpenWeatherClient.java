package org.openjfx.model.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjfx.Config;
import org.openjfx.model.Weather;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class OpenWeatherClient implements WeatherClient {


    private final String apiKey = Config.apiKey;

    @Override
    public Weather getWeather(String cityName) {

        CityData cityData = getCityData(cityName);

        String urlString = "https://api.openweathermap.org/data/2.5/weather?lat=" + cityData.latitude() + "&lon=" +
                cityData.longitude() + "&appid=" + apiKey;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(urlString);
            JsonNode apiResponse = objectMapper.readTree(url);

            double tempInCelsius = fahrenheitToCelsius(apiResponse.findValue("temp").asDouble());
            long unixTimestamp = apiResponse.findValue("dt").asLong();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimestamp),
                    TimeZone.getDefault().toZoneId());

            return new Weather(cityName,tempInCelsius, localDateTime);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CityData getCityData(String cityName) {
        String urlString = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + ","+"&limit=1&appid=" + apiKey;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(urlString);
            JsonNode apiResponse = objectMapper.readTree(url);

            double latitude = apiResponse.findValue("lat").asDouble();
            double longitude = apiResponse.findValue("lon").asDouble();

            return new CityData(latitude, longitude);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private double fahrenheitToCelsius(double tempInFahrenheit){
        return (tempInFahrenheit - 32) * 0.5556;
    }

    private record CityData(double latitude, double longitude) {

    }

}

