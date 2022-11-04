package org.openjfx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.openjfx.model.Weather;
import org.openjfx.model.WeatherService;
import org.openjfx.model.WeatherServiceFactory;
import org.openjfx.model.client.WeatherClient;
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

    public MainViewController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpWeatherService();
        setUpWeatherData();
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
}
