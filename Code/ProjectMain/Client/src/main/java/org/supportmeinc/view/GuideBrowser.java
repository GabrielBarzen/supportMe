package org.supportmeinc.view;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import org.supportmeinc.Main;
import org.supportmeinc.ImageUtils;
import org.supportmeinc.MainController;
import shared.Thumbnail;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class GuideBrowser implements JFXcontroller, Initializable {// class Begin

    private MainController controller;
    private ArrayList<ThumbnailItem> thumbnailItems = new ArrayList<>();

    @FXML private FlowPane flowPane;
    @FXML private ScrollPane scrollPane;

    public void initData(MainController controller){
        this.controller = controller;
        controller.setGuideBrowser(this);

        
    }

    public void addThumbnail(String title, byte[] image, String description) {
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
            item.setData(title, image, description, thumbnailItems.size());
            thumbnailItems.add(item);
            updateThumbnailView(anchorPane);
        }
    }

    public GuideBrowser(){

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
} //class end