module org.supportmeinc {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.supportmeinc to javafx.fxml;
    exports org.supportmeinc;
}