package org.supportmeinc.model;


import shared.Guide;
import shared.Thumbnail;
import shared.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.UUID;

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
        System.out.println("SEND:::::::::::::::::::::::" + object.getClass());
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

    public void grantAccess(UUID uuid, String email) { // grant access to email on guide from uuid
        String request = requestBuilder(requestType.grant, uuid.toString() + ":" + email);
        send(request);
    }

    public void revokeAccess(UUID uuid, String email) { // revoke access to email on guide from uuid
        String request = requestBuilder(requestType.revoke, uuid.toString() + ":" + email);
        send(request);
    }

    public String[] getAccessList(UUID uuid) { // get access list from guide uuid, null if no access on guide is granted
        String[] returnArray = null;
        String request = requestBuilder(requestType.getAccessList, uuid.toString());
        send(request);
        try {
            Object arrayObject = receiveBuffer.get();
            if (arrayObject instanceof String[]) {
                returnArray = (String[]) arrayObject;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnArray;
    }

    private String requestBuilder(requestType type, String data){
        String request = null;
        request = String.format("%s:%s",type.name(), data);
        return request;
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
                    System.out.println("RECIEVCEDDE SOCKET IS CLOSED:::::: " + socket.isClosed());
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
                    System.out.println("EN FÖRRSTA FIN RAD");
                    Object object = sendBuffer.get();
                    System.out.println("SENDING:::::::::::::::::::::::" + object.getClass());
                    System.out.println("SOCKET IS CLOSED:::::: " + socket.isClosed());
                    outputStream.writeObject(object);
                    System.out.println("EN ANDRA FIN RAD");
                    outputStream.flush();
                    System.out.println("EN TREDJE FIN RAD");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}
