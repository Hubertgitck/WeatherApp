package org.openjfx;

import javafx.application.Application;
import javafx.stage.Stage;
import org.openjfx.view.ViewFactory;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    ViewFactory viewFactory = new ViewFactory();

    @Override
    public void start(Stage stage) {

        viewFactory.showMainView();
    }
    @Override
    public void stop() {
        viewFactory.onMainViewCloseEvent();
    }
}