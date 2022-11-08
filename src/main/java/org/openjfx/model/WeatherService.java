package org.openjfx.model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.openjfx.model.client.WeatherClient;

import java.util.ArrayList;

public class WeatherService{

    private final WeatherClient weatherClient;

    public WeatherService(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public Weather getWeather(String cityName){
        return weatherClient.getWeather(cityName);
    }

    public ArrayList<Weather> getForecast(String cityName){
        return weatherClient.getForecast(cityName);
    }

}
