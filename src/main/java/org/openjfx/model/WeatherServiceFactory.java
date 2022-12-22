package org.openjfx.model;

import okhttp3.OkHttpClient;
import org.openjfx.model.client.OpenWeatherClient;
import org.openjfx.model.client.OpenWeatherImplementation;
import org.openjfx.model.client.WeatherClient;
import org.openjfx.model.httpClient.OkHttp;

public class WeatherServiceFactory {

    public static WeatherService createWeatherService(){
        return new WeatherService(createWeatherClient());
    }

    private static WeatherClient createWeatherClient() {
        return new OpenWeatherClient(new OpenWeatherImplementation(new OkHttp(new OkHttpClient())));
    }
}
