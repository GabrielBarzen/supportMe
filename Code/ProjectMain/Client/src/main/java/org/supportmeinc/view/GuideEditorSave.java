package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.supportmeinc.ImageUtils;
import org.supportmeinc.MainController;
import org.supportmeinc.SceneName;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.UUID;

public class GuideEditorSave implements JFXcontroller, Initializable {

    @FXML Button btnBack, btnSaveGuide, btnChooseFile;
    @FXML TextField txtTitle, txtFilePath, txtAccess;
    @FXML TextArea txtDescription;
    @FXML ImageView imgPreview;
    @FXML ListView<String> listView;
    private ArrayList<UUID> guideCardUUID = new ArrayList<>();
    private MainController controller;
    private Alert alert;
    private byte[] img = null;

    public GuideEditorSave() {
        listView = new ListView();
    }

    public void initData(MainController controller) {
        this.controller = controller;
        controller.setGuideEditorSave(this);
    }

    public void saveGuide() {
        if(!(txtTitle.getText().isBlank() || txtDescription.getText().isBlank())) {
            String title = txtTitle.getText();
            String description = txtDescription.getText();
            UUID affirmUUID = null;

            if(listView.getSelectionModel().getSelectedIndex() != -1) {
                if (guideCardUUID.get(listView.getSelectionModel().getSelectedIndex()) != null) {
                    affirmUUID = guideCardUUID.get(listView.getSelectionModel().getSelectedIndex());
                }
            }

            if(affirmUUID != null) {
                if(controller.packGuide(title, description, img, affirmUUID)) {
                    System.out.println("Kommer du innanför packGuide?");
                    if(controller.saveGuide()) {
                        System.out.println("Kommer du innanför saveGuide?");
                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Guide saved!");
                        alert.setHeaderText("Successful!");
                        alert.setContentText("Guide is saved");
                        alert.show();
                        controller.switchScene(SceneName.guideBrowser);
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
                    alert.setTitle("Guide couldn't be saved");
                    alert.setHeaderText("Card links missing");
                    alert.setContentText("More than one card is missing links to next card in guide!");
                    alert.show();
                }
            } else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Could not save guide");
                alert.setHeaderText("Starting card not chosen");
                alert.setContentText("Please select a starting card");
                alert.show();
            }
        }
    }

    public void onLoad() {
        repopulateLists();
    }

    public void repopulateLists() {
        guideCardUUID = null;
        guideCardUUID = new ArrayList<>(Arrays.asList(controller.getGuideEditorCardUUIDs()));

        listView.getItems().clear();
        txtTitle.clear();
        txtDescription.clear();
        txtFilePath.clear();
        txtAccess.clear();

        for (UUID uuid : guideCardUUID) {
            System.out.println(uuid);
            if(uuid != null) {
                listView.getItems().add(controller.getCardTitle(uuid));
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
            Image image = ImageUtils.toImage(byteFile);
            imgPreview.setImage(image);
            txtFilePath.setText(fileName);
            img = ImageUtils.toBytes(file);

        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("File type warning");
            alert.setHeaderText("Could not add selected image to Card");
            alert.setContentText("Selected file must be of type .png or .jpg, please try again");
            alert.show();
        }
    }

    public void back() {
        controller.switchScene(SceneName.guideEditor);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
