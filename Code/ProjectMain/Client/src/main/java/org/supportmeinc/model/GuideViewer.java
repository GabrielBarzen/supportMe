package org.supportmeinc.model;

import org.supportmeinc.MainController;
import shared.Card;
import shared.Guide;

import java.lang.reflect.Array;
import java.util.Arrays;

public class GuideViewer {
    private Guide currentGuide;
    private Card currentCard;
    private MainController mainController;

    public GuideViewer(Guide guide, MainController mainController) {
        this.currentGuide = guide;
        this.currentCard = currentGuide.getDescriptionCard();
        System.out.println(currentCard.getCardUUID());
        this.mainController = mainController;
        System.out.println(Arrays.toString(currentGuide.getCards()));
        for (Card card: currentGuide.getCards()) {
            System.out.println("uuids in card array : " + card.getTitle() + " , " + card.getCardUUID());
        }
    }



    public Card getNext(boolean choice) {
        System.out.println("Cards in guide " + Arrays.toString(currentGuide.getCards()));
        System.out.println("Cards in guide " + currentCard);

        Card nextCard = null;
        System.out.println("Current card : " + currentCard);
        if (currentCard != null) {
            if (currentCard.getAffirmUUID() != null && currentCard.getNegUUID() != null) {
                System.out.print("Setting next card");
                if (choice) {
                    System.out.println("to true card");
                    nextCard = currentGuide.getCard(currentCard.getAffirmUUID());
                } else {
                    System.out.println("to false card");
                    nextCard = currentGuide.getCard(currentCard.getNegUUID());
                }
            } else if (currentCard.getAffirmUUID() != null && currentCard.getNegUUID() == null) {
                System.out.println("Setting next card to guide start");
                System.out.println("Next afirm card before get: " + currentCard.getAffirmUUID());
                nextCard = currentGuide.getCard(currentCard.getAffirmUUID());
                System.out.println("Next afirm card after get: " + nextCard.getCardUUID());
            } else {
                System.out.println("end guide");
            }
            System.out.println("setting card to : " + nextCard);
            currentCard = nextCard;
        }

        return nextCard;
    }
}
