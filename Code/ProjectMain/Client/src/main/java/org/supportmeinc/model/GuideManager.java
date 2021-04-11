package org.supportmeinc.model;

import shared.Card;
import shared.Guide;
import shared.Thumbnail;
import shared.User;

import java.io.*;
import java.util.UUID;
import java.util.ArrayList;
import java.util.UUID;

public class GuideManager {

    private Guide currentGuide;
    private Guide[] guides;
    private Thumbnail[] thumbnails; // fixa plz
    private Connection connection;

    public GuideManager(Connection connection) {
        this.connection = connection;
        thumbnails = connection.getThumbnails();
        connection.setGuideManager(this);
        connection.send(new User("2@2.com","notExist","123456789"));
    }

    //really don't know about this method, will ask you in
    // the code review or before we merge them if this is
    // actually the best way to do it
    public Card initGuide(int index, Thumbnail thumbnail) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("path/to/saved/guides")));
            Object object = inputStream.readObject();
            while (object != null) {
                if (object instanceof Guide) {
                    Guide guide = (Guide) object;
                    if (guide.getThumbnail().equals(thumbnail)) {
                        return guide.getDescriptionCard();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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

