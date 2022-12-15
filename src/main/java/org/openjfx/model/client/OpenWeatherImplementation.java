package org.openjfx.model.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import org.openjfx.Config;
import org.openjfx.model.Weather;
import org.openjfx.model.httpClient.OkHttp;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class OpenWeatherImplementation {

    private static final int NUMBER_OF_DAYS_IN_FORECAST = 5;
    private final String API_KEY = Config.API_KEY;
    private final ObjectMapper objectMapper;
    private final OkHttp httpClient;

    private record CityData(String cityName, double latitude, double longitude) {}

    public OpenWeatherImplementation() {
        this.objectMapper = new ObjectMapper();
        this.httpClient = new OkHttp();
    }

    public Weather getWeather(String cityName) {

        CityData cityData = getCityData(cityName);
        Weather weather;

        if (isCityDataCorrect(cityData)){
            weather = getWeatherData(cityData);
        } else {
            weather = new Weather("Nieznane miasto", "Wprowadź prawidłową nazwę miasta",0,"0",null,"0");
        }
        return weather;
    }

    public List<Weather> getForecast(String cityName){

        List<Weather> forecast = new ArrayList<>();
        CityData cityData = getCityData(cityName);

        if (isCityDataCorrect(cityData)) {
            forecast = getForecastData(cityData);
        } else {
            Weather weather = new Weather("błąd", "nieznane miasto", 0, "błąd", null, "0");
            forecast.add(weather);
        }
        return forecast;
    }

    private Weather getWeatherData (CityData cityData) {

        String urlString = "https://api.openweathermap.org/data/2.5/weather?lat=" + cityData.latitude() + "&lon=" +
                cityData.longitude() + "&lang=pl&appid=" + API_KEY +"&units=metric";
        String responseJsonAsString;

        responseJsonAsString = httpClient.getResponseFromApiAsJSONString(urlString);

        WeatherPOJO weatherPOJO;
        try {
            weatherPOJO = objectMapper.readValue(responseJsonAsString, WeatherPOJO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse response JSON", e);
        }

        return new Weather(cityData.cityName,
                capitalize(weatherPOJO.getConditionsDescription()),
                weatherPOJO.main.getTemperature(),
                weatherPOJO.getDateAsText(),
                getConditionsIcon(weatherPOJO.getWeather().get(0).getIcon()),
                getDayFromUnixTimestamp(weatherPOJO.getDateAsEpochSeconds())
        );
    }

    public List<Weather> getForecastData (CityData cityData) {

        List<Weather> forecast = new ArrayList<>();
        ForecastResponse response;
        String urlString = "https://api.openweathermap.org/data/2.5/forecast?lat=" + cityData.latitude() + "&lon=" +
                cityData.longitude() + "&lang=pl&appid=" + API_KEY + "&units=metric";

        String responseJsonAsString = httpClient.getResponseFromApiAsJSONString(urlString);

        try {
            response = objectMapper.readValue(responseJsonAsString, ForecastResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LocalTime noon = LocalTime.NOON;
        LocalDate today = LocalDate.now();

        LocalDateTime todayNoon = LocalDateTime.of(today, noon);
        long nextDayNoonEpochSeconds = todayNoon.plusDays(1).toEpochSecond(ZoneOffset.UTC);

        for (int i = 0; i <= NUMBER_OF_DAYS_IN_FORECAST; i++) {
            long finalNextDayNoonEpochSeconds = nextDayNoonEpochSeconds;
            response.getList().forEach(element -> {
                Weather weather;
                if (element.getDateAsEpochSeconds() == finalNextDayNoonEpochSeconds) {
                    weather = new Weather(cityData.cityName,
                            element.getConditionsDescription(),
                            element.main.getTemperature(),
                            element.getDateAsText(),
                            getConditionsIcon(element.getWeather().get(0).getIcon()),
                            getDayFromUnixTimestamp(element.getDateAsEpochSeconds())
                    );
                    forecast.add(weather);
                }
            });
            nextDayNoonEpochSeconds = nextDayNoonEpochSeconds + 86400;
        }
        return forecast;
    }

    private CityData getCityData(String cityName) {

        String urlString = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + ",&limit=1&appid=" + API_KEY;
        CityDataPOJO cityDataPOJO;
        CityData cityData;
        String responseJsonAsString;

        responseJsonAsString = httpClient.getResponseFromApiAsJSONString(urlString);
        responseJsonAsString = trimWrappingSquareBracketsFromResponse(responseJsonAsString);

        if (responseJsonAsString.length() > 0){
            try {
                cityDataPOJO = objectMapper.readValue(responseJsonAsString, CityDataPOJO.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse response JSON", e);
            }

            cityData = new CityData(removeQuotes(cityDataPOJO.getLocalNames().get("pl").toString()),
                    cityDataPOJO.getLat(),
                    cityDataPOJO.getLon()
            );
        } else {
            cityData = new CityData("Nie znaleziono miasta",0,0);
        }
        return cityData;
    }

    private Image getConditionsIcon(String iconId){

        iconId = removeQuotes(iconId);
        String urlString = "http://openweathermap.org/img/wn/" + iconId +"@2x.png";

        return new Image(urlString);
    }

    private boolean isCityDataCorrect(CityData cityData) {
        return cityData.longitude != 0 && cityData.latitude != 0;
    }

    private String getDayFromUnixTimestamp(long epochSeconds){

        DayOfWeek dayOfTheWeek = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSeconds), ZoneId.systemDefault()).getDayOfWeek();

        return switch (dayOfTheWeek){
            case SUNDAY -> "Niedz";
            case MONDAY -> "Pon";
            case TUESDAY -> "Wt";
            case WEDNESDAY -> "Śr";
            case THURSDAY -> "Czw";
            case FRIDAY -> "Pt";
            case SATURDAY -> "Sob";
        };
    }
    private String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    private String removeQuotes(String stringWithQuotes){
        return stringWithQuotes.replace("\"","");
    }

    private String trimWrappingSquareBracketsFromResponse (String stringToTrim){
        return stringToTrim.substring(1, stringToTrim.length() - 1);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static
    class CityDataPOJO {
        public double lat;

        public double lon;

        @JsonProperty("local_names")
        JsonNode localNames;

        public double getLat() {
            return lat;
        }

        public double getLon() {
            return lon;
        }

        public JsonNode getLocalNames() {
            return localNames;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    static
    class ForecastResponse {
        List<WeatherPOJO> list;

        public List<WeatherPOJO> getList() {
            return list;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static
    class WeatherPOJO {
        Main main;
        List<WeatherData> weather;
        public long dt;
        @JsonProperty("dt_txt")
        String dtTXT;

        public long getDateAsEpochSeconds(){
            return dt;
        }

        public String getDateAsText() {
            return dtTXT;
        }

        public List<WeatherData> getWeather() {
            return weather;
        }

        public String getConditionsDescription(){
            return weather.get(0).getDescription();
        }

        public Main getMain() {
            return main;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static
    class WeatherData {
        String description;
        String icon;

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static
    class Main {
        public double temp;

        public int getTemperature() {
            return (int) temp;
        }
    }
}
