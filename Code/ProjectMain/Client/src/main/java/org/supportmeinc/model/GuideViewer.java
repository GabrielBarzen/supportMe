package org.supportmeinc.model;

import org.supportmeinc.MainController;
import shared.Card;
import shared.Guide;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Stack;

public class GuideViewer {
    private Guide currentGuide;
    private Card currentCard;
    private Stack<Card> userPath;
    private MainController mainController;

    public GuideViewer(Guide guide, MainController mainController) {
        this.currentGuide = guide;
        this.currentCard = currentGuide.getDescriptionCard();
        this.userPath = new Stack<>();
        this.mainController = mainController;
    }

    public void pushCard(Card previousCard) {
        userPath.push(previousCard);
        System.out.println("PUSH, CURRENT STACK : " + userPath.toString());
    }

    public Card previousCard() {
        Card previousCard;
        if (userPath.isEmpty()) {
            previousCard = null;
        } else {
            previousCard = userPath.pop();
            currentCard = previousCard;
        }
        System.out.println("POP, CURRENT STACK : " + userPath.toString());
        return previousCard;
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
            pushCard(currentCard);
            currentCard = nextCard;
        }
        if (currentCard.getAffirmUUID() == null && currentCard.getNegUUID() == null) {
            mainController.lastCard();
        }

        return nextCard;
    }
}
