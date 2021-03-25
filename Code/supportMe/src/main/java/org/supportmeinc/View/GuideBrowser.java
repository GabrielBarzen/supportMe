package org.supportmeinc.View;

import org.supportmeinc.Controller.Client;
import org.supportmeinc.Main;

public class GuideBrowser implements JFXcontroller {

    Main controller;
    private Client client;

    public void initData(Main controller){
        this.controller = controller;
        controller.registerController(this);
        client = new Client();
    }

    public GuideBrowser(){

    }

    public void getGuide(int index) {
        client.getGuide(index);
    }

}
