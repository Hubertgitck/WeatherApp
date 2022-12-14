package org.openjfx.model;

import org.openjfx.model.client.WeatherClient;

import java.util.List;

public class WeatherService{

    private final WeatherClient weatherClient;

    public WeatherService(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public Weather getWeather(String cityName){
        return weatherClient.getWeather(cityName);
    }

    public List<Weather> getForecast(String cityName){
        return weatherClient.getForecast(cityName);
    }

}
