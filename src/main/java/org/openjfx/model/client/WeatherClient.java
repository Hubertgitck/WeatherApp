package org.openjfx.model.client;

import org.openjfx.model.Weather;

import java.util.List;

public interface WeatherClient {
    Weather getWeather(String cityName);
    List<Weather> getForecast(String cityName);
}
