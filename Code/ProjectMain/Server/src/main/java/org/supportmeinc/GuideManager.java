package org.supportmeinc;

import shared.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public Thumbnail[] getThumbNails(Thumbnail[] oldArray) {

        ArrayList<Thumbnail> oldThumbnails = new ArrayList<>(Arrays.asList(oldArray));
        ArrayList<Thumbnail> currentThumbnails = new ArrayList<>();

        for (Guide guide: guides.values()) {
            currentThumbnails.add(guide.getThumbnail());
        }

        if (oldThumbnails.equals(currentThumbnails)) {
            return null;
        } else {
            return currentThumbnails.toArray(new Thumbnail[0]);
        }

    }
}
