package org.supportmeinc.view;

import org.supportmeinc.Main;

public class CardEditor implements JFXcontroller {

    Main controller;

    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
    }

    public CardEditor(){

    }
}
