module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens org.openjfx to javafx.fxml;
    opens org.openjfx.controller to javafx.fxml;
    exports org.openjfx;
}
