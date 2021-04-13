package org.supportmeinc;

import shared.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.UUID;

public class GuideManager {

    private HashMap<UUID, Guide> guides;
    private ModelDatabaseConnection databaseConnection;

    public GuideManager(ModelDatabaseConnection modelDatabaseConnection){
        databaseConnection = modelDatabaseConnection;
    }

    public Guide getGuide(UUID guideUUID) {
        return guides.getOrDefault(guideUUID, null);
    }

    public Thumbnail[] getThumbNails(Thumbnail[] oldArray, User user) {

        ArrayList<Thumbnail> oldThumbnails = new ArrayList<>(Arrays.asList(oldArray));
        ArrayList<Thumbnail> currentThumbnails = databaseConnection.getCurrentThumbnails(user);

        for (Guide guide: guides.values()) {
            currentThumbnails.add(guide.getThumbnail());
        }

        if (oldThumbnails.equals(currentThumbnails)) {
            return null;
        } else {
            return currentThumbnails.toArray(new Thumbnail[0]);
        }
    }

    public void addGuide(Guide guide){
        guides.put(guide.getGuideUUID(), guide);
    }

}
