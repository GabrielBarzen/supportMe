package org.supportmeinc.view;

import javafx.fxml.FXML;
import org.supportmeinc.Main;

import javax.swing.text.html.ImageView;
import java.awt.*;

public class Register implements JFXcontroller {

    Main controller;

    @FXML private Button registerButton;
    @FXML private Button pictureButton;
    @FXML private TextField firstname;
    @FXML private TextField lastname;
    @FXML private TextField email;
    @FXML private TextField password;
    @FXML private TextField password1;
    @FXML private ImageView picture;

    @Override
    public void initData(Main controller) {
        this.controller = controller;
        controller.registerController(this);
    }

    public void displayImage(){

    }


}
