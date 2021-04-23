package org.supportmeinc.model;

import org.supportmeinc.JfxUtils;
import shared.*;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class GuideEditor {

    private HashMap<UUID,Card> cardsList;
    private Card currentCard;
    private Guide outputGuide;

    public GuideEditor() {
        cardsList = new HashMap<>();
    }

    public void addNewCard(String title, String description, File img, String affirmUUID, String negativeUUID) {
        Card card = new Card();
        card.setTitle(title);
        card.setText(description);
        cardsList.put(card.getCardUUID(),card);
    }

    public HashMap<UUID, Card> getCardsList() {
        return cardsList;
    }


    public void updateCard(String title, String description, File img, UUID affirmUUID, UUID negativeUUID, UUID cardUUID) {
        Card card = cardsList.get(cardUUID);
        card.setTitle(title);
        card.setText(description);
        cardsList.put(cardUUID, card);

    }
}