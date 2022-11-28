package org.openjfx.model.client;

import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.openjfx.model.Weather;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class WeatherClientTest {


    @Mock
    WeatherClient weatherClient = mock(OpenWeatherClient.class);
    @Mock
    Weather weather = mock(Weather.class);

    @Test
    void shouldReturnWeatherObject() {

        //given
        String cityName = "Kielce";
        given(weatherClient.getWeather(cityName))
                .willReturn(weather);

        //when
        Weather result = weatherClient.getWeather(cityName);
        //then
        assertThat(result, sameInstance(weather));
    }

    @Test
    void shouldReturnForecastAsListOfWeatherObjects() {

        //given
        List<Weather> forecast = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            forecast.add(weather);
        }

        String cityName = "Kielce";
        given(weatherClient.getForecast(cityName))
                .willReturn(forecast);

        //when
        List<Weather> result = weatherClient.getForecast(cityName);
        //then
        assertThat(result, sameInstance(forecast));

    }
}