package org.openjfx.model;

import org.openjfx.model.client.OpenWeatherClient;
import org.openjfx.model.client.OpenWeatherImplementation;
import org.openjfx.model.client.WeatherClient;

public class WeatherServiceFactory {

    public static WeatherService createWeatherService(){
        return new WeatherService(createWeatherClient());
    }

    private static WeatherClient createWeatherClient() {
        return new OpenWeatherClient(new OpenWeatherImplementation());
    }
}
