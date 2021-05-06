package org.supportmeinc.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.supportmeinc.ImageUtils;
import org.supportmeinc.MainController;
import org.supportmeinc.SceneName;
import shared.User;

import java.io.File;
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
    private File imageFile;

    @Override
    public void initData(MainController controller) {
        this.controller = controller;
    }

    public void chooseImage() {
        this.imageFile = controller.jfxFileChooser();
        image = ImageUtils.toBytes(imageFile);
        picturePreview.setImage(ImageUtils.toImage(image));
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
            User user = new User(mail,userNameString,pass,image);
            user.setNewUser(true);
            controller.registerUser(user);
        }
    }

    public void switchToLogin(javafx.event.ActionEvent event) throws IOException {
        controller.sceneSwitch(SceneName.login, event);
    }
}
