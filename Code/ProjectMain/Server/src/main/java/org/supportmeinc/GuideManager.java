package org.supportmeinc;

import shared.Guide;
import shared.Thumbnail;
import shared.User;

import java.util.HashMap;
import java.util.UUID;

public class GuideManager {

    private HashMap<UUID, Guide> guides;

    public GuideManager(){
        readGuidesFromFile();
    }

    public void readGuidesFromFile(){
        guides = new HashMap<>();
        Guide guide = new Guide();
        guides.put(guide.getGuideUUID(), guide);
    }

    public void writeGuidesToFile(){

    }

    public Guide getGuide(UUID guideUUID) {
        return guides.getOrDefault(guideUUID, null);
    }
}
