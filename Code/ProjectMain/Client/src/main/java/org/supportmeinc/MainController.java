package org.supportmeinc;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;
import org.supportmeinc.view.JFXcontroller;
import org.supportmeinc.view.Toolbar;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class MainController {

    private Main controller;
    private HashMap<SceneName, AnchorPane> scenes;
    private Stage stage;
    private Scene toolbar;
    private Toolbar toolbarController;

    public MainController(Stage stage, Main controller) {
        this.controller = controller;
        this.stage = stage;

        try {
            toolbar = new Scene(loadFXML(SceneName.toolbar));
            stage.setScene(toolbar);
        } catch (IOException e) {

        }

        scenes = new HashMap<>();
        populateScenes();
    }

    public void populateScenes() {
        for (SceneName sceneName : SceneName.values()) {
            try {
                 AnchorPane scene = new AnchorPane(loadFXML(sceneName));
                 scenes.put(sceneName, scene);
            }
            catch (IOException e) {

            }
        }
    }

    public void registerToolbar(Toolbar toolbar) {
        this.toolbarController = toolbar;
    }

    public Parent loadFXML(SceneName sceneName) throws IOException { //TODO Möjligtvis refactor --> Toolbar

        String resourceName = sceneName.name();
        System.out.println(getClass().getResource("view/" + resourceName + ".fxml"));
        System.out.println(getClass().getResource("view/stylesheets/" + resourceName + "Style.css"));
        System.out.println();

        String fxml = String.valueOf(getClass().getResource("view/" + resourceName + ".fxml"));
        String styleSheet = String.valueOf(getClass().getResource("view/stylesheets/" + resourceName + "Style.css"));

        FXMLLoader fxmlLoader = new FXMLLoader(new URL(fxml));
        Parent root = fxmlLoader.load();

        System.out.println("fxml item : " + root.getClass());
        JFXcontroller jfXcontroller = fxmlLoader.getController();
        jfXcontroller.initData(this);

        root.getStylesheets().add(styleSheet);

        return root;
    }

    public void switchScene(SceneName sceneName) {
        toolbarController.swapScene(scenes.get(sceneName));
    }

    public File jfxFileChooser() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        return selectedFile;
    }
}
