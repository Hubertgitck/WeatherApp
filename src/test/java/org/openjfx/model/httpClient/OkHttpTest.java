package org.openjfx.model.httpClient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class OkHttpTest {

    private static OkHttp okHttpSpy;

    @BeforeAll
    public static void setupClient(){
        OkHttp okHttp = new OkHttp();
        okHttpSpy = spy(okHttp);
    }

    @Test
    void shouldReturnJsonStringFromApiCall(){

        //given
        String dummyUrl = "DummyUrl";
        String testString = "{\"name\":\"Warsaw\",\"local_names\":{\"fi\":\"Varsova\",\"pl\":\"Warszawa\",\"de\":\"Warschau\",\"en\":\"Warsaw\"}}";

        given(okHttpSpy.getResponseFromApiAsJSONString(dummyUrl)).willReturn(testString);

        //when
        String result = okHttpSpy.getResponseFromApiAsJSONString(dummyUrl);

        //then
        assertThat(result, is(testString));
    }

}