package org.supportmeinc.model;

import shared.Card;
import shared.Guide;
import shared.Thumbnail;

import java.util.ArrayList;
import java.util.UUID;

public class GuideCreator {
    private Guide currentEditedGuide;
    private Card currentEditedCard;

    public void createGuide() {
        if (currentEditedGuide == null) {
            currentEditedGuide = new Guide();
        }
    }

    public void createCard() {
        String title = "";
        String text = "";
        byte[] image = {0};
        if (currentEditedCard == null) {
            currentEditedCard = new Card();
        }
        currentEditedCard.setTitle(title);
        currentEditedGuide.addCard(currentEditedCard);
    }

    public void finishGuide() {
        ArrayList<Card> cards = currentEditedGuide.getCards();
        for (Card currentCard: cards) {
            currentCard.setAffirmUUID(UUID.randomUUID());
            currentCard.setNegUUID(UUID.randomUUID());
        }
    }


    /*public void createCard(String title, String text, byte[] image) {
        Card newCard = new Card();
        newCard.setTitle(title);
        while (text.length() > 280) {
            //text = gui.getTextFromGUI();
        }
        newCard.setText(text);
        newCard.setImage(image);
        cardArrayList.add(newCard);

        answer = false;
    }
        guide.setCards(cardArrayList);
    send(guide);
        for (int i = 0; i < cardArrayList.size(); i++) {
        Card card = guide.getCard(UUID.randomUUID());
        card.setAffirmUUID(UUID.randomUUID()); //Change to a real UUID
        card.setNegUUID(UUID.randomUUID()); //Change to a real UUID
    }
}

    public Guide getGuide(Thumbnail thumbnail) {
        UUID id = thumbnail.getGuideUUID();
        Guide guide = connection.getGuide(id);
        return guide;

    }*/
}
