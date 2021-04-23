package org.supportmeinc.model;

import shared.Card;
import shared.Guide;

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

    public void addNewCard(String title, String description, File img, UUID affirmUUID, UUID negativeUUID) {
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
        System.out.println(card);
        card.setTitle(title);
        card.setText(description);
        cardsList.replace(cardUUID, card);
        System.out.println(cardsList.get(cardUUID) + "sista i GuideEditor");

    }
}