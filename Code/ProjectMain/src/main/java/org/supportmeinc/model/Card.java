package org.supportmeinc.model;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.UUID;

public class Card implements Serializable {

    private final UUID cardUUID = UUID.randomUUID();
    private UUID affirmUUID;
    private UUID negUUID;
    private String title;
    private String text;
    private Image image;

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
