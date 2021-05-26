package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.supportmeinc.AlertUtils;
import org.supportmeinc.ImageUtils;
import org.supportmeinc.MainController;
import org.supportmeinc.SceneName;
import java.net.URL;
import java.util.*;

public class GuideEditorSave implements JFXcontroller, Initializable {

    @FXML Button btnBack, btnSaveGuide, btnChooseFile, btnRemoveAccess, btnAddAccess;
    @FXML TextField txtTitle, txtAccess;
    @FXML TextArea txtDescription;
    @FXML ImageView imgPreview;
    @FXML Label lblGuideTextPreview, lblTitlePreview, lblTitleLength, lblTextLength;
    @FXML ListView<String> listViewAccess;
    private ArrayList<String> accessList = new ArrayList<>();
    private MainController controller;
    private byte[] img = null;
    private boolean pressedBack = false;

    public GuideEditorSave() {
        listViewAccess = new ListView<>();
    }

    public void initData(MainController controller) {
        this.controller = controller;
        controller.setGuideEditorSave(this);
    }

    public void updateTitlePreview() {
        String guideTitle = txtTitle.getText();

        if (guideTitle.length() > 30) {
            guideTitle = guideTitle.substring(0, 30);
            txtTitle.setText(guideTitle);

            AlertUtils.alertWarning("Guide title warning", "Can't create guide with title longer than 30 characters", "Please select a shorter title");
        }

        lblTitleLength.setText(guideTitle.length() + "/30");
        lblTitlePreview.setText(guideTitle);
    }

    public void updateTextPreview() {
        String guideText = txtDescription.getText();

        if (guideText.length() > 160) {
            guideText = guideText.substring(0, 160);
            txtDescription.setText(guideText);


            AlertUtils.alertWarning("Guide text limit warning", "Over 160 characters","Can't create guide with text longer than 160 characters");
        }

        lblTextLength.setText(guideText.length() + "/160");
        lblGuideTextPreview.setText(guideText);
    }

    public void saveGuide() {
        if(!(txtTitle.getText().isBlank() || txtDescription.getText().isBlank())) {
            String title = txtTitle.getText();
            String description = txtDescription.getText();
            UUID affirmUUID = controller.getEditorFirstCard();

            if(affirmUUID != null) {
                controller.packGuide(title, description, img);

                if(controller.saveGuide()) {
                    ArrayList<String> accessServer = new ArrayList<>(Arrays.asList(controller.getAccessList()));
                    ArrayList<String> temp;

                    Collections.sort(accessServer);
                    Collections.sort(accessList);

                    temp = accessList;
                    temp.removeAll(accessServer);

                    for (String str : temp) {
                        controller.manageAccess(controller.getOutputGuideUUID(), str, true);
                    }

                    temp = accessServer;
                    temp.removeAll(accessList);

                    for (String str : temp) {
                        controller.manageAccess(controller.getOutputGuideUUID(), str, false);
                    }
                    pressedBack = false;
                    AlertUtils.alertInformation("Guide saved", "Your Guide has been saved", "Successful!");
                    controller.refreshThumbnails();
                    controller.toolbarSwitchSubscene(SceneName.guideBrowser);
                    controller.setNewGuideEditorModel();

                } else {
                    AlertUtils.alertWarning("Save warning", "Guide could not be saved", "Guide could not be saved, please check connection");
                }
            } else {
                AlertUtils.alertWarning("Could not save guide", "Starting card not chosen", "Please select a starting card");
            }
        } else {
            AlertUtils.alertWarning("Could not save guide", "No title or description", "Please fill in a title and a description");
        }
    }

    public void onLoad() {
        accessList.addAll(Arrays.asList(controller.getAccessList()));
        repopulateLists();
    }

    public void clearGuideEditorSave() {
        listViewAccess.getItems().clear();
        txtAccess.clear();
        txtTitle.clear();
        txtDescription.clear();
        img = null;
    }


    public void repopulateLists() {
        if(!pressedBack) {
            clearGuideEditorSave();
        }

        if(controller.getGuideEditor().getOutputGuide() != null) {
            txtTitle.setText(controller.getGuideEditor().getOutputGuide().getThumbnail().getTitle());
            txtDescription.setText(controller.getGuideEditor().getOutputGuide().getThumbnail().getDescription());
            img = controller.getImg();
        }

        updateAccessList();

        if(controller.getGuideEditor().getOutputGuide() != null) {
            txtTitle.setText(controller.getGuideTitle());
            txtDescription.setText(controller.getGuideDescription());
            imgPreview.setImage(ImageUtils.toImage(controller.getImg()));
            img = controller.getImg();
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

    public void removeImage() {
        imgPreview.setImage(null);
        img = null;
    }

    public void updateAccessList() {
        if(accessList != null) {
            listViewAccess.getItems().clear();
            for (String str : accessList) {
                listViewAccess.getItems().add(str);
            }
        }
        txtAccess.clear();
    }

    public void addToAccessList() {
        if(!txtAccess.getText().isBlank()) {
            accessList.add(txtAccess.getText());

        }
        updateAccessList();
    }

    public void removeFromAccessList() {
        if(listViewAccess.getSelectionModel().isSelected(listViewAccess.getSelectionModel().getSelectedIndex())) {
            accessList.remove(listViewAccess.getSelectionModel().getSelectedItem());
        }
        updateAccessList();
    }

    public void back() {
        this.pressedBack = true;
        controller.toolbarSwitchSubscene(SceneName.guideEditor);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
