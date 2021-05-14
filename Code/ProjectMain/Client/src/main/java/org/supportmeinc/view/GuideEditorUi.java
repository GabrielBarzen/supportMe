package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.supportmeinc.ImageUtils;
import org.supportmeinc.MainController;
import org.supportmeinc.SceneName;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class GuideEditorUi implements JFXcontroller, Initializable {

    private MainController controller;

    @FXML private Label lblTitlePreview, lblCardTextPreview, yesCardSelected, noCardSelected;
    @FXML private ImageView imgPreview;
    @FXML private TextField txtCardTitle, txtFilePath;
    @FXML private TextArea txtCardText;
    @FXML private ComboBox<String> cmbYes, cmbNo;
    @FXML private ListView<String> listView;

    private ArrayList<UUID> guideCardUUID;

    private Alert alert;

    private String title = null;
    private String text = null;
    private UUID yesUUID = null;
    private UUID noUUID = null;
    private UUID cardUUID;
    private byte[] img = null;


    public GuideEditorUi() {
        resetList();
    }

    public void resetList() {
        listView = new ListView<>();
        guideCardUUID = new ArrayList<>();
    }

    public void addToCardList(UUID cardToAdd) {
        UUID card = cardToAdd;
        guideCardUUID.add(cardToAdd);
        String title = controller.getCardTitle(card);
        if (!(listView.getItems().contains(title))) {
            if (controller.hasAffirmativeUUID(card) && controller.hasNegativeUUID(card)) {
                listView.getItems().add(title + " : 2");
            } else if (controller.hasAffirmativeUUID(card) || controller.hasNegativeUUID(card)) {
                listView.getItems().add(title + " : 1");
            } else {
                listView.getItems().add(title + " : 0");
            }
        }
        resetView();
    }

    public void removeFromCardList(UUID cardToAdd) {
        UUID card = cardToAdd;
        guideCardUUID.remove(cardToAdd);
        String title = controller.getCardTitle(card);
        listView.getItems().remove(title + " : 0");
        listView.getItems().remove(title + " : 1");
        listView.getItems().remove(title + " : 2");
        resetView();
    }

    public void initData(MainController controller){
        this.controller = controller;
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        controller.setGuideEditorUi(this);
    }

    public void resetView() {
        cmbYes.getItems().clear();
        cmbNo.getItems().clear();
        cmbYes.getItems().addAll(listView.getItems());
        cmbNo.getItems().addAll(listView.getItems());
    }

    public void updateEditGuide() {

    }

    public void updateTitlePreview() {
        String cardTitle = txtCardTitle.getText();

        if (cardTitle.length() > 20) {
            cardTitle = cardTitle.substring(0, 20);
            txtCardTitle.setText(cardTitle);

            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Card title warning");
            alert.setHeaderText("Can't create card with title longer than 20 characters");
            alert.setContentText("Please select a shorter title");
            alert.show();
        }

        title = cardTitle;
        lblTitlePreview.setText(cardTitle);
    }

    public void updateTextPreview() {
        String cardText = txtCardText.getText();

        if (cardText.length() > 160) {
            cardText = cardText.substring(0, 160);
            txtCardText.setText(cardText);

            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Card text limit warning");
            alert.setHeaderText("Can't create card with text longer than 160 characters");
            alert.setContentText("If your card is two steps, please divide them");
            alert.show();
        }

        text = cardText;
        lblCardTextPreview.setText(cardText);
    }

    public void updateComboboxPreview() {
        yesCardSelected.setText("");
        noCardSelected.setText("");
        if(yesUUID != null) {
            yesCardSelected.setText("Selected: " + controller.getCardTitle(yesUUID));
        } else {
            yesCardSelected.setText("Selected: ");
        }

        if(noUUID != null) {
            noCardSelected.setText("Selected: " + controller.getCardTitle(noUUID));
        } else {
            noCardSelected.setText("Selected: ");
        }
    }

    public void selectImage() {
        Image image = controller.jfxImageChooser();
        if (image != null) {
            imgPreview.setImage(image);
            txtFilePath.setText(image.toString());
        }
    }

    public void save() {
        if(!txtCardTitle.getText().isBlank()) {
            title = txtCardTitle.getText();
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No title added");
            alert.setHeaderText("Can't create card without title");
            alert.setContentText("Please fill in title");
            alert.show();
            return;
        }

        if(cmbYes.getSelectionModel().getSelectedIndex() != -1) {
            if (guideCardUUID.get(cmbYes.getSelectionModel().getSelectedIndex()) != null) {
                yesUUID = guideCardUUID.get(cmbYes.getSelectionModel().getSelectedIndex());
            }
        }
        if(cmbNo.getSelectionModel().getSelectedIndex() != -1) {
            if (guideCardUUID.get(cmbNo.getSelectionModel().getSelectedIndex()) != null) {
                noUUID = guideCardUUID.get(cmbNo.getSelectionModel().getSelectedIndex());
            }
        }
      
        controller.saveCard(title, text, img, yesUUID, noUUID, cardUUID);
        addToCardList(cardUUID);
        createNewCard();
        resetView();
    }

    public void loadCardOnListSelection() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex > -1) {
            cardUUID = guideCardUUID.get(selectedIndex);
            if (cardUUID != null) {
                removeFromCardList(cardUUID);

                title = controller.getCardTitle(cardUUID);
                text = controller.getCardText(cardUUID);
                img = controller.getCardImage(cardUUID);
                yesUUID = controller.getCardAffirmUUID(cardUUID);
                noUUID = controller.getCardNegUUID(cardUUID);

                txtCardText.setText(text);
                txtCardTitle.setText(title);
                imgPreview.setImage(ImageUtils.toImage(img));
                txtFilePath.clear();

                updateComboboxPreview();
                updateTextPreview();
                updateTitlePreview();
                resetView();
            }
        }
    }

    public void removeCard() {
        UUID cardUUID = guideCardUUID.get(listView.getSelectionModel().getSelectedIndex());
        if(cardUUID != null) {
            controller.removeCard(cardUUID);
            removeFromCardList(cardUUID);
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No card selected");
            alert.setHeaderText("Couldn't remove card");
            alert.setContentText("Please select a card to be deleted");
            alert.show();
        }
        resetView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void createNewCard() {
        title = null;
        text = null;
        yesUUID = null;
        noUUID = null;
        cardUUID = null;
        img = null;

        txtCardText.setText("");
        txtCardTitle.setText("");
        imgPreview.setImage(null);

        updateTitlePreview();
        updateTextPreview();
        txtFilePath.clear();

        cardUUID = controller.createNewCard();
    }

    public void saveGuide() {
        if(!listView.getItems().isEmpty() && controller.checkCardLinksValid()) {
            controller.switchScene(SceneName.guideEditorSave);
            controller.onLoadGuideEditorSave();
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Couldn't save cardlist");
            alert.setContentText("Couldn't save cardlist due to no cards or missing links between cards");
            alert.show();
        }
    }


}
