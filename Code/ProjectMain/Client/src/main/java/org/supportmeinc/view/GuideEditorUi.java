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
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class GuideEditorUi implements JFXcontroller, Initializable {

    private MainController controller;

    @FXML private Label lblTitlePreview, lblCardTextPreview, yesCardSelected, noCardSelected, lblDescLength, lblTitleLength;
    @FXML private ImageView imgPreview;
    @FXML private TextField txtCardTitle;
    @FXML private TextArea txtCardText;
    @FXML private ComboBox<String> cmbYes, cmbNo;
    @FXML private ListView<String> cardList;

    private CardMap<String, UUID> cardMap;

    private String title = null;
    private String text = null;
    private UUID yesUUID = null;
    private UUID noUUID = null;
    private UUID cardUUID;
    private byte[] img = null;
    private boolean loaded = false;
    private boolean modified = false;

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
        setFirstCard(null);
    }

    public void resetList() {
        cardList.getItems().clear();
        cardMap = new CardMap<>();
    }

    public void addCardToMap(UUID cardValue) {
        String key = controller.getCardTitle(cardValue);
        System.out.println(key);
        if (cardMap.containsValue(cardValue)) {
            cardList.getItems().remove(cardMap.replaceOnValue(cardValue, key));
        } else {
            cardMap.put(key, cardValue);
        }
        if (!cardList.getItems().contains(key)) {
            cardList.getItems().add(key);
        }

        System.out.println("called cardList.getItems().add("+key+") in addcardtoMap");
        resetView();
    }

    public void removeCardFromMap(UUID cardValue) {
        String key = controller.getCardTitle(cardValue);
        cardMap.remove(key);
        cardList.getItems().remove(key);
        resetView();
    }

    public void resetView() {
        cmbYes.getItems().clear();
        cmbNo.getItems().clear();
        List<String> obvList = cardList.getItems();
        ArrayList<String> cmbList = new ArrayList<>(obvList);
        cmbList.remove(title);
        cmbYes.getItems().addAll(cmbList);
        cmbNo.getItems().addAll(cmbList);
    }

    public void updateTitlePreview() {
        modified = true;
        String cardTitle = txtCardTitle.getText();

        if (cardTitle.length() > 30) {
            cardTitle = cardTitle.substring(0, 30);
            txtCardTitle.setText(cardTitle);

            AlertUtils.alertWarning("Card title warning", "Can't create card with title longer than 30 characters", "Please select a shorter title");
        }

        lblTitleLength.setText(cardTitle.length() + "/30");
        title = cardTitle;
        lblTitlePreview.setText(cardTitle);
    }

    public void updateTextPreview() {
        modified = true;
        String cardText = txtCardText.getText();


        if (cardText.length() > 160) {
            cardText = cardText.substring(0, 160);
            txtCardText.setText(cardText);

            AlertUtils.alertWarning("Card text limit warning", "Can't create card with text longer than 160 characters", "If your card is two steps, please divide them");
        }

        lblDescLength.setText(cardText.length() + "/160");
        text = cardText;
        lblCardTextPreview.setText(cardText);
    }

    public void updateComboboxPreview() {
        modified = true;
        String getYesTitle =  controller.getCardTitle(yesUUID);
        String getNoTitle = controller.getCardTitle(noUUID);

        if(getYesTitle != null) {
            yesCardSelected.setText("Selected: " + getYesTitle);
        } else {
            yesCardSelected.setText("Selected: ");
        }

        if(getNoTitle != null) {
            noCardSelected.setText("Selected: " + getNoTitle);
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
        }
    }

    public boolean saveCard() {
        boolean clear = false;
        if(!txtCardTitle.getText().isBlank()) {
            title = txtCardTitle.getText();
            System.out.println("saving: " + title);
            if(cmbYes.getSelectionModel().getSelectedIndex() != -1) {
                UUID cardUUID = cardMap.get(cmbYes.getSelectionModel().getSelectedItem());
                if (cardUUID != null) {
                    yesUUID = cardUUID;
                }
            }
            if(cmbNo.getSelectionModel().getSelectedIndex() != -1) {
                UUID cardUUID = cardMap.get(cmbNo.getSelectionModel().getSelectedItem());
                if (cardUUID != null) {
                    noUUID = cardUUID;
                }
            }
            if (loaded || (!loaded && !cardList.getItems().contains(title))) {
                controller.saveCard(title, text, img, yesUUID, noUUID, cardUUID);
                addCardToMap(cardUUID);
                clear = true;
            } else if (!loaded && cardList.getItems().contains(title)){
                clear = AlertUtils.alertConfirmation("Card Exists with title", "You already have a card with this title!", "Replace card or cancel?");
                if (clear) {
                    controller.saveCard(title, text, img, yesUUID, noUUID, cardUUID);
                    addCardToMap(cardUUID);
                }
            }


        } else {
            if (modified) {
                clear = AlertUtils.alertConfirmation("No title added", "Can't create card without title", "Discard changes?");
            } else {
                clear = true;
            }
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
        loaded = true;

        txtCardText.setText(text);
        txtCardTitle.setText(title);
        imgPreview.setImage(ImageUtils.toImage(img));

        updateComboboxPreview();
        updateTextPreview();
        updateTitlePreview();
        resetView();
    }

    public void removeCard() {
        UUID cardUUID = cardMap.get(cardList.getSelectionModel().getSelectedItem());
        if(cardUUID != null) {
            removeCardFromMap(cardUUID);
            controller.removeCard(cardUUID);
            loadCardInfo(controller.getEditorFirstCard());
        } else {
            AlertUtils.alertWarning("No card selected", "Couldn't remove card", "Please select a card to be deleted");
        }
        resetView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }





    public void removeImage(ActionEvent actionEvent) {
        imgPreview.setImage(null);
        img = null;
    }

    public void setFirstCard(ActionEvent actionEvent) {
        controller.setEditorFirstCard(cardUUID);
    }

    public void addNewCard() {
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
        loaded = false;

        txtCardText.setText("");
        txtCardTitle.setText("");
        imgPreview.setImage(null);

        updateTitlePreview();
        updateTextPreview();

        cardUUID = controller.createNewCard();
        modified = false;
    }

    public void openCard(MouseEvent mouseEvent) {
        boolean clear = saveCard();
        if (clear) {
            String item = cardList.getSelectionModel().getSelectedItem();
            if (item != null) {
                UUID selectedUUID = cardMap.get(item);
                System.out.println(selectedUUID);
                loadCardInfo(selectedUUID);
            } else {
                AlertUtils.alertConfirmation("No card selected", "You have not selected a card", "Select a card from the card-list");
            }
        }

    }

    public void finalizeCheck() {
        if(saveCard()) {
            finalizeGuide();
        } else {
            AlertUtils.alertWarning("Couldn't finalize guide", "Please make sure you've finished your cars before moving on", "något blaha här");
        }
    }

    public void finalizeGuide() {
        if(!cardList.getItems().isEmpty() && controller.checkCardLinksValid()) {
            controller.toolbarSwitchSubscene(SceneName.guideEditorSave);
            controller.onLoadGuideEditorSave();
        } else {
            AlertUtils.alertWarning("Couldn't save guide", "Couldn't save guide due to no cards or missing links between cards", "Please make sure there are no more than one card missing links");
        }
    }
}