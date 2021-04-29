package org.supportmeinc.model;

import org.supportmeinc.ImageUtils;
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
        card.setAffirmUUID(affirmUUID);
        card.setNegUUID(negativeUUID);

        if(img == null) {
            card.setImage(null);
        } else {
            card.setImage(ImageUtils.toBytes(img));
        }

        cardsList.put(card.getCardUUID(),card);
    }

    public void updateCard(String title, String description, File img, UUID affirmUUID, UUID negativeUUID, UUID cardUUID) {
        Card card = cardsList.get(cardUUID);
        card.setTitle(title);
        card.setText(description);
        card.setAffirmUUID(affirmUUID);
        card.setNegUUID(negativeUUID);

        if(img == null) {
            card.setImage(null);
        } else {
            card.setImage(ImageUtils.toBytes(img));
        }

        cardsList.replace(cardUUID, card);
    }

    public void removeCard(UUID cardUUID) {
        cardsList.remove(cardUUID);
    }

    public HashMap<UUID, Card> getCardsList() {
        return cardsList;
    }
}