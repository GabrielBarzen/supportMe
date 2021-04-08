package org.supportmeinc.model;

import shared.Card;
import shared.Guide;
import shared.Thumbnail;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class GuideCreator {
    private Guide currentEditedGuide;
    private Card currentEditedCard;
    ArrayList<Card> cards;
    Connection connection;

    public GuideCreator(Connection connection) {
        this.connection = connection;
    }

    public void createGuide() {
        if (currentEditedGuide == null) {
            currentEditedGuide = new Guide();
            cards = currentEditedGuide.getCards();
        }
    }

    public void createCard() {
        if (currentEditedCard == null) {
            currentEditedCard = new Card();
        }
        String title = "";
        String text = "";
        byte[] image = {0};
        currentEditedCard.setTitle(title);
        currentEditedGuide.addCard(currentEditedCard);
    }

    public void finishGuide() {
        for (Card currentCard: cards) {
            currentCard.setAffirmUUID(UUID.randomUUID());
            currentCard.setNegUUID(UUID.randomUUID());
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(connection.getSocket().getOutputStream());
            oos.writeObject(currentEditedGuide);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void discardCard(Card card) {
        cards.remove(card);
    }

    public void discardThisCard() {
        currentEditedCard = null;
    }

    public void discardThisGuide() {
        currentEditedGuide = null;
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
