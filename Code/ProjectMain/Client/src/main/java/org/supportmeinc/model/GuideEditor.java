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

    public void saveCard(String title, String description, byte[] img, UUID affirmUUID, UUID negativeUUID, UUID cardUUID) {
        currentCard = new Card();
        currentCard.setTitle(title);
        currentCard.setText(description);
        currentCard.setAffirmUUID(affirmUUID);
        currentCard.setNegUUID(negativeUUID);
        currentCard.setImage(img);

        if (cardsList.containsKey(cardUUID)){
            cardsList.replace(cardUUID, currentCard);
        } else {
            cardsList.put(cardUUID, currentCard);
        }
    }

    public void removeCard(UUID cardUUID) {
        cardsList.remove(cardUUID);
    }

    public HashMap<UUID, Card> getCardsList() {
        return cardsList;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public void createNewCard() {
        currentCard = new Card();
    }

    public String getCardTitle(UUID uuid) {
        return cardsList.get(uuid).getTitle();
    }
    public String getCardText(UUID uuid){
        return cardsList.get(uuid).getText();
    }
    public UUID getCardAffirmUUID(UUID uuid){
        return cardsList.get(uuid).getAffirmUUID();
    }
    public UUID getCardNegUUID(UUID uuid){
        return cardsList.get(uuid).getNegUUID();
    }
    public byte[] getCardImage(UUID uuid){
        return cardsList.get(uuid).getImage();
    }
}
