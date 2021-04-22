package org.supportmeinc.model;

import org.supportmeinc.JfxUtils;
import shared.*;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class GuideEditor {

    private ArrayList<Card> cardsList;
    private Card currentCard;
    private Guide outputGuide;

    public GuideEditor() {
        cardsList = new ArrayList<>();
    }

    public void addNewCard(String title, String description, File img, String affirmUUID, String negativeUUID) {
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

    public void updateCard(String title, String description, File img, int index) {
        Card card = cardsList.get(index);
        card.setTitle(title);
        card.setText(description);
        card.setImage(JfxUtils.toBytes(img));
    }
}
