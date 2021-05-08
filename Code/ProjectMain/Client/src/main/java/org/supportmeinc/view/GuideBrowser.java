package org.supportmeinc.view;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    @FXML private Button btnEdit, btnDelete;

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
            item.setData(title, image, description, guideUUID, this, true);
            thumbnailItems.add(item);
            flowPane.getChildren().add(anchorPane);
        }
    }

    public GuideBrowser(){

    }


    public void openGuide(UUID uuid) { //called from Right-click context menu in GuideBrowser-GUI
        controller.openGuide(uuid);
    }

    public void openGuide() { //called from Open Guide button in GuideBrowser-GUI
        controller.openGuide(currentUUID);
    }

    public void editGuide() {
        controller.setEditGuide(currentUUID);
    }

    public void deleteGuide() {
        controller.deleteGuide(currentUUID);
    }

    public void downloadGuide() {
        controller.downloadGuide(currentUUID);
    }

    public void downloadGuide(UUID uuid) {
        controller.downloadGuide(uuid);
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

    public void setSelectedThumbnail(UUID currentUUID, boolean author) { //called when thumbnail is clicked
        this.currentUUID = currentUUID;
        btnEdit.setVisible(author);
        btnDelete.setVisible(author);
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
            item.setData(title, image, description, guideUUID, this, false);
            thumbnailItems.add(item);
            flowPaneSaved.getChildren().add(anchorPane);
        }
    }

    public void addThumbnailDownloaded(String title, byte[] image, String description, UUID guideUUID) {
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
            item.setData(title, image, description, guideUUID, this, false);
            thumbnailItems.add(item);
            flowPaneSaved.getChildren().add(anchorPane);
        }
    }
} //class end