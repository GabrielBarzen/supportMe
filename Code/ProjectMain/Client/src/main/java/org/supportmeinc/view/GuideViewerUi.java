package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    @FXML private Button btnYes, btnNo, btnExit;
    @FXML private HBox hBox;

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
        controller.getNext(false);
    }

    public void yesSelected() {
        controller.getNext(true);
    }

    public void lastCard() {
//        btnNo.setDisable(true);
//        btnNo.setVisible(false);
//        btnYes.setDisable(true);
//        btnYes.setVisible(false);
//        btnExit.setDisable(true);
//        btnExit.setVisible(false);

        hBox.getChildren().clear();
        Button button = new Button("FINISH GUIDE YO");
        button.setPrefSize(69, 69);
        hBox.getChildren().setAll(button);
        System.out.println("Hallå kommer det en ny knappe elleer?");
    }
}
