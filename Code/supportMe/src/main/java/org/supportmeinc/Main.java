package org.supportmeinc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

/**
 * JavaFX App
 */
public class Main extends Application {

    private static Scene scene;
    int port;
    String ip;

    public static void main(String[] args) {
        launch();
    }

    public Main(){
        readConfig();
    }

    //Configuration methods//
    private void readConfig() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("config.conf"))){
            String configEntry;

            while ((configEntry = bufferedReader.readLine()) != null){
                String[] entry = configEntry.split("=");
                System.out.println(entry[1]);
                switch (entry[0]){
                    case "ip":
                        ip = entry[1];
                        break;
                    case "port":
                        port = Integer.parseInt(entry[1]);
                        break;
                }
            }

        } catch (FileNotFoundException e){
            System.out.println("Config file not found");
        } catch (IOException e){
            System.out.println("Read exception in config");
        }
    }
    //Model methods//

    //UI methods//
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("cardViewer"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

}