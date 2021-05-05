package org.supportmeinc.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import org.supportmeinc.ImageUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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


    private int listIndex; //represents index in list of thumbnails in GuideBrowser

    public void setData(String title, byte[] image, String text, int listIndex) {
        this.listIndex = listIndex;

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
        contextMenu.getItems().add(editItem);
        contextMenu.getItems().add(deleteItem);
        contextMenu.getItems().add(cancelItem);

        anchPane.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {

                contextMenu.show(anchPane, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            }
        });
    }

    private void open() {
        System.out.println("open " + lblTitle.getText());
    }

    private void edit() {
        System.out.println("edit " + lblTitle.getText());
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
            System.out.println("dont delete");
        } else {
            System.out.println("do delete");
        }

    }

    private void cancel() {
        System.out.println("cancel");
    }


    public int returnIndex() { //TODO connect class with GuideBrowser to return listIndex when clicking a thumbnail
        System.out.println(listIndex);
        return listIndex;
    }


}