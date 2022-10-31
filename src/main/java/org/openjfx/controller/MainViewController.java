package org.openjfx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController extends BaseController implements Initializable {

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

    public MainViewController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
