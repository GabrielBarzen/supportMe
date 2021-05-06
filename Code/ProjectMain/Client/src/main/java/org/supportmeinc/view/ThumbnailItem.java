package org.supportmeinc.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.*;
import org.supportmeinc.ImageUtils;
import java.util.Optional;
import java.util.UUID;

public class ThumbnailItem {

    @FXML private Label lblTitle;
    @FXML private AnchorPane anchPane;
    @FXML private ImageView imgThumb;
    @FXML private Label lblText;

    @FXML private ContextMenu contextMenu = new ContextMenu();
    @FXML private MenuItem openItem = new MenuItem();
    @FXML private MenuItem editItem = new MenuItem();
    @FXML private MenuItem deleteItem = new MenuItem();
    @FXML private MenuItem cancelItem = new MenuItem();

    private UUID guideUUID;
    private GuideBrowser guideBrowser;
    private boolean author;

    public void setData(String title, byte[] image, String text, UUID guideUUID, GuideBrowser guideBrowser, boolean author) {
        this.guideUUID = guideUUID;
        this.author = author;
        lblTitle.setText(title);
        imgThumb.setImage(ImageUtils.toImage(image));
        lblText.setText(text);

        openItem.setText("Open : " + lblTitle.getText());
        editItem.setText("Edit");
        deleteItem.setText("Delete");
        cancelItem.setText("Cancel");

        openItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                open();
            }
        });
        editItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                edit();
            }
        });
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                delete();
            }
        });
        cancelItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cancel();
            }
        });

        contextMenu.getItems().add(openItem);
        if (author) {
            contextMenu.getItems().add(editItem);
            contextMenu.getItems().add(deleteItem);
        }
        contextMenu.getItems().add(cancelItem);

        anchPane.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {

                contextMenu.show(anchPane, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            }
        });
        this.guideBrowser = guideBrowser;
    }

    private void open() {
        guideBrowser.openGuide(guideUUID);
    }

    private void edit() {
        guideBrowser.editGuide();
    }

    private void delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Are you sure you wish to delete this guide?");
        alert.setContentText("You have chosen to delete the guide" + lblTitle.getText() + "are you sure you wish to delete it?");
        alert.setX(anchPane.getLayoutX());
        alert.setY(anchPane.getLayoutY());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty() || result.get() != ButtonType.OK) {
        } else {

            guideBrowser.deleteGuide();
        }

    }

    private void cancel() {

    }

    public void setSelectedGuide() {
        guideBrowser.setSelectedThumbnail(guideUUID, author);
        anchPane.setStyle("-fx-border-color: black");
    }
}