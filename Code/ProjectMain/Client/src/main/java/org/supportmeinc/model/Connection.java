package org.supportmeinc.model;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;
import shared.*;

public class Connection implements Runnable {

    Thread thread = new Thread(this);
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private User user;

    public Connection(String ip, int port, User user) {
        this.user = user;
        try {
            socket = new Socket(ip, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(Object object) throws IOException {
        oos.writeObject(object);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Object object = ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
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


}
