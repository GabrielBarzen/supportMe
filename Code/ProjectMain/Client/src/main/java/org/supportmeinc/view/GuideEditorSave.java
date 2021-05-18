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
import java.util.*;

public class GuideEditorSave implements JFXcontroller, Initializable {

    @FXML Button btnBack, btnSaveGuide, btnChooseFile, btnRemoveAccess, btnAddAccess;
    @FXML TextField txtTitle, txtFilePath, txtAccess;
    @FXML TextArea txtDescription;
    @FXML ImageView imgPreview;
    @FXML ListView<String> listViewCards;
    @FXML ListView<String> listViewAccess;
    private ArrayList<String> accessList = new ArrayList<>();
    private ArrayList<UUID> guideCardUUID = new ArrayList<>();
    private MainController controller;
    private Alert alert;
    private byte[] img = null;

    public GuideEditorSave() {
        listViewCards = new ListView<>();
        listViewAccess = new ListView<>();
    }

    public void initData(MainController controller) {
        this.controller = controller;
        controller.setGuideEditorSave(this);
    }

    public void updateTitlePreview() {
        String guideTitle = txtTitle.getText();

        if (guideTitle.length() > 20) {
            guideTitle = guideTitle.substring(0, 20);
            txtTitle.setText(guideTitle);

            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Guide title warning");
            alert.setHeaderText("Can't create guide with title longer than 20 characters");
            alert.setContentText("Please select a shorter title");
            alert.show();
        }
    }

    public void updateTextPreview() {
        String guideText = txtDescription.getText();

        if (guideText.length() > 160) {
            guideText = guideText.substring(0, 160);
            txtDescription.setText(guideText);

            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Guide text limit warning");
            alert.setHeaderText("Can't create guide with text longer than 160 characters");
            alert.show();
        }
    }

    public void saveGuide() {
        if(!(txtTitle.getText().isBlank() || txtDescription.getText().isBlank())) {
            String title = txtTitle.getText();
            String description = txtDescription.getText();
            UUID affirmUUID = null;

            if(listViewCards.getSelectionModel().getSelectedIndex() != -1) {
                if (guideCardUUID.get(listViewCards.getSelectionModel().getSelectedIndex()) != null) {
                    affirmUUID = guideCardUUID.get(listViewCards.getSelectionModel().getSelectedIndex());
                }
            }

            if(affirmUUID != null) {
                controller.packGuide(title, description, img, affirmUUID);

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
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Guide saved!");
                    alert.setHeaderText("Successful!");
                    alert.setContentText("Guide is saved");
                    alert.show();

                    controller.refreshThumbnails();
                    controller.toolbarSwitchSubscene(SceneName.guideBrowser);
                    controller.setNewGuideEditorModel();

                } else {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Guide couldn't be saved");
                    alert.setHeaderText("Warning!");
                    alert.setContentText("Guide couldn't be saved to server, please check connection");
                    alert.show();
                }
            } else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Could not save guide");
                alert.setHeaderText("Starting card not chosen");
                alert.setContentText("Please select a starting card");
                alert.show();
            }
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Could not save guide");
            alert.setHeaderText("No title or description");
            alert.setContentText("Please fill in a title and a description!");
            alert.show();
        }
    }

    public void onLoad() {
        accessList.addAll(Arrays.asList(controller.getAccessList()));
        repopulateLists();
    }

    public void clearGuideEditorSave() {
        listViewCards.getItems().clear();
        listViewAccess.getItems().clear();
        txtAccess.clear();
        txtTitle.clear();
        txtDescription.clear();
        img = null;
    }


    public void repopulateLists() {
        guideCardUUID = null;
        guideCardUUID = new ArrayList<>(Arrays.asList(controller.getGuideEditorCardUUIDs()));

        clearGuideEditorSave();

        for (UUID uuid : guideCardUUID) {
            if(uuid != null) {
                listViewCards.getItems().add(controller.getCardTitle(uuid));
            }
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
        Image image = ImageUtils.toImage(bytes);
        if (image != null) {
            imgPreview.setImage(image);
            txtFilePath.setText(image.toString());
        }
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
        controller.toolbarSwitchSubscene(SceneName.guideEditor);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
