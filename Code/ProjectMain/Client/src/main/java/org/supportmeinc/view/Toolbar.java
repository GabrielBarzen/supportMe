package org.supportmeinc.view;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.supportmeinc.ImageUtils;
import org.supportmeinc.MainController;
import org.supportmeinc.SceneName;

public class Toolbar implements JFXcontroller {

    private MainController controller;

    @FXML private BorderPane borderPane;
    @FXML private ImageView profilePicture;
    @FXML private Label userName;
    @FXML private Label viewTitle;

    public void initData(MainController controller){
        this.controller = controller;
        controller.registerToolbar(this);
        setProfilePicture(controller.getUserImage());
        setUserName(controller.getUsername());
    }

    public void setProfilePicture(byte[] imageBytes) {
        if (imageBytes == null) {
            imageBytes = ImageUtils.toBytes("FinalLogotyp.png");
        }
        Image image = ImageUtils.toImage(imageBytes);
        profilePicture.setImage(image);
    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    public void setViewTitle(String title) {
        this.viewTitle.setText(title);
    }

    public void homeButton() {
       borderPane.setCenter(controller.getScene(SceneName.guideBrowser));
       controller.refreshThumbnails();
    }

    public void logout() {
        controller.logout();
    }

    public void createNewGuide() {
        controller.initGuideEditor();
        borderPane.setCenter(controller.getScene(SceneName.guideEditor));
    }

    public void guideViewer() {
        borderPane.setCenter(controller.getScene(SceneName.guideViewer));
    }

    public void swapScene(AnchorPane pane) {
        borderPane.setCenter(pane);
    }

} //class end