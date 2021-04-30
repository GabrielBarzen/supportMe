package org.supportmeinc.model;

import shared.Card;
import shared.Guide;
import shared.Thumbnail;
import shared.User;

import java.io.*;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.UUID;

public class GuideManager {

    private Guide currentGuide;
    private Guide[] guides;
    private Thumbnail[] thumbnails;
    private Connection connection;
    private ArrayList<Card> cardArrayList;

	public GuideManager() {
        getDownloadedThumbnails();
    }

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

    public ArrayList<Thumbnail> getDownloadedThumbnails() {
	    String username = "user"; //TODO byt mot framtida lösning
        ArrayList<Thumbnail> thumbnails = new ArrayList<>();
	    try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(username + ".dat"));
            Object obj = ois.readObject();
            ArrayList<Guide> guides = new ArrayList<>();
            while (obj != null) {
                if (obj instanceof Guide) {
                    Guide guide = (Guide) obj;
                    guides.add(guide);
                    obj = ois.readObject();
                }
            }
            if (guides.size() > 0) {
                for (Guide guide : guides) {
                    thumbnails.add(guide.getThumbnail());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return thumbnails;
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
        if (!(newThumbs == null)) {
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

    public void saveGuide(Guide guide) {
	    boolean success = connection.saveGuide(guide);
        System.out.println(success);
    }

    public User getCurrentUser() {
	    return connection.getUser();
    }
}

