package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.supportmeinc.model.JfxUtils;

public class ThumbnailController {

    @FXML
    private Label lblTitle;

    @FXML
    private ImageView imgThumb;

    @FXML
    private Label lblText;

    public void setData(String title, byte[] image, String text) {
        lblTitle.setText(title);
        imgThumb.setImage(JfxUtils.fromBytes(image));
        lblText.setText(text);
    }


}