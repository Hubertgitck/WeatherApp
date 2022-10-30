package org.openjfx.model;

import org.openjfx.model.client.MyWeatherClient;
import org.openjfx.model.client.WeatherClient;

public class WeatherServiceFactory {

    public static WeatherService createWeatherService(){
        return new WeatherService(createWeatherClient());
    }

    private static WeatherClient createWeatherClient() {
        return new MyWeatherClient();
    }
}
