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
    }

    public Card getNext(boolean choice) {
        Card returnCard;
        System.out.println("KORRTTITITTLES " + currentCard);
        System.out.println("KORRUUUUUUUUID: " + currentCard.getCardUUID());
        if (currentCard.getAffirmUUID() != null && currentCard.getNegUUID() != null) {
            if (choice) {
                returnCard = currentGuide.getCard(currentCard.getAffirmUUID());
            } else {
                returnCard = currentGuide.getCard(currentCard.getNegUUID());
            }
            currentCard = returnCard;
        } else if (currentCard.getAffirmUUID() != null && currentCard.getNegUUID() == null) {
            returnCard = currentGuide.getCard(currentCard.getAffirmUUID());
            System.out.println("Slut på guiden");
            currentCard = returnCard;
        }
        if (currentCard.getAffirmUUID() == null && currentCard.getNegUUID() == null) {
            mainController.lastCard();
        }
        return currentCard;
    }
}
