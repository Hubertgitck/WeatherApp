package org.openjfx.model.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import org.openjfx.Config;
import org.openjfx.model.Weather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.*;
import java.util.*;

import static java.lang.Math.floor;

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

            Image conditionsIcon = getConditionsIcon(apiResponse.findValue("icon").toString());
            double tempInCelsius = apiResponse.findValue("temp").asDouble();
            String dateTimeString = String.valueOf(apiResponse.findValue("dt_txt"));
            String dayOfTheWeek = getDayFromUnixTimestamp(apiResponse.findValue("dt").asLong());

            return new Weather(cityName, conditions, tempInCelsius, dateTimeString, conditionsIcon, dayOfTheWeek);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Image getConditionsIcon(String iconId) throws MalformedURLException {
        iconId = replaceQuotes(iconId);
        String urlString = "http://openweathermap.org/img/wn/" + iconId +"@2x.png";
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

     public ArrayList<Weather> getForecast(String cityName){
        ArrayList<Weather> forecast = new ArrayList<Weather>();
        CityData cityData = getCityData(cityName);

        String urlString = "https://api.openweathermap.org/data/2.5/forecast?lat=" + cityData.latitude() + "&lon=" +
                cityData.longitude() + "&lang=pl&appid=" + apiKey +"&units=metric";
        try {
            URL url = new URL(urlString);
            ObjectMapper objectMapper = new ObjectMapper();
            Response response = objectMapper.readValue(url, Response.class);

            LocalTime noon = LocalTime.NOON;
            LocalDate today = LocalDate.now();

            LocalDateTime todayNoon = LocalDateTime.of(today, noon);
            long nextDayNoonEpochSeconds = todayNoon.plusDays(1).toEpochSecond(ZoneOffset.UTC);

            //Get forecast for next 5 days
            for (int i = 0; i <= 5; i++){
                long finalNextDayNoonEpochSeconds = nextDayNoonEpochSeconds;
                response.getList().forEach(e -> {
                    if (e.getDt() == finalNextDayNoonEpochSeconds) {
                        try {
                            String description = e.getWeather().get(0).getDescription();
                            double temp = e.getMain().get("temp").asDouble();
                            String dateFormatted = e.dtTXT;
                            Image conditionsIcon = getConditionsIcon(e.getWeather().get(0).getIcon());
                            String dayOfTheWeek = getDayFromUnixTimestamp(finalNextDayNoonEpochSeconds);
                            Weather weather = new Weather(cityName, description, temp, dateFormatted,conditionsIcon,dayOfTheWeek);
                            forecast.add(weather);
                        } catch (MalformedURLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                nextDayNoonEpochSeconds = nextDayNoonEpochSeconds + 86400;
            }
            return forecast;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private record CityData(double latitude, double longitude) {

    }
    private String getDayFromUnixTimestamp(long epochSeconds){
        int dayOfTheWeek = (int) (((epochSeconds / 86400) + 4) %7);
        return switch (dayOfTheWeek){
            case 0 -> "Sun";
            case 1 -> "Mon";
            case 2 -> "Tue";
            case 3 -> "Wed";
            case 4 -> "Thu";
            case 5 -> "Fri";
            case 6 -> "Say";
            default -> throw new IllegalStateException("Unexpected value: " + dayOfTheWeek);
        };
    }
    private String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    private String replaceQuotes(String stringWithQuotes){
        return stringWithQuotes.replace("\"","");
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static
    class Response {
        List<WeatherPOJO> list;

        public List<WeatherPOJO> getList() {
            return list;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static
    class WeatherPOJO {
        JsonNode main;
        List<WeatherApiData> weather;
        long dt;
        @JsonProperty("dt_txt")
        String dtTXT;

        public JsonNode getMain() {
            return main;
        }
        public long getDt(){
            return dt;
        }

        public String getDtTXT() {
            return dtTXT;
        }

        public List<WeatherApiData> getWeather() {
            return weather;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static
    class WeatherApiData{
        String description;
        String icon;

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }
}

