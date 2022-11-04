package org.openjfx.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDateTime;

import static java.lang.Math.round;

public class Weather {

    private final String cityName;
    private final String conditions;
    private final double tempInCelsius;
    private final LocalDateTime dateTime;
    private final Image currentConditionsImage;

    public Weather(String cityName, String conditions, double tempInCelsius, LocalDateTime dateTime, Image currentConditionsImage) {
        this.cityName = cityName;
        this.conditions = conditions;
        this.tempInCelsius = tempInCelsius;
        this.dateTime = dateTime;
        this.currentConditionsImage = currentConditionsImage;
    }

    public String getCityName() {
        return cityName;
    }

    public double getTempInCelsius() {
        return round(tempInCelsius);
    }

    public LocalDateTime getDate() {
        return dateTime;
    }

    public String getConditions() {
        return conditions;
    }

    public Image getCurrentConditionsImage() {
        return currentConditionsImage;
    }
}
