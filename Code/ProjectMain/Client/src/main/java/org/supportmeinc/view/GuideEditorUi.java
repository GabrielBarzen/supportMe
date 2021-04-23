package org.supportmeinc.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.supportmeinc.JfxUtils;
import org.supportmeinc.Main;
import shared.Card;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GuideEditorUi implements JFXcontroller, Initializable {

    private Main controller;

    @FXML private Label lblTitlePreview, lblCardTextPreview;
    @FXML private ImageView imgPreview;
    @FXML private TextField txtCardTitle, txtFilePath;
    @FXML private TextArea txtCardText;
    @FXML private ComboBox<Card> cmbYes, cmbNo;
    @FXML private ListView<Card> listView;
    private ObservableList<String> listViewContent;



    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        populateListView();
    }

    public GuideEditorUi() {

    }

    public void populateListView() {

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

 //       if(!listView.isPressed()) {
        controller.addCardToList(title, text, null, null, null);
 //       } else {
 //           UUID cardUUID = listView.getSelectionModel().getSelectedItem().getCardUUID();
  //          controller.updateCard(title, text, null, null, null, cardUUID);
   //     }

        lblTitlePreview.setText(title);
        lblCardTextPreview.setText(text);

        txtCardText.setText("Fill in text");
        txtCardTitle.setText("Fill in title");

        listView.refresh();

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        updateCardsList();
    }
}
