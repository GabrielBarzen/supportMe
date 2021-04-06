package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Guide implements Serializable {

    private UUID guideUUID;
    private Card descriptionCard;
    private Card[] cards;
    private Card currentCard;
    private Thumbnail thumbnail;

    public Guide(){
        guideUUID = UUID.randomUUID();
        //cards = new Card[]; Utbytt mot en array för enklare hantering
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

    public void setDescriptionCard(Card descriptionCard) {
        this.descriptionCard = descriptionCard;
    }

    public Card getCard(String cardUUID){
        for (Card card : cards) {
            if(card.getCardUUID().equals(cardUUID)) {
                currentCard = card;
                return card;
            }
        }
        return null;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }
}
