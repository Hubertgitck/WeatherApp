package org.openjfx.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.openjfx.model.Weather;
import org.openjfx.model.WeatherService;
import org.openjfx.model.WeatherServiceFactory;
import org.openjfx.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController extends BaseController implements Initializable {
    private WeatherService weatherService;
    @FXML
    private TextField leftColCity;
    @FXML
    private TextField leftColCountry;
    @FXML
    private Text leftColDegreeSymbol;
    @FXML
    private Label leftColTemperature;
    @FXML
    private Label leftColWeatherConditions;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private ImageView leftColConditionsIcon;

    @FXML
    private TableView<Weather> leftColForecastTableView;
    @FXML
    private TableColumn<Weather, Image> leftColForecastConditionsIcon;
    @FXML
    private TableColumn<Weather, String> leftColForecastConditions;
    @FXML
    private TableColumn<Weather, String> leftColForecastTemp;
    @FXML
    private TableColumn<Weather, String> leftColForecastDay;


    @FXML
    private TextField rightColCity;

    @FXML
    private ImageView rightColConditionsIcon;
    @FXML
    private TableView<Weather> rightColForecastTableView;
    @FXML
    private TableColumn<Weather, String> rightColForecastConditions;

    @FXML
    private TableColumn<Weather, Image> rightColForecastConditionsIcon;

    @FXML
    private TableColumn<Weather, String> rightColForecastDay;

    @FXML
    private TableColumn<Weather, String> rightColForecastTemp;

    @FXML
    private Label rightColTemperature;

    @FXML
    private Label rightColWeatherConditions;

    public MainViewController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpWeatherService();
        setUpWeatherData();
        setUpForecastData();

    }

    private void setUpWeatherService(){
        weatherService = WeatherServiceFactory.createWeatherService();
    }
    private void setUpWeatherData() {

        String leftColCityName = "Kielce";
        String rightColCityName = "Londyn";
        leftColWeatherUpdate(leftColCityName);
        rightColWeatherUpdate(rightColCityName);
    }
    private void leftColWeatherUpdate(String leftColCityName) {
        Weather weather = weatherService.getWeather(leftColCityName);

        leftColTemperature.setText(convertToDegreeAndCelsiusFormat(weather.getTempInCelsius()));
        leftColCity.setText(leftColCityName);
        leftColWeatherConditions.setText(weather.getConditions());
        leftColConditionsIcon.setImage(weather.getCurrentConditionsImage());
    }

    private void rightColWeatherUpdate(String rightColCityName) {
        Weather weather = weatherService.getWeather(rightColCityName);

        rightColTemperature.setText(convertToDegreeAndCelsiusFormat(weather.getTempInCelsius()));
        rightColCity.setText(rightColCityName);
        rightColWeatherConditions.setText(weather.getConditions());
        rightColConditionsIcon.setImage(weather.getCurrentConditionsImage());
    }

    private void updateWeatherData(String cityName){

    }
    private void setUpForecastData() {
        String leftColCityName = "Kielce";
        String rightColCityName = "Londyn";
        setUpLeftColForecast(leftColCityName);
        setUpRightColForecast(rightColCityName);
    }

    private void setUpLeftColForecast(String leftColCityName){
        ObservableList<Weather> forecast = FXCollections.observableArrayList(weatherService.getForecast(leftColCityName));

        leftColForecastDay.setCellValueFactory(data -> data.getValue().getDayOfTheWeekProperty());
        leftColForecastTemp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Weather, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Weather, String> weatherDoubleCellDataFeatures) {
                int temp =  weatherDoubleCellDataFeatures.getValue().getTempInCelsius();
                return new SimpleStringProperty(convertToDegreeAndCelsiusFormat(temp));
            }
        });
        leftColForecastConditions.setCellValueFactory(data ->  data.getValue().getConditionsProperty());

        leftColForecastConditionsIcon.setCellFactory(tableCell ->{
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            TableCell<Weather, Image> cell = new TableCell<Weather, Image>() {
                protected void updateItem(Image item, boolean empty) {
                    if (item != null) {
                        imageView.setImage(item);
                    }
                }
            };
            cell.setGraphic(imageView);
            return cell;
        });
        leftColForecastConditionsIcon.setCellValueFactory(data -> data.getValue().getImageProperty());

        leftColForecastTableView.setItems(forecast);
    }

    private void setUpRightColForecast(String rightColCityName){
        ObservableList<Weather> forecast = FXCollections.observableArrayList(weatherService.getForecast(rightColCityName));

        rightColForecastDay.setCellValueFactory(data -> data.getValue().getDayOfTheWeekProperty());
        rightColForecastTemp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Weather, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Weather, String> weatherDoubleCellDataFeatures) {
                int temp =  weatherDoubleCellDataFeatures.getValue().getTempInCelsius();
                return new SimpleStringProperty(convertToDegreeAndCelsiusFormat(temp));
            }
        });
        rightColForecastConditions.setCellValueFactory(data ->  data.getValue().getConditionsProperty());

        rightColForecastConditionsIcon.setCellFactory(tableCell ->{
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            TableCell<Weather, Image> cell = new TableCell<Weather, Image>() {
                protected void updateItem(Image item, boolean empty) {
                    if (item != null) {
                        imageView.setImage(item);
                    }
                }
            };
            cell.setGraphic(imageView);
            return cell;
        });
        rightColForecastConditionsIcon.setCellValueFactory(data -> data.getValue().getImageProperty());

        rightColForecastTableView.setItems(forecast);
    }
    private String convertToDegreeAndCelsiusFormat(int temperatureInt){
        return temperatureInt + "\u00B0 C";
    }
}
