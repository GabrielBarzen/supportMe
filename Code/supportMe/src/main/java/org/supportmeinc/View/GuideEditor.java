package org.supportmeinc.View;

import org.supportmeinc.Main;

public class GuideEditor implements JFXcontroller {

    Main controller;

    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
    }

    public GuideEditor(){

    }
}