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

public class GuideViewerUi implements JFXcontroller {

    private MainController controller;

    @FXML private Label cardTitle;
    @FXML private ImageView cardImage;
    @FXML private Label cardText;

    public void initData(MainController controller){
        this.controller = controller;
        controller.setGuideViewer(this);
    }

    public GuideViewerUi(){
        System.out.println("created cardViewer");
    }

    public void setCard(String title, byte[] image, String text) {
        cardTitle.setText(title);
        cardImage.setImage(ImageUtils.toImage(image));
        cardText.setText(text);
    }

    public void noSelected() {

    }

    public void yesSelected() {

    }
}
