module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens org.openjfx to javafx.fxml;

    opens org.openjfx.controller to javafx.fxml;
    opens org.openjfx.model to com.fasterxml.jackson.databind, javafx.base;
    opens org.openjfx.model.client to com.fasterxml.jackson.databind;

    exports org.openjfx;
}
