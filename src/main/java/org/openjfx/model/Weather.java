package org.openjfx.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Weather {

    private final String cityName;
    private final double tempInCelsius;
    private final LocalDateTime dateTime;

    public Weather(String cityName, double tempInCelsius, LocalDateTime dateTime) {
        this.cityName = cityName;
        this.tempInCelsius = tempInCelsius;
        this.dateTime = dateTime;
    }

    public String getCityName() {
        return cityName;
    }

    public double getTempInCelsius() {
        return tempInCelsius;
    }

    public LocalDateTime getDate() {
        return dateTime;
    }
}
