package org.supportmeinc.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Guide implements Serializable {

    private UUID guideUUID;
    private Card descriptionCard;
    private ArrayList<Card> cards;
    private Card currentCard;
    private Thumbnail thumbnail;

    public Guide(){
        guideUUID = UUID.randomUUID();
        cards = new ArrayList<>();
        thumbnail = new Thumbnail(guideUUID);
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public UUID getGuideUUID() {
        return guideUUID;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public Card getDescriptionCard() {
        return descriptionCard;
    }

    public Card getCard(String cardUUID){
        for (Card card : cards) {
            if(card.getCardUuid().equals(cardUUID)) {
                currentCard = card;
                return card;
            }
        }
        return null;
    }
}
