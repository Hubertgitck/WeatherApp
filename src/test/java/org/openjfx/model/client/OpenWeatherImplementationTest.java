package org.openjfx.model.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openjfx.model.Weather;
import org.openjfx.model.httpClient.OkHttp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OpenWeatherImplementationTest {

    @InjectMocks
    private OpenWeatherImplementation openWeatherImplementation;

    @Mock
    private OkHttp okHttp;

    @Test
    void example() {

        //given
        given(okHttp.getResponseFromApiAsJSONString(anyString()))
                // extract this json to file
                .willReturn("""
                        [{"name":"Kielce","local_names":{"pl":"Kielce"},"lat":50.85403585,"lon":20.609914352101452,"country":"PL","state":"Świętokrzyskie Voivodeship"}]
                        """)
                .willReturn("""
                        {"coord":{"lon":20.6099,"lat":50.854},"weather":[{"id":804,"main":"Clouds","description":"zachmurzenie duże","icon":"04n"}],"base":"stations","main":{"temp":1.98,"feels_like":0.75,"temp_min":1.62,"temp_max":2.22,"pressure":1000,"humidity":38},"visibility":311,"wind":{"speed":1.34,"deg":0,"gust":2.24},"clouds":{"all":100},"dt":1671644655,"sys":{"type":2,"id":2000604,"country":"PL","sunrise":1671604651,"sunset":1671633211},"timezone":3600,"id":769250,"name":"Kielce","cod":200}                        
                        """);
        //when
        Weather result = openWeatherImplementation.getWeather(anyString());

        assertAll(
                () -> assertThat(result.getConditions().get(), is("Zachmurzenie duże")),
                () -> assertThat(result.getTempInCelsius(), is(1)),
                () -> assertThat(result, is(notNullValue())
        ));
    }

}