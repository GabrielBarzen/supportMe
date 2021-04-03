package org.supportmeinc.model;

import shared.Card;
import shared.Guide;
import shared.Thumbnail;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.UUID;

public class GuideManager {

    private Guide currentGuide;
    private Guide[] guides;
    private Thumbnail[] thumbnails; // fixa plz
    private Connection connection;

    public GuideManager(Connection connection) {
        this.connection = connection;
        thumbnails = connection.getThumbnails();
    }

    public Card initGuide(int index) {
        currentGuide = connection.getGuide(thumbnails[index].getGuideUUID());
        return currentGuide.getDescriptionCard();
    }

    public Card getCard(boolean choice) {
        return null;
    }

    public Thumbnail[] getThumbnails() {
        return thumbnails;
    }

    public Guide getGuide(Thumbnail thumbnail) {
        UUID id = thumbnail.getGuideUUID();
        Guide guide = connection.getGuide(id);
        return guide;
    }

    public void downloadGuide(Thumbnail thumbnail) {
        Guide guide = getGuide(thumbnail);
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Dat.files", "dat"));
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            oos.writeObject(guide);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
