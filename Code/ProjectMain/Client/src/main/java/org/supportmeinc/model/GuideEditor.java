package org.supportmeinc.model;

import org.supportmeinc.MainController;
import shared.Card;
import shared.Guide;
import shared.Thumbnail;

import java.util.HashMap;
import java.util.UUID;

public class GuideEditor {

    private Guide outputGuide;
    private HashMap<UUID,Card> cardsList;
    private Card currentCard;
    private Card descriptionCard;
    private UUID guideUUID = UUID.randomUUID();
    private Thumbnail thumbnail;
    private MainController controller;
    private UUID firstCard;

    public UUID getGuideUUID() {
        return guideUUID;
    }

    public GuideEditor(MainController controller) {
        this.controller = controller;
        cardsList = new HashMap<>();
    }

    public void setEditGuide(Guide guide) {
        HashMap<UUID,Card> temp = new HashMap<>();
        for (Card card : guide.getCards()) {
            if(!(card.getNegUUID() == null && card.getAffirmUUID() != null)) {
                temp.put(card.getCardUUID(), card);
            }
        }
        this.cardsList = temp;
        this.guideUUID = guide.getGuideUUID();
        this.outputGuide = guide;
    }

    public void saveCard(String title, String description, byte[] img, UUID affirmUUID, UUID negativeUUID, UUID cardUUID) {
        currentCard = new Card(cardUUID);
        currentCard.setTitle(title);
        currentCard.setText(description);
        currentCard.setAffirmUUID(affirmUUID);
        currentCard.setNegUUID(negativeUUID);
        currentCard.setImage(img);

        if (cardsList.containsKey(cardUUID)){
            cardsList.replace(cardUUID, currentCard);
            System.out.println("replaced");
        } else {
            cardsList.put(cardUUID, currentCard);
            System.out.println("put");
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

    //Called from packGuide, creates a description card containing similar information to the thumbnail.
    public void setDescription(String title, String description, byte[] img, Guide guide) {
        Card card = new Card();
        card.setText(description);
        card.setTitle(title);
        card.setImage(img);
        card.setNegUUID(null);
        card.setAffirmUUID(firstCard);
        cardsList.put(card.getCardUUID(), card);

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
    public Guide getOutputGuide() {
        return outputGuide;
    }

    /*
    Takes in parameters to create the description card & thumbnail.
    Creates and stores all data in a new Guide object as outputGuide.
     */
    public void packGuide(String title, String description, byte[] img) {
        Guide returnGuide = new Guide(guideUUID);
        setDescription(title, description, img, returnGuide);
        returnGuide.setCards(cardsList.values().toArray(new Card[0]));
        returnGuide.setDescriptionCard(descriptionCard);
        returnGuide.setThumbnail(thumbnail);
        returnGuide.setAuthor(controller.getAuthor());
        System.out.println(controller.getAuthor());
        this.outputGuide = returnGuide;
    }

    public boolean checkCardLinksValid() {
        boolean boolReturn;
        int ok = 0;

        for (Card card : cardsList.values()) {
            if (card.getNegUUID() == null && card.getAffirmUUID() == null){
                ok++;
            } else if (card.getNegUUID() == null || card.getAffirmUUID() == null) {
                ok = -1;
            }
        }

        if(ok != 1) {
            boolReturn = false;
        } else {
            boolReturn = true;
        }
        return boolReturn;
    }

    public String getGuideTitle() {
        return outputGuide.getThumbnail().getTitle();
    }

    public String getGuideDescription() {
        return outputGuide.getThumbnail().getTitle();
    }

    public Card getCard(UUID uuid){
        return cardsList.get(uuid);
    }

    public void setFirstCard(UUID cardUUID) {
        this.firstCard = cardUUID;
    }

    public UUID getFirstCard() {
        return firstCard;
    }
}
