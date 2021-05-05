package org.supportmeinc.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.supportmeinc.MainController;
import org.supportmeinc.SceneName;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Login implements JFXcontroller {

    private MainController controller;

    private Stage stage;
    private Scene scene;

    @FXML private TextField userEmail;
    @FXML private TextField password;
    @FXML private Label message;


    private FileChooser fileChooser;
    private File filePath;


    public void initData(MainController controller){
        this.controller = controller;
    }

    public void userLogin(ActionEvent event) throws IOException {
        String email = userEmail.getText();
        String pass = password.getText();

        if (email.isEmpty() && pass.isEmpty()) {
            message.setText("Please enter your email and password!");

        } else {
             controller.login(email, pass);

        }

        controller.sceneSwitch(SceneName.toolbar, event);
    }

    public void switchToRegister(javafx.event.ActionEvent event) throws IOException {
        controller.sceneSwitch(SceneName.register, event);
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

