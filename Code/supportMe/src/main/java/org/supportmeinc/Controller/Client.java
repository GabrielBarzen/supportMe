package org.supportmeinc.Controller;

import org.supportmeinc.Model.Card;

public class Client {

    private GuideManager guideManager;

    public Client() {
        guideManager = new GuideManager();
    }

    public void getGuide(int index) {

        guideManager.getGuide(index);
    }

    public Card getCard(boolean choice) {
        return null;
    }

    public void exitCardView() {

    }

} //class end
