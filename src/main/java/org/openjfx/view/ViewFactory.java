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
import java.util.Objects;

public class ViewFactory {

    private final boolean mainWindowInitialized = false;
    private final ArrayList<Stage> activeStages;
    private final ColorTheme colorTheme = ColorTheme.DEFAULT;

    //TODO
   // private final FontSize fontSize = FontSize.MEDIUM;

    public ViewFactory() {
        this.activeStages = new ArrayList<>();
    }

    public void showMainView(){
        BaseController controller = new MainViewController(this, "/fxml/MainView.fxml");
        initializeStage(controller);
    }

    public void showOptions(){
        BaseController controller = new OptionsController(this,"/fxml/Options.fxml");
        initializeStage(controller);
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
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(ColorTheme.getCssPath(colorTheme))).toExternalForm());
            //TODO
            //scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
        }
    }
}
