package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.supportmeinc.ImageUtils;
import org.supportmeinc.MainController;
import org.supportmeinc.SceneName;

import java.io.IOException;

public class Register implements JFXcontroller {

    private MainController controller;

    @FXML private TextField userName;
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private PasswordField rePassword;
    @FXML private ImageView picturePreview;
    @FXML private Label rMessage;

    private byte[] image;
    private Image imageFile;

    @Override
    public void initData(MainController controller) {
        this.controller = controller;
    }

    public void chooseImage() {
        this.image = controller.jfxImageChooser();
        this.imageFile = ImageUtils.toImage(image);
        picturePreview.setImage(imageFile);
    }

    public void userRegister() {
        String userNameString = userName.getText();
        String mail = email.getText();
        String pass = password.getText();
        String rePass = rePassword.getText();


        if (userNameString.isEmpty() || mail.isEmpty() || pass.isEmpty() || rePass.isEmpty()) {
            rMessage.setText("Please fill up all fields!");
        }
        else if (!pass.equals(rePass)) {
            rMessage.setText("Passwords does not match!");
        }
        else {
            controller.registerUser(mail,userNameString,pass,image);
        }
    }

    public void switchToLogin(javafx.event.ActionEvent event) throws IOException {
        controller.sceneSwitch(SceneName.login, event);
    }
}
