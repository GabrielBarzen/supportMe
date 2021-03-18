package org.supportmeinc.View;

import javafx.fxml.FXML;
import org.supportmeinc.Main;

import java.io.IOException;

public class Login implements JFXcontroller {

    Main controller;

    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
    }

}

