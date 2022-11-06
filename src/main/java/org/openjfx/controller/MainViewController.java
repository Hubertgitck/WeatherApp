package org.openjfx.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.text.NumberFormat;
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
    private TextField rightColCity;
    @FXML
    private TextField rightColCountry;
    @FXML
    private Text rightColDegreeSymbol;
    @FXML
    private Label rightColTemperature;
    @FXML
    private Label rightColWeatherConditions;
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

        leftColUpdate(leftColCityName);
    }

    private void leftColUpdate(String leftColCityName) {
        Weather weather = weatherService.getWeather(leftColCityName);

        leftColTemperature.setText(Double.toString(weather.getTempInCelsius()));
        leftColCity.setText(leftColCityName);
        leftColWeatherConditions.setText(weather.getConditions());
        leftColConditionsIcon.setImage(weather.getCurrentConditionsImage());
    }

    private void updateWeatherData(String cityName){

    }
    private void setUpForecastData() {
        ObservableList<Weather> forecast = FXCollections.observableArrayList(weatherService.getForecast("Kielce"));

        leftColForecastDay.setCellValueFactory(data -> data.getValue().getDayOfTheWeekProperty());
        //leftColForecastTemp.setCellValueFactory(data -> data.getValue().getTempInCelsiusProperty());
        leftColForecastTemp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Weather, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Weather, String> weatherDoubleCellDataFeatures) {
                double temp =  weatherDoubleCellDataFeatures.getValue().getTempInCelsius();
                int tempInt = (int) temp;
                String displayTemp = temp + "\u00B0 C";
                return new SimpleStringProperty(displayTemp);
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
}
