package org.supportmeinc.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.supportmeinc.MainController;
import java.io.File;
import java.io.IOException;

public class Register implements JFXcontroller {

    private MainController controller;

    @FXML private Button btnRegister;
    @FXML private Button btnPicture;
    @FXML private TextField firstname;
    @FXML private TextField lastname;
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private PasswordField password1;
    @FXML private ImageView picturePreview;
    @FXML private Label rMessage;

    private byte[] image;

    private Stage stage;
    private Scene scene;

    private FileChooser fileChooser;
    private File filePath;

    @Override
    public void initData(MainController controller) {
        this.controller = controller;
    }

    public void displayImage() {

    }

    public void userRegister(ActionEvent event) throws IOException {
        String fName = firstname.getText();
        String lName = lastname.getText();
        String mail = email.getText();
        String pass = password.getText();
        String pass1 = password1.getText();
        Image pic = picturePreview.getImage();

        if(fName.isEmpty()|| lName.isEmpty()|| mail.isEmpty()|| pass.isEmpty()||pass1.isEmpty()){
            rMessage.setText("please fill up all fields!");
        }

    }

    public void switchToLogin(ActionEvent actionEvent) {
    }
}
