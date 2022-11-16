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
import javafx.scene.text.Text;
import org.openjfx.model.Weather;
import org.openjfx.model.WeatherService;
import org.openjfx.model.WeatherServiceFactory;
import org.openjfx.view.ColorTheme;
import org.openjfx.view.FontSize;
import org.openjfx.view.IconsEnum;
import org.openjfx.view.ViewFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController extends BaseController implements Initializable{
    private WeatherService weatherService;

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
        if (!leftCity.isEmpty() && leftCity != null){
            leftColWeatherUpdate(leftCity);
            leftColForecastUpdate(leftCity);
        }
    }

    @FXML
    void rightColCityAction() {
        String rightCity = rightColCity.getText();
        if (!rightCity.isEmpty() && rightCity !=null ){
            rightColWeatherUpdate(rightCity);
            rightColForecastUpdate(rightCity);
        }
    }


    public MainViewController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpButtonIcons();
        draggableMaker.makeWindowDraggable(mainViewRootAnchorPane);
        setUpWeatherService();
        setUpInitialData();
    }


    private void setUpButtonIcons() {
        menuButton.setGraphic(imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.MENU_WHITE),30,30));
        exitButton.setGraphic(imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.EXIT_WHITE),30,30));

    }

    private void setUpWeatherService(){
        weatherService = WeatherServiceFactory.createWeatherService();
    }
    private void setUpInitialData() {
        List<String> persistenceList = Persistence.loadFromPersistence();
        if (persistenceList.size() !=0){
            if (persistenceList.size() == 3){
                String leftCity = persistenceList.get(2);
                setUpLeftColumnData(leftCity);
            } else if (persistenceList.size() == 4){
                String leftCity = persistenceList.get(2);
                setUpLeftColumnData(leftCity);
                String rightCity = persistenceList.get(3);
                setUpRightColumnData(rightCity);
            }
            viewFactory.setColorTheme(ColorTheme.valueOf(persistenceList.get(0)));
            viewFactory.setFontSize(FontSize.valueOf(persistenceList.get(1)));
        }
    }

    private void setUpLeftColumnData(String cityName){
        leftColWeatherUpdate(cityName);
        leftColForecastUpdate(cityName);
    }

    private void setUpRightColumnData(String cityName){
        rightColWeatherUpdate(cityName);
        rightColForecastUpdate(cityName);
    }

    private void leftColWeatherUpdate(String leftColCityName) {

        new Thread( () ->{
            Weather weather = weatherService.getWeather(leftColCityName);

            Platform.runLater(() ->{
                leftColTemperature.setText(convertToDegreeAndCelsiusFormat(weather.getTempInCelsius()));
                leftColCity.setText(weather.getCityName());
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
                rightColCity.setText(weather.getCityName());
                rightColWeatherConditions.setText(weather.getConditions());
                rightColConditionsIcon.setImage(weather.getCurrentConditionsImage());
            });
        }).start();
    }

    private void leftColForecastUpdate(String leftColCityName){

        new Thread( () -> {
            ObservableList<Weather> forecast = FXCollections.observableArrayList(weatherService.getForecast(leftColCityName));
            Platform.runLater( () -> leftColForecastTableView.setItems(forecast));
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

    private void rightColForecastUpdate(String rightColCityName){

        new Thread( () -> {
            ObservableList<Weather> forecast = FXCollections.observableArrayList(weatherService.getForecast(rightColCityName));
            Platform.runLater( () -> rightColForecastTableView.setItems(forecast));
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

    public void saveToPersistence(){
        List<String> persistenceList = new ArrayList<>();

        persistenceList.add(viewFactory.getColorTheme().toString());
        persistenceList.add(viewFactory.getFontSize().toString());

        if (leftColCity.getText() != null && !leftColCity.getText().isEmpty()){
            persistenceList.add(leftColCity.getText());
        }

        if (rightColCity.getText() != null && !rightColCity.getText().isEmpty()){
            persistenceList.add(rightColCity.getText());
        }

        Persistence.saveToPersistence(persistenceList);
    }
}
