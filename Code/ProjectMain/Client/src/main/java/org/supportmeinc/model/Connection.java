package org.supportmeinc.model;


import shared.Guide;
import shared.Thumbnail;
import shared.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {

    private Socket socket;
    private GuideManager guideManager;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private User user;
    private Receive receive;
    private Send send;
    private ThumbnailListener listener;

    Buffer<Object> sendBuffer = new Buffer<>();
    Buffer<Object> receiveBuffer = new Buffer<>();

    public Connection(String ip, int port, User user) throws IOException {
        this.user = user;

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

    }

    public void registerListener(ThumbnailListener listener){
        this.listener = listener;
    }

    private void send(Object object){
        sendBuffer.put(object);
    }

    public Guide getGuide(Thumbnail thumbnail) throws InterruptedException{
        Guide retGuide = null;
        send(thumbnail);
        Object guide = receiveBuffer.get();
        if (guide instanceof Guide){
            retGuide = (Guide) guide;
        }
        return retGuide;
    }

    public void getThumbnails(Thumbnail[] thumbnails) throws InterruptedException{
        send(thumbnails);
        Object returnAccessObject = receiveBuffer.get();
        Thumbnail[] returnAccess = null;
        boolean success;
        if (returnAccessObject instanceof Thumbnail[]) {
            returnAccess = (Thumbnail[]) returnAccessObject;
            success = true;
        } else {
            System.out.println("error in Connection.getThumbnails accessThumbnails");
            success = false;
        }

        Object returnAuthorObject = receiveBuffer.get();
        Thumbnail[] returnAuthor = null;
        if (returnAuthorObject instanceof Thumbnail[] && success) {
            returnAuthor = (Thumbnail[]) returnAuthorObject;
            success = true;
        } else {
            System.out.println("error in Connection.getThumbnails authorThumbnails");
            success = false;
        }

        if (success ) {
            listener.thumbnailsReceived(returnAccess, returnAuthor);
        }
    }

    public boolean saveGuide(Guide guide) {
        boolean success = false;
        send(guide);
        Object obj = null;

        try {
            obj = receiveBuffer.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(obj instanceof Boolean){
            success = (Boolean) obj;
        }
        return success;
    }

    public void disconnect() throws IOException{
        send.interrupt();
        receive.interrupt();
        socket.close();
    }

    public void setGuideManager(GuideManager manager) {
        guideManager = manager;
    }

    public User getUser() {
        return user;
    }

    private class Receive extends Thread {

        @Override
        public void run() {
            try {
                Object userLogin = inputStream.readObject();
                if (userLogin instanceof User){
                    System.out.println("received user obj from server");
                } else {
                    System.out.println("Invalid login or other error");
                }

                while (!Thread.interrupted()) {

                    Object object = inputStream.readObject();
                    receiveBuffer.put(object);
                    System.out.println("loop check receive");

                }
            } catch (IOException e) {
                try {
                    disconnect();
                } catch (IOException ex){
                    System.out.println("Disconnected ");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private class Send extends Thread {

        @Override
        public void run() {
            send(user);
            try {
                while (!Thread.interrupted()) {
                        outputStream.writeObject(sendBuffer.get());
                        outputStream.flush();
                        System.out.println("loop check send");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e){

            }
        }
    }


}
