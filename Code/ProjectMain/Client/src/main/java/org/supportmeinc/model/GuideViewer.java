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
        this.mainController = mainController;
    }



    public Card getNext(boolean choice) {

        Card nextCard = null;

        if (currentCard != null) {
            if (currentCard.getAffirmUUID() != null && currentCard.getNegUUID() != null) {

                if (choice) {

                    nextCard = currentGuide.getCard(currentCard.getAffirmUUID());
                } else {

                    nextCard = currentGuide.getCard(currentCard.getNegUUID());
                }
            } else if (currentCard.getAffirmUUID() != null && currentCard.getNegUUID() == null) {

                nextCard = currentGuide.getCard(currentCard.getAffirmUUID());

            } else {
                //TODO end guide
            }
            currentCard = nextCard;
        }
        if (currentCard.getAffirmUUID() == null && currentCard.getNegUUID() == null) {
            mainController.lastCard();
        }

        return nextCard;
    }
}
