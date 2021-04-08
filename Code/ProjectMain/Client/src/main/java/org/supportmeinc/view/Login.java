package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.supportmeinc.Main;

import java.awt.event.ActionEvent;

public class Login implements JFXcontroller {

    Main controller;

    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private TextField userName;
    @FXML private TextField password;

    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
    }

    public void switchToRegister(ActionEvent event){

    }

}

