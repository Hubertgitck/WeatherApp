package org.openjfx.model;

import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openjfx.model.client.WeatherClient;


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherClient weatherClient;
    @InjectMocks
    private WeatherService weatherService;

    @Test
    void shouldReturnWeatherObject() {

        //given
        Weather expectedWeather = createWeatherObject();
        String cityName = "Kielce";

        given(weatherClient.getWeather(cityName)).willReturn(expectedWeather);

        //when
        Weather result = weatherClient.getWeather(cityName);
        //then
        assertThat(result, is(expectedWeather));
    }

    @Test
    void shouldReturnForecastList() {

        //given
        List<Weather> expectedForecast = List.of(createWeatherObject(),createWeatherObject());
        String cityName = "Kielce";

        given(weatherClient.getForecast(cityName)).willReturn(expectedForecast);

        //when
        List<Weather> result = weatherClient.getForecast(cityName);
        //then
        assertThat(result, is(expectedForecast));
    }

    private Weather createWeatherObject() {
        Image image = mock(Image.class);
        return new Weather("Kielce","Zachmurzenie duże",15,"2022-11-11", image, "śr");
    }

}