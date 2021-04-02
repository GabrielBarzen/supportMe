package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import org.supportmeinc.Main;
import org.supportmeinc.model.JfxUtils;
import shared.Thumbnail;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class GuideBrowser implements JFXcontroller, Initializable {

    private Main controller;
    private List<Thumbnail> thumbnails = new ArrayList<>();

    @FXML
    private GridPane gridPane;

    @FXML
    private FlowPane flowPane;

    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
    }

    public GuideBrowser(){

    }

    public void getGuide(int index) {
        controller.initGuide(index);
    }

    public void setThumbnails(ArrayList<Thumbnail> thumbnails) {
        this.thumbnails = thumbnails;
    }

    //TODO STUBBE, VÄNLIGEN ELIMINERA EFTER TEST
    public void fakeThumbnails() {
        for (int i = 0; i < 25; i++) {
            Thumbnail nail = new Thumbnail(UUID.randomUUID());
            nail.setTitle("Title #" + i);
            byte[] testImage = JfxUtils.toBytes("src/main/resources/org/supportmeinc/FinalLogotyp.png");
            nail.setImage(testImage);
            nail.setDescription("This is a short but detailed and descriptive description for guide #" + i + " some extra text bla bla bla bla ayyyyyyyylmao wassup yoyo heyeyeyeaaa");
            thumbnails.add(nail);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fakeThumbnails();
        updateThumbnailView();
    }

    public void updateThumbnailView() {


        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < thumbnails.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("thumbnail.fxml"));
                AnchorPane anchorPane = loader.load();

                ThumbnailController thumbnailController = loader.getController();
                Thumbnail thumbnail = thumbnails.get(i);
                thumbnailController.setData(thumbnail.getTitle(), thumbnail.getImage(), thumbnail.getDescription());

                if (column == 4) {
                    column = 0;
                    row++;
                }

//                flowPane.add(anchorPane, column++, row);
//                flowPane.getChildren().add(anchorPane);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} //class end
