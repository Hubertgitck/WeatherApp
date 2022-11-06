package org.openjfx.model.client;

import org.openjfx.model.Weather;

import java.util.ArrayList;

public interface WeatherClient {
    Weather getWeather(String cityName);

    ArrayList<Weather> getForecast(String cityName);
}
