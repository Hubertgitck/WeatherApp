package org.openjfx.model;

import java.time.LocalDate;

public class Weather {

    private final String cityName;
    private final double tempInCelsius;
    private final LocalDate date;

    public Weather(String cityName, double tempInCelsius, LocalDate date) {
        this.cityName = cityName;
        this.tempInCelsius = tempInCelsius;
        this.date = date;
    }

    public String getCityName() {
        return cityName;
    }

    public double getTempInCelsius() {
        return tempInCelsius;
    }

    public LocalDate getDate() {
        return date;
    }
}
