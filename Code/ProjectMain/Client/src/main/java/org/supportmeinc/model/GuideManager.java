package org.supportmeinc.model;

import shared.Card;
import shared.Guide;
import shared.Thumbnail;
import shared.User;

import java.util.UUID;
import java.util.ArrayList;
import java.util.UUID;

public class GuideManager {

    private Guide currentGuide;
    private Guide[] guides;
    private Thumbnail[] thumbnails; // fixa plz
    private Connection connection;
    private ArrayList<Card> cardArrayList;

    public GuideManager(Connection connection) {
        this.connection = connection;
        thumbnails = connection.getThumbnails();
        connection.setGuideManager(this);
        connection.send(new User("2@2.com","notExist","123456789"));
    }

    public Card initGuide(int index) {
        currentGuide = connection.getGuide(thumbnails[index].getGuideUUID());
        return currentGuide.getDescriptionCard();
    }

    public Card getCard(boolean choice) {
        Card card;
        card = currentGuide.getCard(choice);
        return card;
    }

    public Thumbnail[] getThumbnails() {
        thumbnails = connection.getThumbnails();
        return thumbnails;
    }

  
    public Guide getGuide(Thumbnail thumbnail) {
        UUID id = thumbnail.getGuideUUID();
        Guide guide = connection.getGuide(id);
        return guide;
    }

    public Card getDescriptionCard() {
        Card card;
        card = currentGuide.getDescriptionCard();
        return card;
    }

}

