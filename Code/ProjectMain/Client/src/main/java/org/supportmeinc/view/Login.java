package org.supportmeinc.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.supportmeinc.MainController;
import org.supportmeinc.SceneName;

import java.io.IOException;


public class Login implements JFXcontroller {

    private MainController controller;

    @FXML private TextField userEmail;
    @FXML private TextField password;
    @FXML private Label message;


    public void initData(MainController controller){
        this.controller = controller;
    }

    public void userLogin() throws IOException {
        String email = userEmail.getText();
        String pass = password.getText();

        if (email.isEmpty() && pass.isEmpty()) {
            message.setText("Please enter your email and password!");

        } else {
             controller.login(email, pass);
        }
    }

    public void switchToRegister() throws IOException {
        controller.switchLoginStage(SceneName.register);
    }

}

