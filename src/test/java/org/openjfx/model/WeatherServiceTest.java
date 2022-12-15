package org.openjfx.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openjfx.model.client.WeatherClient;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherClient weatherClient;
    @InjectMocks
    private WeatherService weatherService;

    @Test
    void shouldReturnWeatherObject() {

        //given
        Weather expectedWeather = WeatherStub.createWeatherObject();
        String cityName = "Kielce";

        given(weatherClient.getWeather(cityName)).willReturn(expectedWeather);

        //when
        Weather result = weatherService.getWeather(cityName);
        //then
        assertThat(result, is(expectedWeather));
    }

    @Test
    void shouldReturnForecastList() {

        //given
        List<Weather> expectedForecast = List.of(WeatherStub.createWeatherObject(),WeatherStub.createWeatherObject());
        String cityName = "Kielce";

        given(weatherClient.getForecast(cityName)).willReturn(expectedForecast);

        //when
        List<Weather> result = weatherService.getForecast(cityName);
        //then
        assertThat(result, is(expectedForecast));
    }

}