package org.supportmeinc.view;

import org.supportmeinc.Main;

public class Login implements JFXcontroller {

    Main controller;

    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
    }

}

