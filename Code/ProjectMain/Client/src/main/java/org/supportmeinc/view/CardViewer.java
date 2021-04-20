package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.supportmeinc.Main;
import org.supportmeinc.JfxUtils;
import java.net.URL;
import java.util.ResourceBundle;

public class CardViewer implements JFXcontroller, Initializable {

    Main controller;

    @FXML
    private Label cardTitle;
    @FXML
    private ImageView cardImage;
    @FXML
    private Label cardText;
    @FXML
    private VBox box;

    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
    }

    public CardViewer(){
        System.out.println("created cardViewer");
    }

    public void setCard(String title, byte[] image, String text) {
        cardTitle.setText(title);
        cardImage.setImage(JfxUtils.fromBytes(image));
        cardText.setText(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardImage.fitHeightProperty().bind(box.heightProperty());
        cardImage.fitWidthProperty().bind(box.widthProperty());
    }
}
