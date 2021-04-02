package org.supportmeinc.view;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.supportmeinc.Main;

import java.io.IOException;

public class Toolbar implements JFXcontroller {

    private Main controller;

    @FXML
    private BorderPane borderPane; //Swap FXML in CENTER of borderpane to switch scenes, use ANCHORPANE as parent

    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
    }

    public void homeButton() throws IOException {
//        anchorPane.getChildren().setAll(controller.loadFXML("guideBrowser"));
        borderPane.setCenter(controller.loadFXML("newCardViewer"));
        controller.testCard();
//        anchorPane.getChildren().add(controller.loadFXML("guideBrowser"));
    }

} //class end