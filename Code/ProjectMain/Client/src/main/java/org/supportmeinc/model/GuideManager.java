package org.supportmeinc.model;

import org.supportmeinc.MainController;
import shared.Card;
import shared.Guide;
import shared.Thumbnail;
import shared.User;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Semaphore;

public class GuideManager implements ThumbnailListener{

    private Guide currentGuide;
    private Guide[] guides;
    private Thumbnail[] accessThumbnails;
    private Thumbnail[] authorThumbnails;
    private Thumbnail[] downloadThumbnails;
    private Connection connection;
    private ArrayList<Card> cardArrayList;
    private Semaphore newAccess = new Semaphore(0);
    private Semaphore newAuthor = new Semaphore(0);
    private MainController controller;

    public GuideManager(Connection connection) {
        this.connection = connection;
        connection.registerListener(this);
        accessThumbnails = new Thumbnail[0];
        connection.setGuideManager(this);
    }

    public GuideManager(User user) {
        getDownloadedThumbnails(user);
        //setThumbnails(downloadThumbnails);
    }

    private void setThumbnails(Thumbnail[] downloadThumbnails) {
        controller.setThumbnailInView(downloadThumbnails);
    }

    public Thumbnail[] getDownloadedThumbnails(User user) {
        ArrayList<Thumbnail> thumbnails = new ArrayList<>();
	    try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(user.getUserName() + ".dat"));
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
	    Thumbnail[] thumbnailArray = new Thumbnail[thumbnails.size()];
        for (int i = 0; i < thumbnailArray.length; i++) {
            thumbnailArray[i] = thumbnails.get(i);
        }
        return thumbnailArray;
    }

    public Card getCard(boolean choice) {
        return null;
    }

    public void getThumbnails() {
	    try {
            connection.getThumbnails(accessThumbnails);
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
    }

    public Guide getGuide(UUID uuid) {
        Guide returnGuide = null;
        try {
            returnGuide = connection.getGuide(new Thumbnail(uuid));
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return returnGuide;
    }

    public boolean saveGuide(Guide guide) {
	    boolean success = connection.saveGuide(guide);
        return success;
    }

    public User getCurrentUser() {
	    return connection.getUser();
    }

    @Override
    public void thumbnailsReceived(Thumbnail[] access, Thumbnail[] author) {
	    this.accessThumbnails = access;
        this.authorThumbnails = author;
        newAccess.release();
        newAuthor.release();
    }

    public Thumbnail[] getAccessThumbnails() {
	    Thumbnail[] thumbnails = null;
        try {
            newAccess.acquire();
            thumbnails = accessThumbnails;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return thumbnails;
    }

    public Thumbnail[] getAuthorThumbnails() {
        Thumbnail[] thumbnails = null;
        try {
            newAuthor.acquire();
            thumbnails = authorThumbnails;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return thumbnails;
    }

    public void disconnect() {
	    if (connection != null) {
            try {
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteGuide(UUID uuid) {
	    connection.removeGuide(uuid);
    }

    public void downloadGuide(UUID uuid) {
        connection.downloadGuide(uuid);
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }
}

