package org.openjfx.model.client;

import org.openjfx.model.Weather;

public class OpenWeatherClient implements WeatherClient {

    private OpenWeatherApi openWeatherApi = new OpenWeatherApi();

    @Override
    public Weather getWeather(String cityName) {

        CityData cityData = openWeatherApi.getCoordinates(cityName);

        return null;

    }

}

