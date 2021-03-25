package org.supportmeinc.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.supportmeinc.Main;

public class CardViewer implements JFXcontroller {

    Main controller;

    @FXML
    private ImageView cardImage;
    @FXML
    private Label cardText;
    @FXML
    private Label cardTitle;

    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
    }

    public CardViewer(){
        System.out.println("created cardViewer");
    }

    public void setCard(String title, Image image, String text) {
        cardTitle.setText(title);
        cardImage.setImage(image);
        cardText.setText(text);
    }
}
