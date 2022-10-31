package org.openjfx;

import javafx.application.Application;
import javafx.stage.Stage;
import org.openjfx.view.ViewFactory;

/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory viewFactory = new ViewFactory();

        viewFactory.showMainView();
    }
}