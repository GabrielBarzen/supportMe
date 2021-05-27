package org.supportmeinc.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.*;
import org.supportmeinc.AlertUtils;
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
    private String defaulStyle = "-fx-background-color: #aaaaaa;-fx-border-color:#a1a1a1 ; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5; -fx-border-width: 2";

    public void setData(String title, byte[] image, String text, UUID guideUUID, GuideBrowser guideBrowser, boolean author) {
        anchPane.setStyle(defaulStyle);
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
        boolean confirmDelete = AlertUtils.alertConfirmation("Delete guide", "Are you sure you wish to delete this guide?", "Are you sure you wish to delete selected guide?");

        if(confirmDelete) {
            guideBrowser.deleteGuide();
        }

    }

    private void cancel() {

    }

    public void setSelectedGuide() {
        guideBrowser.setSelectedThumbnail(this);
        anchPane.setStyle("-fx-border-color: #444444; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5; -fx-border-width: 2; -fx-background-color: #9e9e9e");
    }

    public boolean getAuthor() {
        return author;
    }

    public UUID getUUID() {
        return guideUUID;
    }

    public void deSelected() {
        anchPane.setStyle(defaulStyle);

    }
}