package org.supportmeinc;

import shared.*;
import shared.Thumbnail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuideManager {

    Guide[] guides;

    public Thumbnail[] getThumbNails(Thumbnail[] oldArray) {

        ArrayList<Thumbnail> oldThumbnails = new ArrayList<>(Arrays.asList(oldArray));
        ArrayList<Thumbnail> currentThumbnails = new ArrayList<>();

        for (Guide guide: guides) {
            currentThumbnails.add(guide.getThumbnail());
        }

        if (oldThumbnails.equals(currentThumbnails)) {
            return null;
        } else {
            return currentThumbnails.toArray(new Thumbnail[0]);
        }
    }
}
