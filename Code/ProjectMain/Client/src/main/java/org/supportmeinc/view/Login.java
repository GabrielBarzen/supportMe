package org.supportmeinc.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.supportmeinc.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Login implements JFXcontroller {

    Main controller;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private TextField userName;
    @FXML private TextField password;
    @FXML private Label message;


    private FileChooser fileChooser;
    private File filePath;


    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
    }

    public void userLogin(ActionEvent event) throws IOException {
        String email = userName.getText();
        String pass = password.getText();


        if (email.isEmpty() && pass.isEmpty()) {
            message.setText("Please enter your email and password!");

        } else if (email.equals("a") && pass.equals("a")) {
            scene = new Scene(controller.loadFXML("toolbar"));
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else {
            message.setText("Wrong username or password!");
        }
    }


/*
    public void checkLogin() throws IOException{
        if (userName.getText().isEmpty() && password.getText().isEmpty()){
            message.setText("Please enter your email and password!");
        }
        else if (userName.getText().toString().equals("a")&& password.getText().toString().equals("a")) {
 //           controller.changeScene("toolbar.fxml");
            scene = new Scene(controller.loadFXML("register"));
            stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
    }

 */

    public void switchToRegister(javafx.event.ActionEvent event) throws IOException {
        scene = new Scene(controller.loadFXML("register"));
        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
      //  Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
     //   stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
     //   scene = new Scene(root);
     //   stage.setScene(scene);
     //   stage.show();
    }

    public void switchToLogin(javafx.event.ActionEvent event) throws IOException {
        scene = new Scene(controller.loadFXML("login"));
        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void chooseImage(ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);

        try {
            BufferedImage bufferedImage = ImageIO.read(filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}

