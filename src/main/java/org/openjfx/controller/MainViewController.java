package org.openjfx.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.openjfx.model.Persistence.Persistence;
import org.openjfx.model.Persistence.PersistenceState;
import org.openjfx.model.Weather;
import org.openjfx.model.WeatherService;
import org.openjfx.model.WeatherServiceFactory;
import org.openjfx.view.IconsEnum;
import org.openjfx.view.ViewFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController extends BaseController implements Initializable{
    private WeatherService weatherService;
    private Persistence persistence = new Persistence();
    private boolean isLeftColCityUpdated = false;
    private boolean isRightColCityUpdated = false;
    @FXML
    private AnchorPane mainViewRootAnchorPane;
    @FXML
    private Pane leftPaneWrapper;
    @FXML
    private Pane rightPaneWrapper;
    @FXML
    private TextField leftColCity;
    @FXML
    private TextField rightColCity;
    @FXML
    private Button menuButton;
    @FXML
    private Button exitButton;

    public MainViewController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @FXML
    void exitButtonAction() {
        Platform.exit();
    }
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
        if (leftCity != null && !leftCity.isEmpty()){
            setUpLeftColumnData(leftCity);
        }
        isLeftColCityUpdated = true;
    }
    @FXML
    void rightColCityAction(){
        String rightCity = rightColCity.getText();

        if (rightCity != null && !rightCity.isEmpty()){
            setUpRightColumnData(rightCity);
        }
        isRightColCityUpdated = true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpButtonIcons();
        draggableMaker.makeWindowDraggable(mainViewRootAnchorPane);
        setUpWeatherService();
        setUpInitialData();
        setUpTextFieldsListener();
    }

    private void setUpButtonIcons() {
        menuButton.setGraphic(imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.MENU_WHITE),30,30));
        exitButton.setGraphic(imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.EXIT_WHITE),30,30));
    }

    private void setUpWeatherService(){
        weatherService = WeatherServiceFactory.createWeatherService();
    }
    private void setUpInitialData() {

        PersistenceState persistenceState = persistence.getPersistenceState();

        if (persistenceState.getNumberOfCitiesSavedToPersistence() > 0){
            leftColCity.textProperty().set(persistenceState.getLeftCity());
            setUpLeftColumnData(leftColCity.getText());
            viewFactory.setColorTheme(persistenceState.getColorTheme());
            viewFactory.setFontSize(persistenceState.getFontSize());
        }
        if (persistenceState.getNumberOfCitiesSavedToPersistence() > 1) {
            rightColCity.textProperty().set(persistenceState.getRightCity());
            setUpRightColumnData(rightColCity.getText());
        }
    }

    private void setUpLeftColumnData(String cityName){
        Weather weather = weatherService.getWeather(cityName);
        ObservableList<Weather> forecast = FXCollections.observableArrayList(weatherService.getForecast(cityName));

        leftPaneWrapper.getChildren().clear();
        leftPaneWrapper.getChildren().add(viewFactory.getSingleColumnView(weather,forecast));
    }

    private void setUpRightColumnData(String cityName){
        Weather weather = weatherService.getWeather(cityName);
        ObservableList<Weather> forecast = FXCollections.observableArrayList(weatherService.getForecast(cityName));

        rightPaneWrapper.getChildren().clear();
        rightPaneWrapper.getChildren().add(viewFactory.getSingleColumnView(weather,forecast));
    }

    private void setupLoseFocusWhenClickedOutside(){

        mainViewRootAnchorPane.getScene().setOnMousePressed(event -> {
            if (!leftColCity.equals(event.getSource())) {
                mainViewRootAnchorPane.requestFocus();
                if (!isLeftColCityUpdated){
                    leftColCity.fireEvent(new ActionEvent());
                }
            }
            if (!rightColCity.equals(event.getSource())){
                mainViewRootAnchorPane.requestFocus();
                if(!isRightColCityUpdated){
                    rightColCity.fireEvent(new ActionEvent());
                }
            }
        });
    }

    private void setUpTextFieldsListener(){
        leftColCity.textProperty().addListener( ((observableValue, s, t1) -> isLeftColCityUpdated = false));
        rightColCity.textProperty().addListener(((observableValue, s, t1) -> isRightColCityUpdated = false));
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

        persistence.saveToPersistence(persistenceList);
    }
}
