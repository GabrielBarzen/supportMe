package org.supportmeinc.model;

import shared.Card;
import shared.Guide;
import shared.Thumbnail;

import java.util.ArrayList;
import java.util.UUID;

public class GuideManager {

    private Guide currentGuide;
    private Guide[] guides;
    private Thumbnail[] thumbnails;
    private Connection connection;
    private ArrayList<Card> cardArrayList;

    public GuideManager(Connection connection) {
        this.connection = connection;
        thumbnails = new Thumbnail[0];
        connection.setGuideManager(this);

        //getThumbnails();

        UUID guideUUID = UUID.fromString("a860a789-fea8-42e3-8a40-43ffa3e4f3bf");
        System.out.println("GuideManager running");

        //TODO: Obs måste köra server om dessa block är avkommenterade
        /*        try {
            thumbnails = connection.getThumbnails(thumbnails);
            System.out.println("got thumbnails from server");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(thumbnails.length);
            Guide guide = connection.getGuide(thumbnails[0]);
            System.out.println(guide.getDescriptionCard().getTitle());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }


    public Card getCard(boolean choice) {
        return null;
    }

    public Thumbnail[] getThumbnails() {
        Thumbnail[] newThumbs = null;
        try {
            newThumbs = connection.getThumbnails(thumbnails);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!(newThumbs == null)){
            thumbnails = newThumbs;
        }
        return thumbnails;
    }

    public Guide getGuide(int i) {
        Guide returnGuide = null;
        //TODO: Obs måste köra server om dessa block är avkommenterade
        /*try {
            returnGuide = connection.getGuide(thumbnails[i]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return new Guide();
    }
}

