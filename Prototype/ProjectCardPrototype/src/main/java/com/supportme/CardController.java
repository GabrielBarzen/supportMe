package com.supportme;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class CardController {

    @FXML
    ImageView instructionImage;

    @FXML
    private void affirmative() throws IOException {
        System.out.println("yoooo");
    }

    @FXML
    private void negative() throws IOException {
        System.out.println("nooooo");
        instructionImage.setImage(new Image("com/supportme/img.png"));
    }
}
