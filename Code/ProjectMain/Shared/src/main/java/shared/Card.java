package shared;

import java.io.Serializable;
import java.util.UUID;

public class Card implements Serializable {

    private final UUID cardUUID;
    private UUID affirmUUID;
    private UUID negUUID;
    private String title;
    private String text;
    private byte[] image;

    public Card(UUID cardUUID){
        this.cardUUID = cardUUID;
    }

    public Card(){
        this.cardUUID = UUID.randomUUID();
    }

    public void setAffirmUUID(UUID affirmUUID) {
        this.affirmUUID = affirmUUID;
    }

    public void setNegUUID(UUID negUUID) {
        this.negUUID = negUUID;
    }

    public UUID getCardUUID() {
        return cardUUID;
    }

    public UUID getAffirmUUID() {
        return affirmUUID;
    }

    public UUID getNegUUID() {
        return negUUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}
