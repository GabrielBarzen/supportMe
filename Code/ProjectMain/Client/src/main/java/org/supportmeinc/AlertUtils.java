package org.supportmeinc;

import javafx.scene.control.Alert;

public class AlertUtils {

    public static void alert(String title, String header, String description, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(description);
        alert.show();
    }

    public static void alertWarning(String title, String header, String description) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(description);
        alert.show();
    }

    public static void alertConfirmation(String title, String header, String description) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(description);
        alert.show();
    }

    public static void alertError(String title, String header, String description) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(description);
        alert.show();
    }


}
