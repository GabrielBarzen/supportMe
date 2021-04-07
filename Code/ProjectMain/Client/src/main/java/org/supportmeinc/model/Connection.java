package org.supportmeinc.model;


import java.io.*;
import java.net.Socket;
import java.util.UUID;
import shared.*;

public class Connection {

    private Socket socket;
    private GuideManager guideManager;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private User user;
    private Receive receive;
    private Send send;

    public Connection(String ip, int port, User user) {
        this.user = user;
        try {
            System.out.println("starting connection");
            socket = new Socket(ip, port);
            System.out.println("starting connected");
            System.out.println("opening streams");
            outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            inputStream = new ObjectInputStream(this.socket.getInputStream());
            System.out.println("Starting threads");

            send = new Send();
            receive = new Receive();
            System.out.println("threads created");

            send.start();
            receive.start();
            System.out.println("threads started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Receive extends Thread {

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {

                        inputStream.readObject();
                        System.out.println("loop check receive");

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    Buffer<Object> objectBuffer = new Buffer<>();

    public void send(Object object){
        objectBuffer.put(object);
    }

    private class Send extends Thread {

        @Override
        public void run() {
            send(user);
            try {
                while (!Thread.interrupted()) {
                        outputStream.writeObject(objectBuffer.get());
                        outputStream.flush();
                        System.out.println("loop check send");
                }
            } catch (IOException | InterruptedException e) {
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

    public void disconnect() {
        try {
            socket.close();
//            guideManager.loadGuides();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGuideManager(GuideManager manager) {
        guideManager = manager;
    }

}
