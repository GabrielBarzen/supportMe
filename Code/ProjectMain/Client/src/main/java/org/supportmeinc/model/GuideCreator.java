package org.supportmeinc.model;

import shared.Card;
import shared.Guide;
import shared.Thumbnail;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.UUID;


public class GuideCreator {
    private Guide currentEditedGuide;
    private Card currentEditedCard;
    private Card descriptionCard;
    ArrayList<Guide> guideArrayList;
    Connection connection;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public GuideCreator(Connection connection) {
        this.connection = connection;
        try {
            oos = new ObjectOutputStream(connection.getSocket().getOutputStream());
            ois = new ObjectInputStream(connection.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createGuide() {
        if (currentEditedGuide == null) {
            currentEditedGuide = new Guide();
        }
        ArrayList<Card> cards = currentEditedGuide.getCards();
        createDescriptionCard();
    }

    public void finishGuide() {
        ArrayList<Card> cards = currentEditedGuide.getCards();
        for (Card currentCard: cards) {
            currentCard.setAffirmUUID(UUID.randomUUID());
            currentCard.setNegUUID(UUID.randomUUID());
        }
        currentEditedGuide.setAuthor("ändras till release");
        sendCreatedGuide();
    }

    public void sendCreatedGuide() {
        if (currentEditedGuide != null) {
            try {
                oos.writeObject(currentEditedGuide);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Guide[] getGuides() {
        guideArrayList = new ArrayList<>();
        String author = "Namn på author";
        //Method to get guides that the author has created from database
        try {
            oos.writeObject(author);
            Object object = ois.readObject();
            while (object != null) {
                if (object instanceof Guide) {
                    Guide guide = (Guide) object;
                    guideArrayList.add(guide);
                }
                object = ois.readObject();
            }
            Guide[] guides = guideArrayList.toArray(Guide[]::new);
            return guides;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void editGuide(int index) {
        ArrayList<Card> cards;
        Guide[] guides = getGuides();
        currentEditedGuide = guides[index];
        cards = currentEditedGuide.getCards();
        editCard();
    }

    public void createDescriptionCard() {
        if (descriptionCard == null) {
            descriptionCard = new Card();
        }
        Thumbnail thumbnail = getThumbnail();
        String title = thumbnail.getTitle();
        String description = thumbnail.getDescription();
        byte[] image = thumbnail.getImage();
        descriptionCard.setTitle(title);
        descriptionCard.setText(description);
        descriptionCard.setImage(image);
        currentEditedGuide.addCard(descriptionCard);
    }

    public void createCard() {
        if (currentEditedCard == null) {
            currentEditedCard = new Card();
        }
        String title = "";
        String text = "";
        byte[] image = {0};
        String newTitle = ""; //get info from gui
        String newText = ""; //same
        byte[] newImage = {0}; //same
        UUID newAffirmID = UUID.randomUUID(); //change this to another cards UUID
        UUID newNegativeID = UUID.randomUUID(); //same with this
        currentEditedCard.setTitle(newTitle);
        currentEditedCard.setText(newText);
        currentEditedCard.setImage(newImage);
        currentEditedCard.setAffirmUUID(newAffirmID);
        currentEditedCard.setNegUUID(newNegativeID);
        currentEditedGuide.addCard(currentEditedCard);
    }

    public void editCard() {
        ArrayList<Card> cards = new ArrayList<>();
        int index = 0;
        Card newCard = cards.get(index);
        String title = newCard.getTitle();
        String text = newCard.getText();
        byte[] image = newCard.getImage();
        UUID affirmID = newCard.getAffirmUUID();
        UUID negativeID = newCard.getNegUUID();
        //Send all to gui (maybe not needed but good to have right now)
        String newTitle = ""; //get info from gui
        String newText = ""; //same
        byte[] newImage = {0}; //same
        UUID newAffirmID = UUID.randomUUID(); //change this to another cards UUID
        UUID newNegativeID = UUID.randomUUID(); //same with this
        newCard.setTitle(newTitle);
        newCard.setText(newText);
        newCard.setImage(newImage);
        newCard.setAffirmUUID(newAffirmID);
        newCard.setNegUUID(newNegativeID);
        cards.add(newCard);
    }

    public void discardCard(Card card) {
        currentEditedGuide.getCards().remove(card);
    }

    public void discardThisCard() {
        currentEditedCard = null;
    }

    public void discardThisGuide() {
        currentEditedGuide = null;
    }

    public Thumbnail getThumbnail() {
        return currentEditedGuide.getThumbnail();
    }
}
