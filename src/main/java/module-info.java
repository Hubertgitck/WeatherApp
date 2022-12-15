module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires okhttp3;

    opens org.openjfx.controller to javafx.fxml;
    opens org.openjfx.model to com.fasterxml.jackson.databind, javafx.base;
    opens org.openjfx.model.client to com.fasterxml.jackson.databind;

    exports org.openjfx;
    opens org.openjfx.model.httpClient to com.fasterxml.jackson.databind, javafx.base;
    opens org.openjfx.model.Persistence to javafx.fxml;
}
