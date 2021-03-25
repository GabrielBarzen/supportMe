package org.supportmeinc.Model;

import javax.swing.*;
import java.util.UUID;

public class Thumbnail {

    private String title;
    private String description;
    private ImageIcon icon;
    private UUID guideUUID;

    public Thumbnail(UUID guideUUID){
        this.guideUUID = guideUUID;
    }

    public UUID getGuideUUID() {
        return guideUUID;
    }
}
