package org.supportmeinc;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;
import org.supportmeinc.model.GuideEditor;
import org.supportmeinc.model.GuideManager;
import org.supportmeinc.view.JFXcontroller;
import org.supportmeinc.view.Toolbar;
import shared.Card;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

public class MainController {

    private Main controller;
    private HashMap<SceneName, AnchorPane> scenes;
    private Stage stage;
    private Scene toolbar;
    private Toolbar toolbarController;
    private GuideManager guideManager;
    private GuideEditor guideEditor;

    public MainController(Stage stage, Main controller) {
        this.controller = controller;
        this.stage = stage;


        try {
            stage.setScene(new Scene(loadFXML(SceneName.login)));
        } catch (IOException e) {

        }

        scenes = new HashMap<>();
        stage.setTitle("supportMe");
        stage.show();
        populateScenes();
        guideEditor = new GuideEditor();
       // guideManager = new GuideManager();
    }

    public void populateScenes() {
        for (SceneName sceneName : SceneName.values()) {
            if(!(sceneName.equals(SceneName.login) || sceneName.equals(SceneName.register) || sceneName.equals(SceneName.toolbar))) {
                try {
                    AnchorPane scene = new AnchorPane(loadFXML(sceneName));
                    scenes.put(sceneName, scene);
                } catch (IOException e) {

                }
            }
        }
    }

    public AnchorPane getScenes(SceneName scene) {
        return scenes.get(scene);
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

    public void sceneSwitch(SceneName sceneName, Event event) throws IOException {
        Scene scene = new Scene(loadFXML(sceneName));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public File jfxFileChooser() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        return selectedFile;
    }

    public HashMap<UUID, Card> getCardsList() {
        return guideEditor.getCardsList();
    }

    public void addCardToList(String title, String description, File img, UUID affirmUUID, UUID negativeUUID) {
        guideEditor.addNewCard(title, description, img, affirmUUID, negativeUUID);
    }

    public void updateCard(String title, String text, File img, UUID affirmUUID, UUID negativeUUID, UUID cardUUID) {
        guideEditor.updateCard(title, text, img, affirmUUID, negativeUUID, cardUUID);
    }

    public void removeCard(UUID cardUUID) {
        guideEditor.removeCard(cardUUID);
    }
}
