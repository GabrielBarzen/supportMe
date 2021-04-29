package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.supportmeinc.ImageUtils;
import org.supportmeinc.MainController;
import java.net.URL;
import java.util.ResourceBundle;

public class CardViewer implements JFXcontroller, Initializable {

    MainController controller;

    @FXML private Label cardTitle;
    @FXML private ImageView cardImage;
    @FXML private Label cardText;
    @FXML private VBox box;

    public void initData(MainController controller){
        this.controller = controller;
    }

    public CardViewer(){
        System.out.println("created cardViewer");
    }

    public void setCard(String title, byte[] image, String text) {
        cardTitle.setText(title);
        cardImage.setImage(ImageUtils.toImage(image));
        cardText.setText(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        cardImage.fitHeightProperty().bind(box.heightProperty()); TODO previously used for binding imageView size with other component, might be used later
//        cardImage.fitWidthProperty().bind(box.widthProperty()); TODO previously used for binding imageView size with other component, might be used later
    }
}
