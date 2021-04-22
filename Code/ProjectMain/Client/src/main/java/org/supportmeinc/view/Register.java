package org.supportmeinc.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.supportmeinc.Main;

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    @FXML private Label rMessage;

    private Stage stage;
    private Scene scene;

    private FileChooser fileChooser;
    private File filePath;

    @Override
    public void initData(Main controller) {
        this.controller = controller;
        controller.registerController(this);
    }

    public void displayImage(){

    }

    public void userRegister(ActionEvent event) throws IOException {
        String fName = firstname.getText();
        String lName = lastname.getText();
        String mail = email.getText();
        String pass = password.getText();
        String pass1 = password1.getText();
        Image pic = picture.getImage();

        if(fName.isEmpty()|| lName.isEmpty()|| mail.isEmpty()|| pass.isEmpty()||pass1.isEmpty()){
            rMessage.setText("please fill up all fields!");
        }

    }



}
