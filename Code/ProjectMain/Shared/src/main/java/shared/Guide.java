package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Guide implements Serializable {

    private UUID guideUUID;
    private Card descriptionCard;
    private HashMap<UUID,Card> cards;
    private Card currentCard;
    private String author;

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    private Thumbnail thumbnail;

    public Guide(){
        guideUUID = UUID.randomUUID();
        cards = new HashMap<>();
        thumbnail = new Thumbnail(guideUUID);
    }
    public Guide(UUID guideUUID){
        this.guideUUID = guideUUID;
        cards = new HashMap<>();
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

        return cards.get(cardUUID);
    }

    public void setCards(Card[] cards) {
        HashMap<UUID, Card> cardMap = new HashMap<>();
        for (Card card: cards) {
            cardMap.put(card.getCardUUID(),card);
        }
        this.cards = cardMap;
    }

    public Card[] getCards() {
        return cards.values().toArray(new Card[0]);
    }

    public void addCard(Card card) {
        cards.put(card.getCardUUID(), card);
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void removeCard(Card card) {
        cards.remove(card.getCardUUID());
    }
}
