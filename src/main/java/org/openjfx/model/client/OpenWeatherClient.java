package org.openjfx.model.client;

import org.openjfx.model.Weather;

import java.util.List;

public class OpenWeatherClient implements WeatherClient {

    OpenWeatherImplementation openWeather;

    public OpenWeatherClient(OpenWeatherImplementation openWeather) {
        this.openWeather = openWeather;
    }

    @Override
    public Weather getWeather(String cityName) {
        return  openWeather.getWeather(cityName);
    }
    @Override
    public List<Weather> getForecast(String cityName) {
        return  openWeather.getForecast(cityName);
    }
}

