module com.supportme {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.supportme to javafx.fxml;
    exports com.supportme;
}