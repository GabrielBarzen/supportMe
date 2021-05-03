package org.supportmeinc.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import org.supportmeinc.ImageUtils;

import java.util.ArrayList;
import java.util.Collection;

public class ThumbnailItem {

    @FXML private Label lblTitle;
    @FXML private AnchorPane anchPane;
    @FXML private ImageView imgThumb;
    @FXML private Label lblText;

    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem openItem = new MenuItem();
    private MenuItem editItem = new MenuItem();
    private MenuItem deleteItem = new MenuItem();
    private MenuItem cancelItem = new MenuItem();


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



    public int returnIndex() { //TODO connect class with GuideBrowser to return listIndex when clicking a thumbnail
        System.out.println(listIndex);
        return listIndex;
    }


}