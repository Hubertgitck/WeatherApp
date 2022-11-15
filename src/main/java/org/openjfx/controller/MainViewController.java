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
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.openjfx.model.Weather;
import org.openjfx.model.WeatherService;
import org.openjfx.model.WeatherServiceFactory;
import org.openjfx.view.IconsEnum;
import org.openjfx.view.ImageFactory;
import org.openjfx.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController extends BaseController implements Initializable{
    private WeatherService weatherService;

    private final static PersistenceCities persistenceCities = new PersistenceCities();


    @FXML
    void exitButtonAction() {
        Platform.exit();
    }
    @FXML
    private Button exitButton;
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
    private AnchorPane mainViewRootAnchorPane;
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
    private Button menuButton;
    @FXML
    void menuOnMouseEntered() {
        menuButton.setGraphic(imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.MENU_GREEN),30,30));
    }

    @FXML
    void menuOnMouseExited() {
        menuButton.setGraphic(imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.MENU_WHITE),30,30));
    }
    @FXML
    void menuButtonOnAction() {
        viewFactory.showOptions();
    }
    @FXML
    void exitOnMouseEntered() {
        exitButton.setGraphic(imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.EXIT_RED),30,30));
    }
    @FXML
    void exitOnMouseExited() {
        exitButton.setGraphic(imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.EXIT_WHITE),30,30));
    }

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
        String leftCity = leftColCity.getText();
        leftColWeatherUpdate(leftCity);
        setUpLeftColForecast(leftCity);
    }

    @FXML
    void rightColCityAction() {
        String rightCity = rightColCity.getText();
        rightColWeatherUpdate(rightCity);
        setUpRightColForecast(rightCity);
    }


    public MainViewController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpButtonIcons();
        draggableMaker.makeWindowDraggable(mainViewRootAnchorPane);
        setUpWeatherService();
        setUpWeatherData();
        setUpForecastData();
    }

    private void setUpButtonIcons() {
        menuButton.setGraphic(imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.MENU_WHITE),30,30));
        exitButton.setGraphic(imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.EXIT_WHITE),30,30));

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

        new Thread( () ->{
            Weather weather = weatherService.getWeather(leftColCityName);

            Platform.runLater(() ->{
                leftColTemperature.setText(convertToDegreeAndCelsiusFormat(weather.getTempInCelsius()));
                leftColCity.setText(leftColCityName);
                leftColWeatherConditions.setText(weather.getConditions());
                leftColConditionsIcon.setImage(weather.getCurrentConditionsImage());
            });
        }).start();
    }

    private void rightColWeatherUpdate(String rightColCityName) {

        new Thread ( () -> {
            Weather weather = weatherService.getWeather(rightColCityName);

            Platform.runLater( () -> {
                rightColTemperature.setText(convertToDegreeAndCelsiusFormat(weather.getTempInCelsius()));
                rightColCity.setText(rightColCityName);
                rightColWeatherConditions.setText(weather.getConditions());
                rightColConditionsIcon.setImage(weather.getCurrentConditionsImage());
            });
        }).start();
    }

    private void setUpForecastData() {

        setUpLeftColForecast("Kielce");
        setUpRightColForecast("Madryt");
    }

    private void setUpLeftColForecast(String leftColCityName){

        new Thread( () -> {
            ObservableList<Weather> forecast = FXCollections.observableArrayList(weatherService.getForecast(leftColCityName));
            leftColForecastTableView.setItems(forecast);
        }).start();

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
    }

    private void setUpRightColForecast(String rightColCityName){

        new Thread( () -> {
            ObservableList<Weather> forecast = FXCollections.observableArrayList(weatherService.getForecast(rightColCityName));
            rightColForecastTableView.setItems(forecast);
        }).start();

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


    }
    private String convertToDegreeAndCelsiusFormat(int temperatureInt){
        return temperatureInt + "\u00B0 C";
    }

    private void setupLoseFocusWhenClickedOutside(){
        mainViewRootAnchorPane.getScene().setOnMousePressed(event -> {
            if (!leftColCity.equals(event.getSource()) || !rightColCity.equals(event.getSource())){
                mainViewRootAnchorPane.requestFocus();
            }
        });
    }

}
