package org.openjfx.model.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.openjfx.Config;
import org.openjfx.model.Weather;

import java.io.IOException;
import java.net.MalformedURLException;
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
                cityData.longitude() + "&lang=pl&appid=" + apiKey +"&units=metric";
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(urlString);
            JsonNode apiResponse = objectMapper.readTree(url);

            String conditions = apiResponse.findValue("description").toString();
            conditions = replaceQuotes(conditions);
            conditions = capitalize(conditions);

            Image currentConditionsImage = getCurrentConditionsIcon(apiResponse.findValue("icon").toString());

            double tempInCelsius = apiResponse.findValue("temp").asDouble();

            long unixTimestamp = apiResponse.findValue("dt").asLong();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimestamp),
                    TimeZone.getDefault().toZoneId());

            return new Weather(cityName, conditions, tempInCelsius, localDateTime, currentConditionsImage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Image getCurrentConditionsIcon(String iconId) throws MalformedURLException {
        iconId = replaceQuotes(iconId);
        String urlString = "http://openweathermap.org/img/wn/" + iconId +"@2x.png";
        System.out.println(urlString);
        return new Image(urlString);
    }

    private CityData getCityData(String cityName) {
        String urlString = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + ",&limit=1&appid=" + apiKey;
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

    private record CityData(double latitude, double longitude) {

    }

    private String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    private String replaceQuotes(String stringWithQuotes){
        return stringWithQuotes.replace("\"","");
    }
}

