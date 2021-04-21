package org.supportmeinc.model;

import shared.*;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class GuideEditor {

    private ArrayList<Card> cardsList;
    private Card currentCard;
    private Guide outputGuide;

    public GuideEditor() {
        cardsList = new ArrayList<>();
    }

    public void addNewCard(String title, String description, Image img, String affirmUUID, String negativeUUID) {
        Card card = new Card();
        card.setTitle(title);
        card.setText(description);
        cardsList.add(card);
    }

    public ArrayList<Card> getCardsList() {
        return cardsList;
    }

    public void populateCardList() {
        Card card;

        for (int i = 0; i < 9; i++) {
            card = new Card();
            card.setTitle(i + " Wow it works");
            cardsList.add(card);
        }
    }
}
