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
    @FXML private ImageView imgProfilePicture;
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
        imgProfilePicture.setImage(image);
    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    private void setViewTitle(String title) {
        this.viewTitle.setText(title);
    }

    public void homeButton() {
       borderPane.setCenter(controller.getScene(SceneName.guideBrowser));
       controller.refreshThumbnails();
       setViewTitle("Guide Browser");
    }

    public void logout() {
        controller.logout();
    }

    public void swapScene(AnchorPane pane, SceneName sceneName) {
        borderPane.setCenter(pane);
        String name = returnSceneName(sceneName);
        setViewTitle(name);
    }

    private String returnSceneName(SceneName sceneName) {
        String returnName = "";
        switch (sceneName) {
            case guideViewer:
                returnName = "Guide Viewer";
                break;
            case guideBrowser:
                returnName = "Guide Browser";
                break;
            case guideEditor:
                returnName = "Guide Editor";
                break;
            case guideEditorSave:
                returnName = "Save Guide";
                break;
        }
        return returnName;
    }

} //class end