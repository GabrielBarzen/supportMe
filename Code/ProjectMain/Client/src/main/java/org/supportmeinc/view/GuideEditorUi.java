package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.supportmeinc.ImageUtils;
import org.supportmeinc.MainController;
import shared.Card;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class GuideEditorUi implements JFXcontroller, Initializable {

    private MainController controller;

    @FXML private Label lblTitlePreview, lblCardTextPreview, yesCardSelected, noCardSelected;
    @FXML private ImageView imgPreview;
    @FXML private TextField txtCardTitle, txtFilePath;
    @FXML private TextArea txtCardText;
    @FXML private ComboBox<Card> cmbYes, cmbNo;
    @FXML private ListView<Card> listView;
    private Alert alert = new Alert(Alert.AlertType.WARNING);

    public GuideEditorUi() {
        this.listView = new ListView<>();
    }

    public void initData(MainController controller){
        this.controller = controller;
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void populateListView() {
        listView.getItems().clear();
        for (Card card : controller.getCardsList().values()) {
            listView.getItems().add(card);
        }
    }

    public void populateComboBoxes() {
        cmbYes.getItems().clear();
        cmbNo.getItems().clear();
        for (Card card : controller.getCardsList().values()) {
            if(card != listView.getSelectionModel().getSelectedItem()) {
                if(card.getAffirmUUID() == null) {
                    cmbYes.getItems().add(card);
                }

                if(card.getNegUUID() == null) {
                    cmbNo.getItems().add(card);
                }
            }
        }
    }

    public void selectImage() {
        File file = controller.jfxFileChooser();
        String fileName = file.toString();
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index+1);
        System.out.println(extension);

        if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg")) {
            byte[] byteFile = ImageUtils.toBytes(file);
            Image img = ImageUtils.toImage(byteFile);
            imgPreview.setImage(img);
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("File type warning");
            alert.setHeaderText("Could not add selected image to Card");
            alert.setContentText("Selected file must be of type .png or .jpg, please try again");
            alert.show();
        }

    }

    public void save() {
        String title;
        String text = txtCardText.getText();
        UUID yesUUID = null;
        UUID noUUID = null;
        File img = null;

        if(!txtCardTitle.getText().equals("")) {
            title = txtCardTitle.getText();
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No title added");
            alert.setHeaderText("Can't create card without title");
            alert.setContentText("Please fill in title");
            alert.show();
            return;
        }

        if (text.length() > 280) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Card text cannot be more than 280 characters");
			alert.setHeaderText("Can't create card with text more than 280 characters");
			alert.setContentText("If your card is two steps, please divide them");
			alert.show();
			return;
        }

        if(cmbYes.getSelectionModel().getSelectedItem() != null) {
            yesUUID = cmbYes.getSelectionModel().getSelectedItem().getCardUUID();
        }

        if(cmbNo.getSelectionModel().getSelectedItem() != null) {
            noUUID = cmbNo.getSelectionModel().getSelectedItem().getCardUUID();
        }

        //TODO: Add image, type File, might need new method in JfxUtils.

        if(!listView.getSelectionModel().isSelected(listView.getSelectionModel().getSelectedIndex())) {
            controller.addCardToList(title, text, img, yesUUID, noUUID);
        } else {
            UUID cardUUID = listView.getSelectionModel().getSelectedItem().getCardUUID();
            controller.updateCard(title, text, img, yesUUID, noUUID, cardUUID);
        }

        lblTitlePreview.setText(title);
        lblCardTextPreview.setText(text);

        populateListView();
        populateComboBoxes();
    }

    /*public void onClick() {
        if(listView.getSelectionModel().isSelected(listView.getSelectionModel().getSelectedIndex())) {
            if(listView.getSelectionModel().getSelectedItem() != null) {
                Card affirmCard = controller.getCardsList().get(listView.getSelectionModel().getSelectedItem().getAffirmUUID());
                if(affirmCard != null) {
                    yesCardSelected.setText(affirmCard.toString());
                }
            }
        }
    }*/

    public void removeCard() {
        if(listView.getSelectionModel().isSelected(listView.getSelectionModel().getSelectedIndex())) {
            controller.removeCard(listView.getSelectionModel().getSelectedItem().getCardUUID());
            populateListView();
            populateComboBoxes();
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No card selected");
            alert.setHeaderText("Couldn't remove card");
            alert.setContentText("Please select a card to be deleted");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        populateListView();
    }
}
