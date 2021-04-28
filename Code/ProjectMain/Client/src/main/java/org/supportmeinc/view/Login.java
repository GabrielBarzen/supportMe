package org.supportmeinc.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
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
import org.supportmeinc.MainController;
import org.supportmeinc.SceneName;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Login implements JFXcontroller {

    private MainController controller;

    private Stage stage;
    private Scene scene;

    @FXML private TextField userName;
    @FXML private TextField password;
    @FXML private Label message;


    private FileChooser fileChooser;
    private File filePath;


    public void initData(MainController controller){
        this.controller = controller;
    }

    public void userLogin(ActionEvent event) throws IOException {
        String email = userName.getText();
        String pass = password.getText();


        if (email.isEmpty() && pass.isEmpty()) {
            message.setText("Please enter your email and password!");

        } else if (email.equals("a") && pass.equals("a")) {
//            sceneSwitch(SceneName.toolbar, event);
            controller.sceneSwitch(SceneName.toolbar, event);
        }
        else {
            message.setText("Wrong username or password!");
        }
    }

    public void switchToRegister(javafx.event.ActionEvent event) throws IOException {
//        sceneSwitch(SceneName.register, event);
        controller.sceneSwitch(SceneName.register, event);
    }

    public void switchToLogin(javafx.event.ActionEvent event) throws IOException {
//        sceneSwitch(SceneName.login, event);
        controller.sceneSwitch(SceneName.login, event);
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

