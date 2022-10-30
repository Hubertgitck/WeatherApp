package org.openjfx.model.client;

import org.openjfx.model.Weather;

public interface WeatherClient {
    Weather getWeather(String cityName);
}
