package org.openjfx.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.openjfx.view.ColorTheme;
import org.openjfx.view.FontSize;
import org.openjfx.view.IconsEnum;
import org.openjfx.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public final class OptionsController extends BaseController implements Initializable {

    @FXML
    private AnchorPane optionsRootAnchorPane;
    @FXML
    private Button exitButton;
    @FXML
    private Button applyButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Slider fontSizePicker;
    @FXML
    private ChoiceBox<ColorTheme> themePicker;

    public OptionsController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @FXML
    void applyButtonOnAction() {
        viewFactory.setColorTheme(themePicker.getValue());
        viewFactory.setFontSize(FontSize.values()[(int)(fontSizePicker.getValue())]);
        viewFactory.updateStyles();
    }
    @FXML
    void cancelButtonOnAction() {
        closeOptionsWindowAction();
    }
    @FXML
    void exitButtonAction() {
        closeOptionsWindowAction();
    }
    @FXML
    void exitOnMouseEntered() {
        exitButton.setGraphic(imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.CLOSE_RED),20,20));
    }
    @FXML
    void exitOnMouseExited() {
        exitButton.setGraphic(imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.CLOSE_WHITE),20,20));
    }
    @FXML
    void applyOnMouseEntered() {
        setUpButtonGreen(applyButton);
    }

    @FXML
    void applyOnMouseExited() {
        setUpButtonDefault(applyButton);
    }

    @FXML
    void cancelOnMouseEntered() {
        setUpButtonRed(cancelButton);
    }

    @FXML
    void cancelOnMouseExited() {
        setUpButtonDefault(cancelButton);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draggableMaker.makeWindowDraggable(optionsRootAnchorPane);
        setUpButtons();
    }

    private void setUpButtons(){
        setUpThemePicker();
        setUpSizePicker();

        exitButton.setGraphic(imageFactory.getImageView(IconsEnum.getIconPath(IconsEnum.CLOSE_WHITE),20,20));
        setUpButtonDefault(applyButton);
        setUpButtonDefault(cancelButton);
    }

    private void setUpThemePicker() {
        themePicker.setItems(FXCollections.observableArrayList(ColorTheme.values()));
        themePicker.setValue(viewFactory.getColorTheme());
    }

    private void setUpSizePicker() {
        fontSizePicker.setMin(0);
        fontSizePicker.setMax(FontSize.values().length - 1);
        fontSizePicker.setValue(viewFactory.getFontSize().ordinal());
        fontSizePicker.setMajorTickUnit(1);
        fontSizePicker.setMinorTickCount(0);
        fontSizePicker.setBlockIncrement(1);
        fontSizePicker.setSnapToTicks(true);
        fontSizePicker.setShowTickMarks(true);
        fontSizePicker.setShowTickLabels(true);
        fontSizePicker.setLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Double aDouble) {
                int i = aDouble.intValue();
                return FontSize.values()[i].toString();
            }

            @Override
            public Double fromString(String s) {
                return null;
            }
        });
        fontSizePicker.valueProperty().addListener((obs, oldVal, newVal) -> fontSizePicker.setValue(newVal.intValue()));
    }

    private void setUpButtonDefault(Button button){
        if (viewFactory.getColorTheme() == ColorTheme.LIGHT){
            button.setStyle("-fx-border-color: #545454; -fx-border-radius: 4;");
        } else {
            button.setStyle("-fx-border-color: #ffffff; -fx-border-radius: 4;");
        }
    }

    private void setUpButtonGreen(Button button){
        button.setStyle("-fx-border-color: #9affa0; -fx-text-fill: #9affa0; -fx-border-radius: 4;");
    }

    private void setUpButtonRed(Button button){
        button.setStyle("-fx-border-color: #ff8080; -fx-text-fill: #ff8080; -fx-border-radius: 4;");
    }

    private void closeOptionsWindowAction(){
        Stage stage = (Stage) fontSizePicker.getScene().getWindow();
        viewFactory.closeStage(stage);
        viewFactory.setOptionsWindowInitialized(false);
    }
}
