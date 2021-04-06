package org.supportmeinc.model;

import org.supportmeinc.view.GuideEditor;
import shared.Card;
import shared.Guide;
import shared.Thumbnail;

import java.util.ArrayList;

public class GuideManager {

    private Guide currentGuide;
    private Guide[] guides;
    private Thumbnail[] thumbnails; // fixa plz
    private Connection connection;
    private Card[][] cards;
    private ArrayList<Card> cardArrayList;

    public GuideManager(Connection connection) {
        this.connection = connection;
        thumbnails = connection.getThumbnails();
    }

    public Card initGuide(int index) {
        currentGuide = connection.getGuide(thumbnails[index].getGuideUUID());
        return currentGuide.getDescriptionCard();
    }

    public Card getCard(boolean choice) {
        return null;
    }

    public Thumbnail[] getThumbnails() {
        return thumbnails;
    }

    public void createGuide() {
        boolean answerToQuit = true;
        String title = "wow"; //TODO: Byt ut dessa mot riktiga värden från gui senare
        String text = "woow"; //CardEditor.getText(); TODO: Add this method to guideEditor so we can get text and title from cardEditor
        byte[] image = {0};
        currentGuide = new Guide();
        while (answerToQuit) {
            while (text.length() > 280) {
                //text = CardEditor.getText();
            }
            createCard(title, text, image);
            answerToQuit = false;
        }
        cards = new Card[cardArrayList.size()][cardArrayList.size()];
        for (int i = 0; i < cardArrayList.size(); i++) {
            currentGuide.setCards(cards[0]);
        }
    }

    public void createCard(String title, String text, byte[] image) {
        Card newCard = new Card();
        newCard.setTitle(title);
        newCard.setText(text);
        newCard.setImage(image);
        cardArrayList.add(newCard);
    }


}
