package org.supportmeinc.view;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.supportmeinc.Main;
import org.supportmeinc.MainController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GuideBrowser implements JFXcontroller, Initializable {

    private MainController controller;
    private ArrayList<ThumbnailItem> thumbnailItems = new ArrayList<>();

    @FXML private FlowPane flowPane, flowPaneSaved;
    @FXML private ScrollPane scrollPane;

    public void initData(MainController controller){
        this.controller = controller;
        controller.setGuideBrowser(this);
    }

    public void addThumbnail(String title, byte[] image, String description,UUID guideUUID) {
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
            item.setData(title, image, description, thumbnailItems.size(), guideUUID, this);
            thumbnailItems.add(item);
            updateThumbnailView(anchorPane);
        }
    }

    public GuideBrowser(){

    }


    public void openGuide(UUID uuid){
        controller.openGuide(uuid);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void updateThumbnailView(AnchorPane anchorPane) {
        flowPane.getChildren().add(anchorPane);
    }

    public void resetView() {
        thumbnailItems = new ArrayList<>();
        flowPane.getChildren().clear();
    }

    public void createNewGuide() {
        controller.createNewGuide();
    }
} //class end