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
        return modelDatabase.getCurrentThumbnails(guideAccessUUID);}

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
    public boolean getAuthor(UUID guideUUID){
        return userDatabase.getAuthor(guideUUID);//TODO get author from guide
    }

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

    public boolean giveAccess(String authorEmail, String userEmail, UUID guideUUID) {
        return userDatabase.giveAccess(authorEmail, userEmail, guideUUID);
    }

    public boolean revokeAccess(String authorEmail, String userEmail, UUID guideUUID) {
        return userDatabase.giveAccess(authorEmail, userEmail, guideUUID);
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
}
