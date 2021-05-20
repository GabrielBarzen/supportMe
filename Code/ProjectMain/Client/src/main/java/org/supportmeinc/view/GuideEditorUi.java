package org.supportmeinc.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.supportmeinc.AlertUtils;
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
    @FXML private ListView<String> cardList;

    private ArrayList<UUID> guideCardUUID;

    private String title = null;
    private String text = null;
    private UUID yesUUID = null;
    private UUID noUUID = null;
    private UUID cardUUID;
    private byte[] img = null;

    public void initData(MainController controller){
        this.controller = controller;
        resetList();
        cardList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        controller.setGuideEditorUi(this);
    }

    public GuideEditorUi() {
    }


    public void initializeEditor() {
        resetList();
        createNewCard();
    }

    public void resetList() {
        cardList.getItems().clear();
        guideCardUUID = new ArrayList<>();
    }

    public void addToCardList(UUID cardToAdd) {
        UUID card = cardToAdd;
        guideCardUUID.add(cardToAdd);
        String title = controller.getCardTitle(card);
        if (!(cardList.getItems().contains(title))){
            cardList.getItems().add(title);
        }
        resetView();
    }

    public void removeFromCardList(UUID cardToAdd) {
        UUID card = cardToAdd;
        guideCardUUID.remove(cardToAdd);
        String title = controller.getCardTitle(card);
        cardList.getItems().remove(title);
        resetView();
    }



    public void resetView() {
        cmbYes.getItems().clear();
        cmbNo.getItems().clear();
        cmbYes.getItems().addAll(cardList.getItems());
        cmbNo.getItems().addAll(cardList.getItems());
    }

    public void updateTitlePreview() {
        String cardTitle = txtCardTitle.getText();

        if (cardTitle.length() > 20) {
            cardTitle = cardTitle.substring(0, 20);
            txtCardTitle.setText(cardTitle);

            AlertUtils.alertWarning("Card title warning", "Can't create card with title longer than 20 characters", "Please select a shorter title");
        }

        title = cardTitle;
        lblTitlePreview.setText(cardTitle);
    }

    public void updateTextPreview() {
        String cardText = txtCardText.getText();

        if (cardText.length() > 160) {
            cardText = cardText.substring(0, 160);
            txtCardText.setText(cardText);

            AlertUtils.alertWarning("Card text limit warning", "Can't create card with text longer than 160 characters", "If your card is two steps, please divide them");
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
        byte[] bytes = controller.jfxImageChooser();
        img = bytes;
        Image image = ImageUtils.toImage(bytes);
        if (image != null) {
            imgPreview.setImage(image);
            txtFilePath.setText(image.toString());
        }
    }

    public boolean saveCard() {
        boolean clear = false;
        if(!txtCardTitle.getText().isBlank()) {
            title = txtCardTitle.getText();
            System.out.println("saving: " + title);
            if(cmbYes.getSelectionModel().getSelectedIndex() != -1) {
                UUID cardUUID = guideCardUUID.get(cmbYes.getSelectionModel().getSelectedIndex());
                if (cardUUID != null) {
                    yesUUID = cardUUID;
                }
            }
            if(cmbNo.getSelectionModel().getSelectedIndex() != -1) {
                UUID cardUUID = guideCardUUID.get(cmbNo.getSelectionModel().getSelectedIndex());
                if (cardUUID != null) {
                    noUUID = cardUUID;
                }
            }

            controller.saveCard(title, text, img, yesUUID, noUUID, cardUUID);
            addToCardList(cardUUID);
            resetView();
            clear = true;

        } else {
            clear = AlertUtils.alertConfirmation("No title added", "Can't create card without title", "Discard changes?");
        }

        return clear;
    }

    public void loadCardInfo(UUID selectedUUID) {
        this.cardUUID = selectedUUID;

        title = controller.getCardTitle(selectedUUID);
        text = controller.getCardText(selectedUUID);
        img = controller.getCardImage(selectedUUID);
        yesUUID = controller.getCardAffirmUUID(selectedUUID);
        noUUID = controller.getCardNegUUID(selectedUUID);

        txtCardText.setText(text);
        txtCardTitle.setText(title);
        imgPreview.setImage(ImageUtils.toImage(img));
        txtFilePath.clear();

        updateComboboxPreview();
        updateTextPreview();
        updateTitlePreview();
        resetView();
    }

    public void removeCard() {
        UUID cardUUID = guideCardUUID.get(cardList.getSelectionModel().getSelectedIndex());
        if(cardUUID != null) {
            removeFromCardList(cardUUID);
            controller.removeCard(cardUUID);
        } else {
            AlertUtils.alertWarning("No card selected", "Couldn't remove card", "Please select a card to be deleted");
        }
        resetView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    public void saveGuide() {
        if(!cardList.getItems().isEmpty() && controller.checkCardLinksValid()) {
            controller.toolbarSwitchSubscene(SceneName.guideEditorSave);
            controller.onLoadGuideEditorSave();
        } else {
            AlertUtils.alertWarning("Couldn't save guide", "Couldn't save guide due to no cards or missing links between cards", "Please make sure there are no more than one card missing links");
        }
    }

    public void removeImage(ActionEvent actionEvent) {
        imgPreview.setImage(null);
        img = null;
    }

    public void setFirstCard(ActionEvent actionEvent) {
        controller.setEditorFirstCard(cardUUID);
    }

    public void addNewCard(ActionEvent actionEvent) {
        saveCard();
        createNewCard();
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

    public void openCard(MouseEvent mouseEvent) {
        boolean clear = saveCard();
        if (clear) {
            int index = cardList.getSelectionModel().getSelectedIndex();
            if (index > -1) {
                UUID selectedUUID = guideCardUUID.get(index);
                System.out.println(selectedUUID);
                loadCardInfo(selectedUUID);
            } else {
                AlertUtils.alertConfirmation("No card selected", "You have not selected a card", "Select a card from the card-list");
            }
        }
    }
}