package org.supportmeinc.model;


import java.io.IOException;
import java.net.Socket;
import java.util.UUID;
import shared.*;

public class Connection {

    private Socket socket;
    private GuideManager guideManager;

    public Connection(String ip, int port) {
/*        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }*/ //TODO server
    }

    public Guide getGuide(UUID guideUUID) {
        return goodLordTheCardGiver();
    }

    public Guide goodLordTheCardGiver() {
        Guide guide = new Guide();
        Card card = new Card();
        card.setTitle("The last guide you will ever need");
        card.setText("Step 1 get rich, step 2 ???, setp 3 profit");
        card.setImage(JfxUtils.toBytes("src/main/resources/org/supportmeinc/FinalLogotyp.png"));
        guide.setDescriptionCard(card);
        return guide;
    }

    public Thumbnail[] getThumbnails() {
        return new Thumbnail[]{new Thumbnail(UUID.randomUUID())};
    }

    public void disconnect() {
        try {
            socket.close();
            guideManager.loadGuides();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGuideManager(GuideManager manager) {
        guideManager = manager;
    }
}
