package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.supportmeinc.JfxUtils;

public class ThumbnailItem {

    @FXML
    private Label lblTitle;

    @FXML
    private ImageView imgThumb;

    @FXML
    private Label lblText;

    private int listIndex; //represents index in list of thumbnails in GuideBrowser

    public void setData(String title, byte[] image, String text) {
        lblTitle.setText(title);
        imgThumb.setImage(JfxUtils.fromBytes(image));
        lblText.setText(text);
    }

    public void setData(String title, byte[] image, String text, int listIndex) {
        lblTitle.setText(title);
        imgThumb.setImage(JfxUtils.fromBytes(image));
        lblText.setText(text);
        this.listIndex = listIndex;
    }

    public int returnIndex() { //TODO connect class with GuideBrowser to return listIndex when clicking a thumbnail
        System.out.println(listIndex);
        return listIndex;
    }


}