package org.openjfx.model;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

import static java.lang.Math.round;

public class Weather {

    private final SimpleStringProperty cityName;
    private final SimpleStringProperty conditions;
    private final SimpleObjectProperty<Double> tempInCelsius;
    private final SimpleStringProperty dateTime;
    private final SimpleObjectProperty<Image> currentConditionsImage;
    private final SimpleStringProperty dayOfTheWeek;

    public Weather(String cityName, String conditions, double tempInCelsius, String dateTime, Image currentConditionsImage, String dayOfTheWeek) {
        this.cityName = new SimpleStringProperty(cityName);
        this.conditions = new SimpleStringProperty(conditions);
        this.tempInCelsius = new SimpleObjectProperty<>(tempInCelsius);
        this.dateTime = new SimpleStringProperty(dateTime);
        this.currentConditionsImage = new SimpleObjectProperty<>(currentConditionsImage);
        this.dayOfTheWeek = new SimpleStringProperty(dayOfTheWeek);
    }

    public String getCityName() {
        return this.cityName.get();
    }

    public double getTempInCelsius() {
        return round(this.tempInCelsius.get());
    }

    public String getDate() {
        return this.dateTime.get();
    }

    public String getConditions() {
        return this.conditions.get();
    }

    public Image getCurrentConditionsImage() {
        return this.currentConditionsImage.get();
    }

    public SimpleObjectProperty<Double> getTempInCelsiusProperty(){
        return this.tempInCelsius;
    }

    public StringProperty getConditionsProperty(){
        return this.conditions;
    }

    public SimpleObjectProperty<Image> getImageProperty(){
        return this.currentConditionsImage;
    }

    public SimpleStringProperty getDayOfTheWeekProperty() {
        return this.dayOfTheWeek;
    }
}
