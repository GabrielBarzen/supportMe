package org.supportmeinc.Model;

import java.io.Serializable;
import java.util.UUID;

public class Card implements Serializable {

    private UUID cardUUID;
    private UUID affirmUUID;
    private UUID negUUID;

    public Card(){
        cardUUID = UUID.randomUUID();
    }

    public String getCardUuid() {
        return cardUUID.toString();
    }

    public String getNextAffirmative(){
        return affirmUUID.toString();
    }

    public String getNextNegative(){
        return negUUID.toString();
    }
}
