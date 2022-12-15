package org.openjfx.model.client;

import javafx.application.Platform;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openjfx.model.Weather;
import org.openjfx.model.WeatherStub;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OpenWeatherClientTest {

    private static OpenWeatherClient openWeatherClient;
    private static OpenWeatherImplementation openWeatherImplementation;

    @BeforeAll
    public static void setUpJavaFXRuntime() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await(5, TimeUnit.SECONDS);
    }
    @BeforeAll
    public static void setUpClient(){
        openWeatherImplementation = mock(OpenWeatherImplementation.class);
        openWeatherClient = new OpenWeatherClient(openWeatherImplementation);
    }

    @AfterAll
    public static void tearDownJavaFXRuntime() throws InterruptedException {
        Platform.exit();
    }


    @Test
    void shouldReturnWeatherObjectFromGetWeatherMethod(){

        //given
        Weather expectedWeather = WeatherStub.createWeatherObject();
        String cityName = "Kielce";

        given(openWeatherClient.getWeather(cityName)).willReturn(expectedWeather);
        //when
        Weather result = openWeatherClient.getWeather(cityName);
        //then
        assertThat(result, is(expectedWeather));
    }

    @Test
    void shouldReturnForecastObjectFromGetWeatherMethod() {

        //given
        List<Weather> expectedForecast = List.of(WeatherStub.createWeatherObject(), WeatherStub.createWeatherObject());
        String cityName = "Kielce";

        given(openWeatherClient.getForecast(cityName)).willReturn(expectedForecast);

        //when
        List<Weather> result = openWeatherClient.getForecast(cityName);
        //then
        assertThat(result, is(expectedForecast));
    }
}