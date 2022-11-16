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

public class OpenWeatherClient implements WeatherClient {
    private final String API_KEY = Config.API_KEY;
    private record CityData(String cityName, double latitude, double longitude) {}

    @Override
    public Weather getWeather(String cityName) {

        CityData cityData = getCityData(cityName);

        if (cityData.latitude != 0){
            String urlString = "https://api.openweathermap.org/data/2.5/weather?lat=" + cityData.latitude() + "&lon=" +
                    cityData.longitude() + "&lang=pl&appid=" + API_KEY +"&units=metric";

            try{
                ObjectMapper objectMapper = new ObjectMapper();
                URL url = new URL(urlString);
                JsonNode apiResponse = objectMapper.readTree(url);

                String conditions = apiResponse.findValue("description").toString();
                conditions = removeQuotes(conditions);
                conditions = capitalize(conditions);

                Image conditionsIcon = getConditionsIcon(apiResponse.findValue("icon").toString());
                int tempInCelsius = apiResponse.findValue("temp").asInt();
                String dateTimeString = String.valueOf(apiResponse.findValue("dt_txt"));
                String dayOfTheWeek = getDayFromUnixTimestamp(apiResponse.findValue("dt").asLong());
                return new Weather(cityData.cityName , conditions, tempInCelsius, dateTimeString, conditionsIcon, dayOfTheWeek);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new Weather("Nieznane miasto", "Wprowadź prawidłową nazwę miasta",0,"0",null,"0");
        }
    }

    private Image getConditionsIcon(String iconId) throws MalformedURLException {
        iconId = removeQuotes(iconId);
        String urlString = "http://openweathermap.org/img/wn/" + iconId +"@2x.png";
        return new Image(urlString);
    }

    private CityData getCityData(String cityName) {
        String urlString = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + ",&limit=1&appid=" + API_KEY;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(urlString);
            JsonNode apiResponse = objectMapper.readTree(url);

            if (!apiResponse.isEmpty()){
                String name = apiResponse.findValue("name").toString();
                name = removeQuotes(name);
                double latitude = apiResponse.findValue("lat").asDouble();
                double longitude = apiResponse.findValue("lon").asDouble();
                return new CityData(name,latitude, longitude);
            } else {
                return new CityData("",0,0);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

     public ArrayList<Weather> getForecast(String cityName){
        ArrayList<Weather> forecast = new ArrayList<>();
        CityData cityData = getCityData(cityName);

         if (cityData.latitude != 0 && cityData.longitude != 0) {
             String urlString = "https://api.openweathermap.org/data/2.5/forecast?lat=" + cityData.latitude() + "&lon=" +
                     cityData.longitude() + "&lang=pl&appid=" + API_KEY + "&units=metric";
             try {
                 URL url = new URL(urlString);
                 ObjectMapper objectMapper = new ObjectMapper();
                 Response response = objectMapper.readValue(url, Response.class);

                 LocalTime noon = LocalTime.NOON;
                 LocalDate today = LocalDate.now();

                 LocalDateTime todayNoon = LocalDateTime.of(today, noon);
                 long nextDayNoonEpochSeconds = todayNoon.plusDays(1).toEpochSecond(ZoneOffset.UTC);

                 //Get forecast for next 5 days
                 for (int i = 0; i <= 5; i++) {
                     long finalNextDayNoonEpochSeconds = nextDayNoonEpochSeconds;
                     response.getList().forEach(e -> {
                         if (e.getDt() == finalNextDayNoonEpochSeconds) {
                             try {
                                 String description = e.getWeather().get(0).getDescription();
                                 int temp = e.getMain().get("temp").asInt();
                                 String dateFormatted = e.getDtTXT();
                                 Image conditionsIcon = getConditionsIcon(e.getWeather().get(0).getIcon());
                                 String dayOfTheWeek = getDayFromUnixTimestamp(finalNextDayNoonEpochSeconds);
                                 Weather weather = new Weather(cityData.cityName, description, temp, dateFormatted, conditionsIcon, dayOfTheWeek);
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
         } else {
             Weather weather = new Weather("błąd", "błąd", 0, "błąd", null, "0");
             forecast.add(weather);
             return forecast;
         }
    }

    private String getDayFromUnixTimestamp(long epochSeconds){
        int dayOfTheWeek = (int) (((epochSeconds / 86400) + 4) %7);
        return switch (dayOfTheWeek){
            case 0 -> "Niedz";
            case 1 -> "Pon";
            case 2 -> "Wt";
            case 3 -> "Śr";
            case 4 -> "Czw";
            case 5 -> "Pt";
            case 6 -> "Sob";
            default -> throw new IllegalStateException("Unexpected value: " + dayOfTheWeek);
        };
    }
    private String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    private String removeQuotes(String stringWithQuotes){
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

