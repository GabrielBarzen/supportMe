package org.supportmeinc.Controller;

import org.supportmeinc.Model.Card;
import org.supportmeinc.Model.Guide;
import org.supportmeinc.Model.Thumbnail;

import java.util.UUID;

public class GuideManager {

    private Guide currentGuide;
    private Thumbnail[] thumbnails;
    private Connection connection;

    public GuideManager() {
         connection = new Connection("localhost", 1028);
    }

    public Card getGuide(int index) {
        currentGuide = connection.getGuide(new UUID(555, 111)); //TODO ???
        return currentGuide.getDescriptionCard();
    }

    public Card getCard(boolean choice) {
        return null;
    }

    public Thumbnail[] getThumbnails() {
        return thumbnails;
    }

}
