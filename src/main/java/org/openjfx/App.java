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

    ViewFactory viewFactory = new ViewFactory();

    @Override
    public void start(Stage stage) throws Exception {

        viewFactory.showMainView();
    }
    @Override
    public void stop() throws  Exception {

    }
}