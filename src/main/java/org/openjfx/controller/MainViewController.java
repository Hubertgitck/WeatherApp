package org.openjfx.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.openjfx.model.Weather;
import org.openjfx.model.WeatherService;
import org.openjfx.model.WeatherServiceFactory;
import org.openjfx.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController extends BaseController implements Initializable{
    private WeatherService weatherService;
    private final DraggableMaker draggableMaker = new DraggableMaker();

    private final static PersistenceCities persistenceCities = new PersistenceCities();

    @FXML
    void exitButtonAction() {
        Platform.exit();
    }

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

    @FXML
    private Pane loadingPane;

    @FXML
    void leftColCityOnMouseClicked() {
        setupLoseFocusWhenClickedOutside();
    }

    @FXML
    void rightColCityOnMouseClicked() {
        setupLoseFocusWhenClickedOutside();
    }

    @FXML
    void leftColCityAction() {
        new Thread ( () ->{
            Platform.runLater( () -> {
                String cityName = leftColCity.getText();
                leftColWeatherUpdate(cityName);
                setUpLeftColForecast(cityName);
                leftColCity.getParent().requestFocus();
            });
        }).start();
    }

    @FXML
    void rightColCityAction() {
        new Thread ( () ->{
            Platform.runLater( () -> {
                String cityName = rightColCity.getText();
                rightColWeatherUpdate(cityName);
                setUpRightColForecast(cityName);
                rightColCity.getParent().requestFocus();
            });
        }).start();
    }


    public MainViewController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draggableMaker.makeWindowDraggable(rootAnchorPane);
        setUpWeatherService();
        setUpWeatherData();
        setUpForecastData();
    }
    private void setUpWeatherService(){
        weatherService = WeatherServiceFactory.createWeatherService();
    }
    private void setUpWeatherData() {
/*        List<String> rememberedCitiesList = persistenceCities.loadCitiesFromPersistance();
        if (rememberedCitiesList.size() == 1){
            leftColWeatherUpdate(rememberedCitiesList.get(0));
        } else if(rememberedCitiesList.size() == 2){
            leftColWeatherUpdate(rememberedCitiesList.get(0));
            rightColWeatherUpdate(rememberedCitiesList.get(1));
        }*/

        leftColWeatherUpdate("Kielce");
        rightColWeatherUpdate("Madryt");
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

    private void setUpForecastData() {

        setUpLeftColForecast("Kielce");
        setUpRightColForecast("Madryt");
    }

    private void setUpLeftColForecast(String leftColCityName){
        ObservableList<Weather> forecast = FXCollections.observableArrayList(weatherService.getForecast(leftColCityName));

        leftColForecastDay.setCellValueFactory(data -> data.getValue().getDayOfTheWeekProperty());
        leftColForecastTemp.setCellValueFactory(data -> {
            int temp =  data.getValue().getTempInCelsius();
            return new SimpleStringProperty(convertToDegreeAndCelsiusFormat(temp));
        });
        leftColForecastConditions.setCellValueFactory(data ->  data.getValue().getConditionsProperty());

        leftColForecastConditionsIcon.setCellFactory(tableCell ->{
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
        leftColForecastConditionsIcon.setCellValueFactory(data -> data.getValue().getImageProperty());

        leftColForecastTableView.setItems(forecast);
    }

    private void setUpRightColForecast(String rightColCityName){
        ObservableList<Weather> forecast = FXCollections.observableArrayList(weatherService.getForecast(rightColCityName));

        rightColForecastDay.setCellValueFactory(data -> data.getValue().getDayOfTheWeekProperty());
        rightColForecastTemp.setCellValueFactory(data -> {
            int temp =  data.getValue().getTempInCelsius();
            return new SimpleStringProperty(convertToDegreeAndCelsiusFormat(temp));
        });
        rightColForecastConditions.setCellValueFactory(data ->  data.getValue().getConditionsProperty());

        rightColForecastConditionsIcon.setCellFactory(tableCell ->{
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
        rightColForecastConditionsIcon.setCellValueFactory(data -> data.getValue().getImageProperty());

        rightColForecastTableView.setItems(forecast);
    }
    private String convertToDegreeAndCelsiusFormat(int temperatureInt){
        return temperatureInt + "\u00B0 C";
    }

    private void setupLoseFocusWhenClickedOutside(){
        System.out.println(rootAnchorPane.getScene());
        rootAnchorPane.getScene().setOnMousePressed(event -> {
            if (!leftColCity.equals(event.getSource()) || !rightColCity.equals(event.getSource())){
                rootAnchorPane.requestFocus();
            }
        });
    }

}
