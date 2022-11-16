package org.openjfx.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.openjfx.controller.BaseController;
import org.openjfx.controller.MainViewController;
import org.openjfx.controller.OptionsController;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {

    private boolean isOptionsWindowInitialized = false;
    private final ArrayList<Stage> activeStages;
    private MainViewController mainViewController = null;

    private ColorTheme colorTheme = ColorTheme.DEFAULT;
    private FontSize fontSize = FontSize.MEDIUM;

    public ViewFactory() {
        this.activeStages = new ArrayList<>();
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }
    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public FontSize getFontSize(){
        return  fontSize;
    }
    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public void setOptionsWindowInitialized(boolean status){
        this.isOptionsWindowInitialized = status;
    }
    public void showMainView(){
        BaseController controller = new MainViewController(this, "/fxml/MainView.fxml");
        initializeStage(controller);
        mainViewController = (MainViewController) controller;
    }

    public void showOptions(){
        if (!isOptionsWindowInitialized){
            BaseController controller = new OptionsController(this,"/fxml/Options.fxml");
            initializeStage(controller);
            isOptionsWindowInitialized = true;
        }
    }

    private void initializeStage(BaseController baseController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;

        try{
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);

        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();

        //requesting focus on parent scene to disable default focus on text fields
        parent.requestFocus();


        activeStages.add(stage);
        updateStyles();
    }
    public void closeStage(Stage stageToClose){
        stageToClose.close();
        activeStages.remove(stageToClose);
    }

    public void updateStyles() {
        for (Stage stage: activeStages){
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
        }
    }

    public void onMainViewCloseEvent(){
        mainViewController.saveToPersistence();
    }
}
