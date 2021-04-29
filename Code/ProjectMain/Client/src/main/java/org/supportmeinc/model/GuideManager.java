package org.supportmeinc.model;

import shared.Card;
import shared.Guide;
import shared.Thumbnail;
import shared.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.UUID;
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
        getThumbnails();
        try {
            System.out.println(thumbnails.length);
            Guide guide = connection.getGuide(thumbnails[0]);
            System.out.println(guide.getDescriptionCard().getTitle());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        try {
            returnGuide = connection.getGuide(thumbnails[i]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnGuide;
    }
}

