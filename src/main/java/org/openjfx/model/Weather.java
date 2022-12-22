package org.openjfx.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import static java.lang.Math.round;

public class Weather {

    private final SimpleStringProperty cityName;
    private final SimpleStringProperty conditions;
    private final SimpleObjectProperty<Integer> tempInCelsius;
    private final SimpleStringProperty dateTime;
    private final SimpleStringProperty currentConditionsImage;
    private final SimpleStringProperty dayOfTheWeek;

    public Weather(String cityName, String conditions, int tempInCelsius, String dateTime, String currentConditionsIconUrl, String dayOfTheWeek) {
        this.cityName = new SimpleStringProperty(cityName);
        this.conditions = new SimpleStringProperty(conditions);
        this.tempInCelsius = new SimpleObjectProperty<>(tempInCelsius);
        this.dateTime = new SimpleStringProperty(dateTime);
        this.currentConditionsImage = new SimpleStringProperty(currentConditionsIconUrl);
        this.dayOfTheWeek = new SimpleStringProperty(dayOfTheWeek);
    }


    public int getTempInCelsius() {
        return round(this.tempInCelsius.get());
    }

    public StringProperty getConditions() {
        return this.conditions;
    }


    public StringProperty getConditionsProperty(){
        return this.conditions;
    }

    public SimpleStringProperty getIconStringProperty(){
        return this.currentConditionsImage;
    }

    public SimpleStringProperty getDayOfTheWeekProperty() {
        return this.dayOfTheWeek;
    }
}
