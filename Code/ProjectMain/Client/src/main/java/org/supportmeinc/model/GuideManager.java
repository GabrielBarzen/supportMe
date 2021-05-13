package org.supportmeinc.model;

import org.supportmeinc.MainController;
import shared.Guide;
import shared.Thumbnail;
import shared.User;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Semaphore;

public class GuideManager implements ThumbnailListener{

    private User user;
    private MainController controller;
    private Guide[] guides;
    private Thumbnail[] accessThumbnails;
    private Thumbnail[] authorThumbnails;
    private Thumbnail[] downloadThumbnails;
    private Connection connection;
    private Semaphore newAccess = new Semaphore(0);
    private Semaphore newAuthor = new Semaphore(0);
    private boolean hasOfflineGuides = false;

    public GuideManager(Connection connection) {
        this.connection = connection;
        connection.registerListener(this);
        accessThumbnails = new Thumbnail[0];
        connection.setGuideManager(this);
    }

    public GuideManager(User user) {
        this.user = user;
        this.downloadThumbnails = getDownloadedThumbnails(user);
    }

    public Thumbnail[] getDownloadedThumbnails(User user) {
        ArrayList<Thumbnail> thumbnails = new ArrayList<>();
	    try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(user.getEmail() + ".dat"));
            ArrayList<Guide> guidesArrayList = new ArrayList<>();
            try {
                Object obj;
                do {
                    obj = ois.readObject();
                    if (obj instanceof Guide) {
                        Guide guide = (Guide) obj;
                        guidesArrayList.add(guide);
                    }
                } while (ois.available() > 0);
                hasOfflineGuides = true;
            } catch (EOFException e) {
                e.printStackTrace();
            }

            guides = new Guide[guidesArrayList.size()];
            for (int i = 0; i < guidesArrayList.size(); i++) {
                guides[i] = guidesArrayList.get(i);
            }

            if (guidesArrayList.size() > 0) {
                for (Guide guide : guidesArrayList) {
                    thumbnails.add(guide.getThumbnail());
                }
            }
        } catch (FileNotFoundException e) {
	        System.out.println("no downloaded guides");
            hasOfflineGuides = false;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        Thumbnail[] thumbnailArray = new Thumbnail[thumbnails.size()];
        for (int i = 0; i < thumbnailArray.length; i++) {
            thumbnailArray[i] = thumbnails.get(i);
        }

        return thumbnailArray;
    }

    public void refreshThumbnails() {
	    try {
	        connection.getThumbnails(accessThumbnails);
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
    }

    public Guide getGuide(UUID uuid) {
        Guide returnGuide = null;

        try {
            if (connection != null) {
                returnGuide = connection.getGuide(new Thumbnail(uuid));
            } else {
                for (int i = 0; i < guides.length; i++) {
                    if (guides[i].getGuideUUID().equals(uuid)) {
                        returnGuide = guides[i];
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
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

    public Thumbnail[] getDownloadThumbnails() {
        return downloadThumbnails;
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

    public boolean isHasOfflineGuides() {
        return hasOfflineGuides;
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

    public void grantAccess(UUID uuid, String email) {
	    connection.grantAccess(uuid, email);
    }
    public void revokeAccess(UUID uuid, String email) {
        connection.revokeAccess(uuid, email);
    }

    public String[] getAccessList(UUID guideUUID) {
	    return connection.getAccessList(guideUUID);
    }

    public Connection getConnection() {
        return connection;
    }
}

