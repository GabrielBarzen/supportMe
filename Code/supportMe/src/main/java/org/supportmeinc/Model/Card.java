package org.supportmeinc.Model;

import java.io.Serializable;
import java.util.UUID;

public class Card implements Serializable {

    private UUID uuid;
    private UUID affirmUUID;
    private UUID negUUID;

    public Card(){
        uuid = UUID.randomUUID();
    }

    public String getCardUuid() {
        return uuid.toString();
    }

    public String getNextAffirmative(){
        return affirmUUID.toString();
    }

    public String getNextNegative(){
        return negUUID.toString();
    }
}
