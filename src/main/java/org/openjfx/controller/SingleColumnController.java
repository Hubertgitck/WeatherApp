package org.openjfx.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.openjfx.model.Weather;
import org.openjfx.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleColumnController extends BaseController implements Initializable {

    private final Weather weather;
    private final ObservableList<Weather> forecast;

    @FXML
    private ImageView conditionsIcon;

    @FXML
    private Label temperature;

    @FXML
    private Label weatherConditions;

    @FXML
    private TableView<Weather> forecastTableView;
    @FXML
    private TableColumn<Weather, Image> forecastConditionsIconCol;
    @FXML
    private TableColumn<Weather, String> forecastConditionsCol;
    @FXML
    private TableColumn<Weather, String> forecastTemperatureCol;
    @FXML
    private TableColumn<Weather, String> forecastDayCol;

    public SingleColumnController(ViewFactory viewFactory, String fxmlName, Weather weather, ObservableList<Weather> forecast) {
        super(viewFactory, fxmlName);
        this.weather = weather;
        this.forecast = forecast;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupWeather();
        setupForecast();
    }

    private void setupWeather(){
        Platform.runLater(() ->{

            temperature.setText(convertToDegreeAndCelsiusFormat(weather.getTempInCelsius()));
            weatherConditions.setText(weather.getConditions());
            conditionsIcon.setImage(weather.getCurrentConditionsImage());
        });
    }

    private void setupForecast(){

        Platform.runLater( () -> forecastTableView.setItems(forecast));

        forecastDayCol.setCellValueFactory(data -> data.getValue().getDayOfTheWeekProperty());
        forecastTemperatureCol.setCellValueFactory(data -> {
            int temp =  data.getValue().getTempInCelsius();
            return new SimpleStringProperty(convertToDegreeAndCelsiusFormat(temp));
        });
        forecastConditionsCol.setCellValueFactory(data ->  data.getValue().getConditionsProperty());

        forecastConditionsIconCol.setCellFactory(tableCell ->{
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            TableCell<Weather, Image> cell = new TableCell<>() {
                protected void updateItem(Image item, boolean empty) {
                    if (item != null) {
                        imageView.setImage(item);
                    }
                }
            };
            cell.setGraphic(imageView);
            return cell;
        });
        forecastConditionsIconCol.setCellValueFactory(data -> data.getValue().getImageProperty());
    }

    private String convertToDegreeAndCelsiusFormat(int temperatureInt){
        return temperatureInt + "\u00B0 C";
    }
}
