package org.supportmeinc.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.supportmeinc.JfxUtils;
import org.supportmeinc.Main;
import shared.Card;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class GuideEditorUi implements JFXcontroller, Initializable {

    private Main controller;

    @FXML private Label lblTitlePreview, lblCardTextPreview;
    @FXML private ImageView imgPreview;
    @FXML private TextField txtCardTitle, txtFilePath;
    @FXML private TextArea txtCardText;
    @FXML private ComboBox<Card> cmbYes, cmbNo;
    @FXML private ListView<Card> listView;




    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    public GuideEditorUi() {
        this.listView = new ListView<>();

    }

    public void populateListView() {
        listView.getItems().clear();
        for (Card card : controller.getCardsList().values()) {
            listView.getItems().add(card);
            System.out.println(card.getCardUUID());
        }


    }

    public void selectImage() {
        File file = controller.jfxFileChooser();
        String fileName = file.toString();
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index+1);
        System.out.println(extension);

        if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg")) {
            byte[] byteFile = JfxUtils.toBytes(file);
            Image img = JfxUtils.toImage(byteFile);
            imgPreview.setImage(img);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("File type warning");
            alert.setHeaderText("Could not add selected image to Card");
            alert.setContentText("Selected file must be of type .png or .jpg, please try again");
            alert.show();
        }

    }

    public void save() {
        String title = txtCardTitle.getText();
        String text = txtCardText.getText();

        lblTitlePreview.setText(title);
        lblCardTextPreview.setText(text);

        if(!listView.getSelectionModel().isSelected(listView.getSelectionModel().getSelectedIndex())) {
            controller.addCardToList(title, text, null, null, null);
        } else {
            System.out.println("Update card ifen");
            UUID cardUUID = listView.getSelectionModel().getSelectedItem().getCardUUID();
            controller.updateCard(title, text, null, null, null, cardUUID);
        }

        txtCardText.setText("Fill in text");
        txtCardTitle.setText("Fill in title");

        populateListView();

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        populateListView();
    }
}
