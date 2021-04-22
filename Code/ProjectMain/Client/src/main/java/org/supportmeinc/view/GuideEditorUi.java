package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.supportmeinc.JfxUtils;
import org.supportmeinc.Main;
import shared.Card;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GuideEditorUi implements JFXcontroller, Initializable {

    private Main controller;

    @FXML private VBox cardList;
    @FXML private Label lblTitlePreview, lblCardTextPreview;
    @FXML private ImageView imgPreview;
    @FXML private TextField txtCardTitle, txtFilePath;
    @FXML private TextArea txtCardText;
    @FXML private ComboBox cmbYes, cmbNo;
    private ArrayList<Card> cards;
    private ArrayList<AnchorPane> btnList;


    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
        updateCardsList();
    }

    public GuideEditorUi() {

    }

    public void updateCardsList() {

        cards = controller.getCardsList();
        cardList.getChildren().clear();

        try {
            for (int i = 0; i < cards.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("view/buttonItem.fxml"));
                AnchorPane anchorPane = loader.load();

                ButtonItem btnItem = loader.getController();
                btnItem.setGuideEditorUi(this);
                Card card = cards.get(i);
                btnItem.setData(card.getTitle(), i);

                cardList.getChildren().add(anchorPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectImage() {
        File file = controller.jfxFileChooser();
        String fileName = file.toString();
        String extension = fileName.lastIndexOf(".");

        byte[] byteFile = JfxUtils.toBytes(file);
        Image img = JfxUtils.toImage(byteFile);
        imgPreview.setImage(img);
    }

    public void save() {
        String title = txtCardTitle.getText();
        String text = txtCardText.getText();


        lblTitlePreview.setText(title);
        lblCardTextPreview.setText(text);

        int counter = 0;

        if(cardList.getChildren().size() == 0) {
            controller.addCardToList(title, text, null, null, null);
        }

        for (int i = 0; i < cardList.getChildren().size(); i++) {
            System.out.println(cardList.getChildren().get(i));
            if(cardList.getChildren().get(i).isDisabled()){
                controller.updateCard(title, text, null, i);
            } else {
                counter++;
            }
            if(counter == i) {
                System.out.println("kommer du ens hit bror");
                controller.addCardToList(title, text, null, null, null);
            }
        }

        txtCardText.setText("Fill in text");
        txtCardTitle.setText("Fill in title");
        updateCardsList();
    }

    public void updateCardViewOnClick(int index) {
        cards = controller.getCardsList();
        Card card = cards.get(index);
        lblTitlePreview.setText(card.getTitle());
        lblCardTextPreview.setText(card.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        updateCardsList();
    }
}
