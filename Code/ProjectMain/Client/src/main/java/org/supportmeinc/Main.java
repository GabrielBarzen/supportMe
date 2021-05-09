package org.supportmeinc;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import shared.Card;
import shared.User;
import org.supportmeinc.model.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;



/**
 * JavaFX App
 */
public class Main extends Application {

    private static Scene scene;
    private static Stage mainStage;
    private int port;
    private String ip;
    private Connection connection;
    private org.supportmeinc.model.GuideEditor guideEditor;
    private GuideManager guideManager;

    public static void main(String[] args) {
        launch();
    }

    public Connection getConnection() {
        return connection;
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
    public void start(Stage stage) {
        readConfig(getClass().getResource("config.conf"));

//        User replaceWithUserFromLoginScreen = new User("Nicholas","6nice9","NiCeRdIcErDeLuXePrOfUsIoNeXTrEaMSdReaAMS", ImageUtils.toBytes("FinalLogotyp.png"));
//        replaceWithUserFromLoginScreen.setNewUser(false);

        this.mainController = new MainController(stage, this);
    }

    public GuideManager Login (String email, String userPassword) {
        User user = new User(email, userPassword);
        GuideManager guideManager;
        try {
            connection = new Connection(ip, port, user); //Todo : replace with user from login screen
            guideManager = new GuideManager(connection);
        } catch (IOException e) {
            guideManager = new GuideManager(user);
        }
        //guideManager.setController(mainController);
        return guideManager;
    }


    public GuideManager register(User user) {
        GuideManager guideManager = null;
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

