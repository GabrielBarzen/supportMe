package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.supportmeinc.JfxUtils;
import org.supportmeinc.Main;
import shared.Card;
import javafx.scene.control.Button;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GuideEditorUi implements JFXcontroller, Initializable {

    private Main controller;

    @FXML private FlowPane fpCardList;
    @FXML private VBox cardList;
    @FXML private Label lblTitlePreview, lblCardTextPreview;
    @FXML private ImageView imgPreview;
    @FXML private TextField txtCardTitle, txtFilePath;
    @FXML private TextArea txtCardText;
    @FXML private ComboBox cmbYes, cmbNo;

    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
        updateCardsList();
    }

    public GuideEditorUi() {

    }

    public void updateCardsList() {

        ArrayList<Card> cards = controller.getCardsList();
//        fpCardList.getChildren().clear();
        cardList.getChildren().clear();

        for (Card card: cards) {
            Button btn = new Button();
            btn.setText(card.getTitle());
            btn.setPrefSize(290, 40);
            btn.setMinSize(290, 40);

//            fpCardList.getChildren().add(btn);
            cardList.getChildren().add(btn);
        }
    }

    public void openFileChooser() {
        File file = controller.jfxFileChooser();
        byte[] byteFile = JfxUtils.toBytes(file);
        Image img = JfxUtils.fromBytes(byteFile);
        imgPreview.setImage(img);
    }

    public void save() {
        String title = lblTitlePreview.getText();
        String text = lblCardTextPreview.getText();
        Image img = imgPreview.getImage();

        controller.addCardToList(title, text, img, null, null);
        updateCardsList();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        updateCardsList();
    }
}
