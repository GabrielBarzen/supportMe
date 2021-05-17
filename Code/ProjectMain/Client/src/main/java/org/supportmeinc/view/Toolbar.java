package org.supportmeinc.view;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.supportmeinc.MainController;
import org.supportmeinc.SceneName;

public class Toolbar implements JFXcontroller {

    private MainController controller;

    @FXML private BorderPane borderPane;

    public void initData(MainController controller){
        this.controller = controller;
        controller.registerToolbar(this);
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