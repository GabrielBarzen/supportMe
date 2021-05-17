package org.supportmeinc;

import shared.Card;
import shared.Guide;
import shared.Thumbnail;
import shared.User;

import java.util.UUID;

public class DatabaseManager {

    private UserDatabase userDatabase;
    private ModelDatabase modelDatabase;

    public DatabaseManager(){
        userDatabase = new UserDatabase(this);
        modelDatabase = new ModelDatabase(this);
    }


    //ModelDatabase methods//
    public Thumbnail[] getCurrentThumbnails(UUID[] guideAccessUUID) {
        return modelDatabase.getThumbnailsFromUUID(guideAccessUUID);}

    public Card[] getCards(UUID guideUUID) {
        return modelDatabase.getCards(guideUUID);}

    public Thumbnail getThumbnail(UUID guideUUID) {
        return modelDatabase.getThumbnail(guideUUID);}

    public Card getCard(UUID cardUUID) {
        return modelDatabase.getCard(cardUUID);}

    public Guide getGuide(UUID guideUUID) {
        return modelDatabase.getGuide(guideUUID);}

    public void modelDatabaseAddGuide(Guide guide) {
        modelDatabase.addGuide(guide);
    }

    //UserDatabase methods//

    public String getSalt(User user) {
        return userDatabase.getSalt(user);
    }

    public User authenticate(User user, String passwordHash) {
        return userDatabase.authenticate(user, passwordHash);
    }

    public boolean registerUser(User user, String passwordHash, String salt) {
        return userDatabase.registerUser(user, passwordHash, salt);
    }

    public UUID[] getGuideUUIDaccess(User user) {
        return userDatabase.getGuideUUIDAccess(user);
    }

    public boolean saveGuide(Guide guide, User user) {
        boolean success = false;
        System.out.println("Saving gude");
        success = modelDatabase.saveGuide(guide);
        if (success) {
            System.out.println("saved guide");
            success = userDatabase.saveGuide(guide);
        }
        return success;
    }

    public Thumbnail[] getAccessThumbnails(User user) {
        UUID[] accessUUID = userDatabase.getGuideUUIDAccess(user);
        Thumbnail[] accessThumbnails = modelDatabase.getThumbnailsFromUUID(accessUUID);
        return accessThumbnails;
    }

    public Thumbnail[] getAuthorThumbnails(User user) {
        UUID[] authorUUID = userDatabase.getGuideUUIDAuthor(user);
        Thumbnail[] authorThumbnails = modelDatabase.getThumbnailsFromUUID(authorUUID);
        return authorThumbnails;
    }


    public boolean grantAccess(String userEmail, UUID guideUUID) {
        return userDatabase.grantAccess(userEmail, guideUUID);
    }

    public boolean revokeAccess(String userEmail, UUID guideUUID) {
        return userDatabase.revokeAccess(userEmail, guideUUID);
    }

    public String[] getAccessList(String requestPart) {
        return userDatabase.getAccessList(UUID.fromString(requestPart));
    }

    public void removeGuide(String requestPart) {
        boolean success = userDatabase.removeGuide(UUID.fromString(requestPart));
        if (success) {
            modelDatabase.removeGuide(UUID.fromString(requestPart));
        }
    }
}