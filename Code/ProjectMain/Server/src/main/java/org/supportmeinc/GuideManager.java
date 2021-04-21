package org.supportmeinc;

import shared.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.UUID;

public class GuideManager {

    private ModelDatabaseConnection databaseConnection;

    public GuideManager(ModelDatabaseConnection modelDatabaseConnection){
        databaseConnection = modelDatabaseConnection;
    }

    public Guide getGuide(UUID guideUUID) {
        return databaseConnection.getGuide(guideUUID);
    }

    public Thumbnail[] getThumbNails(Thumbnail[] oldArray, UUID[] userGuideAccess) {

        ArrayList<Thumbnail> oldThumbnails = new ArrayList<>(Arrays.asList(oldArray));
        ArrayList<Thumbnail> currentThumbnails = new ArrayList<>(Arrays.asList(databaseConnection.getCurrentThumbnails(userGuideAccess)));

        Thumbnail[] returnArray;

        if (oldThumbnails.equals(currentThumbnails)) {
            returnArray = null;
        } else {
            returnArray = currentThumbnails.toArray(new Thumbnail[0]);
        }
        return returnArray;
    }


}
