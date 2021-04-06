package org.supportmeinc.model;

import org.supportmeinc.view.GuideEditor;
import shared.Card;
import shared.Guide;
import shared.Thumbnail;
import shared.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.UUID;
import java.util.ArrayList;

public class GuideManager {

    private Guide currentGuide;
    private Guide[] guides;
    private Thumbnail[] thumbnails; // fixa plz
    private Connection connection;
    private Card[][] cards;
    private ArrayList<Card> cardArrayList;

    public GuideManager(Connection connection) {
        this.connection = connection;
        thumbnails = connection.getThumbnails();
        connection.setGuideManager(this);
        connection.send(new User("2@2.com","notExist","123456789"));
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

//    public void downloadGuide(Thumbnail thumbnail) {
//        Guide guide = getGuide(thumbnail);
//        try {
//            JFileChooser chooser = new JFileChooser();
//            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//            chooser.showOpenDialog(null);
//            System.out.println(chooser.getCurrentDirectory());
//            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(chooser.getSelectedFile() + "guides.dat")));
//            oos.writeObject(guides);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public void loadGuides() {
//        JFileChooser chooser = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("DAT files", "dat");
//        chooser.setFileFilter(filter);
//        chooser.showOpenDialog(null);
//        File file = chooser.getSelectedFile();
//        try {
//            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
//            Guide guide = (Guide) ois.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        public void createGuide () {
//            boolean answerToQuit = true;
//            String title = "wow"; //TODO: Byt ut dessa mot riktiga värden från gui senare
//            String text = "woow"; //CardEditor.getText(); TODO: Add this method to guideEditor so we can get text and title from cardEditor
//            byte[] image = {0};
//
//            currentGuide = new Guide();
//            while (answerToQuit) {
//                while (text.length() > 280) {
//                    //text = CardEditor.getText();
//                }
//
//                createCard(title, text, image);
//                answerToQuit = false;
//            }
//            cards = new Card[cardArrayList.size()][cardArrayList.size()];
//            for (int i = 0; i < cardArrayList.size(); i++) {
//                guide.setCards(cards[0]);
//            }
//            send(guide);
//        }
//
//        public void createCard (String title, String text,byte[] image){
//            Card newCard = new Card();
//            newCard.setTitle(title);
//            newCard.setText(text);
//            newCard.setImage(image);
//            cardArrayList.add(newCard);
//        }
//
//        public void send(Guide guide) {
//            connection.send(guide);
//        }
}


