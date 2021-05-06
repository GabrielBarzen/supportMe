package org.supportmeinc.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.supportmeinc.ImageUtils;
import org.supportmeinc.MainController;
import org.supportmeinc.SceneName;

import java.net.URL;
import java.util.Collection;
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
        hBox.getChildren().clear();
        Button button = new Button("Finish Guide");
        button.setPrefSize(150, 45);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetButtons();
                controller.switchScene(SceneName.guideBrowser);
                controller.refreshThumbnails();
            }
        });
        hBox.getChildren().setAll(button);
    }

    public void resetButtons() {
        hBox.getChildren().clear();
        hBox.getChildren().add(btnYes);
        hBox.getChildren().add(btnNo);
        hBox.getChildren().add(btnExit);
    }

    public void firstCard() {
        hBox.getChildren().clear();
        Button button = new Button("Start Guide");
        button.setPrefSize(150, 45);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetButtons();
                controller.getNext(true);
            }
        });
        hBox.getChildren().setAll(button);
    }
}
