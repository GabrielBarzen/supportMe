package org.supportmeinc.model;

import org.supportmeinc.ImageUtils;
import org.supportmeinc.MainController;
import shared.Card;
import shared.Guide;
import shared.Thumbnail;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class GuideEditor {

    private HashMap<UUID,Card> cardsList;
    private Card currentCard;
    private Guide outputGuide;
    private Card descriptionCard;
    private Thumbnail thumbnail;
    private MainController controller;

    public GuideEditor(MainController controller) {
        this.controller = controller;
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

    public void setDescription(String title, String description, byte[] img, UUID affirmUUID, Guide guide) {
        Card card = new Card();
        card.setText(description);
        card.setTitle(title);
        card.setImage(img);
        card.setNegUUID(null);
        card.setAffirmUUID(affirmUUID);

        Thumbnail thumbnail = new Thumbnail(guide.getGuideUUID());
        thumbnail.setDescription(card.getText());
        thumbnail.setTitle(card.getTitle());
        thumbnail.setImage(card.getImage());

        this.descriptionCard = card;
        this.thumbnail = thumbnail;
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

    public Guide packGuide(String title, String description, byte[] img, UUID affirmUUID) {
        Guide returnGuide;
        int ok = 0;

        for (Card card : cardsList.values()) {
            if (card.getNegUUID() == null && card.getAffirmUUID() == null){
                ok++;
            } else if (card.getNegUUID() == null || card.getAffirmUUID() == null) {
                ok = -1;
            }
        }

        if(ok != 1){
            returnGuide = null;
        } else {
            returnGuide = new Guide();
            setDescription(title, description, img, affirmUUID, returnGuide);
            returnGuide.setCards(cardsList.values().toArray(new Card[0]));
            returnGuide.setDescriptionCard(descriptionCard);
            returnGuide.setThumbnail(thumbnail);
            returnGuide.setAuthor(controller.getAuthor());
        }

        return returnGuide;
    }
}
