package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Guide implements Serializable {

    private UUID guideUUID;
    private Card descriptionCard;
    private ArrayList<Card> cards;
    private Card currentCard;

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    private Thumbnail thumbnail;

    public Guide(){
        guideUUID = UUID.randomUUID();
        cards = new ArrayList<Card>();
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

    public Card getCard(UUID cardUUID){
        for (Card card : cards) {
            if(card.getCardUUID().equals(cardUUID)) {
                currentCard = card;
                return card;
            }
        }
        return null;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Card getCard(boolean choice) {
        UUID id;
        Card card;
        if (choice) {
            id = currentCard.getAffirmUUID();
        }
        else {
            id = currentCard.getNegUUID();
        }
        card = getCard(id);
        return card;
    }
}
