package org.supportmeinc.model;

import org.supportmeinc.MainController;
import shared.Card;
import shared.Guide;

public class GuideViewer {
    private Guide currentGuide;
    private Card currentCard;
    private MainController mainController;

    public GuideViewer(Guide guide, MainController mainController) {
        this.currentGuide = guide;
        currentCard = currentGuide.getDescriptionCard();
        this.mainController = mainController;
    }

    public Card getNext(boolean choice) {
        Card returnCard;
        if (currentCard.getAffirmUUID() == null && currentCard.getNegUUID() == null) {
            if (choice) {
                returnCard = currentGuide.getCard(currentCard.getAffirmUUID());
            } else {
                returnCard = currentGuide.getCard(currentCard.getNegUUID());
            }
            currentCard = returnCard;
        } else {
            System.out.println("Slut på guiden");
        }
        System.out.println(currentCard.getAffirmUUID());
        System.out.println(currentCard.getNegUUID());
        if (currentCard.getAffirmUUID() == null && currentCard.getNegUUID() == null) {
            mainController.lastCard();
        }
        return currentCard;
    }
}
