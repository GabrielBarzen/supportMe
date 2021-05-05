package org.supportmeinc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.supportmeinc.view.*;
import org.supportmeinc.view.GuideEditorUi;
import shared.Card;
import shared.User;

import org.supportmeinc.model.*;

import java.io.*;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.HashMap;
import shared.User;



/**
 * JavaFX App
 */
public class Main extends Application {

    private static Scene scene;
    private static Stage mainStage;
    private int port;
    private String ip;
    private Connection connection;
    private GuideManager guideManager;
    private org.supportmeinc.model.GuideEditor guideEditor;

    public static void main(String[] args) {
        launch();
    }



    //Configuration methods//
    private void readConfig(URL url) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Paths.get(url.toURI()).toFile()))){
            String configEntry;

            while ((configEntry = bufferedReader.readLine()) != null) {
                String[] entry = configEntry.split("=");
                switch (entry[0]) {
                    case "ip":
                        ip = entry[1];
                        break;
                    case "port":
                        port = Integer.parseInt(entry[1]);
                        break;
                    default:
                        System.out.println("Config entry : " + entry[0] + " is not a valid config entry");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Config file not found");
        } catch (IOException e) {
            System.out.println("Read exception in config");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //Model methods//
    private MainController mainController;

    @Override
    public void start(Stage stage) throws IOException {
        readConfig(getClass().getResource("config.conf"));

//        User replaceWithUserFromLoginScreen = new User("Nicholas","6nice9","NiCeRdIcErDeLuXePrOfUsIoNeXTrEaMSdReaAMS", ImageUtils.toBytes("FinalLogotyp.png"));
//        replaceWithUserFromLoginScreen.setNewUser(false);

        this.mainController = new MainController(stage, this, guideManager);
    }

    public GuideManager Login (String email, String userPassword){
        User user = new User(email, userPassword);

        try {
            connection = new Connection(ip, port, user); //Todo : replace with user from login screen
            guideManager = new GuideManager(connection);
        } catch (IOException e) {
            guideManager = new GuideManager();
            System.out.println("Cannot create a connection, starting in offline mode");
        }
        return guideManager;
    }


    public GuideManager register(User user) {
        try {
            connection = new Connection(ip, port, user); //Todo : replace with user from login screen
            guideManager = new GuideManager(connection);
        } catch (IOException e) {
            System.exit(1);
            e.printStackTrace();
        }
        return guideManager;
    }
}

