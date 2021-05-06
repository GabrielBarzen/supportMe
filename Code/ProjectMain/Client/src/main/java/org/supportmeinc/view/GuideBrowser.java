package org.supportmeinc.view;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.supportmeinc.Main;
import org.supportmeinc.MainController;
import org.supportmeinc.SceneName;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class GuideBrowser implements JFXcontroller, Initializable {

    private MainController controller;
    private ArrayList<ThumbnailItem> thumbnailItems = new ArrayList<>();
    private UUID currentUUID;

    @FXML private FlowPane flowPane, flowPaneSaved;
    @FXML private ScrollPane scrollPane;

    public void initData(MainController controller){
        this.controller = controller;
        controller.setGuideBrowser(this);
    }

    public void addThumbnailAuthor(String title, byte[] image, String description, UUID guideUUID) {
        ThumbnailItem item = null;
        AnchorPane anchorPane = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/thumbnail.fxml"));

            anchorPane = loader.load();
            item = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (item != null && anchorPane != null) {
            item.setData(title, image, description, guideUUID, this);
            thumbnailItems.add(item);
            flowPane.getChildren().add(anchorPane);
        }
    }

    public GuideBrowser(){

    }


    public void openGuide(UUID uuid) { //called from Right-click context menu in GuideBrowser-GUI
        System.out.println("GuideBrowser: TO OPEN: " + uuid);
        controller.openGuide(uuid);
    }

    public void openGuide() { //called from Open Guide button in GuideBrowser-GUI
        System.out.println("GuideBrowser: TO OPEN: " + currentUUID);
        controller.openGuide(currentUUID);
    }

    public void editGuide() {
        controller.setNewGuideEditorModel();
        controller.setEditGuide();
        controller.switchScene(SceneName.guideEditor);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void resetView() {
        thumbnailItems = new ArrayList<>();
        flowPane.getChildren().clear();
        flowPaneSaved.getChildren().clear();
    }

    public void createNewGuide() {
        controller.createNewGuide();
    }

    public void setCurrentUUID(UUID currentUUID) { //called when thumbnail is clicked
        this.currentUUID = currentUUID;
    }

    public void addThumbnailAccess(String title, byte[] image, String description, UUID guideUUID) {
        ThumbnailItem item = null;
        AnchorPane anchorPane = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/thumbnail.fxml"));

            anchorPane = loader.load();
            item = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (item != null && anchorPane != null) {
            item.setData(title, image, description, guideUUID, this);
            thumbnailItems.add(item);
            flowPaneSaved.getChildren().add(anchorPane);
        }
    }
} //class end