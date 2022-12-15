package org.openjfx.model;

import javafx.scene.image.Image;

import static org.mockito.Mockito.mock;

public class WeatherStub {

    public static Weather createWeatherObject(){
        Image image = mock(Image.class);
        return new Weather("Kielce","Zachmurzenie duże",15,"2022-11-11", image, "śr");
    }
}
