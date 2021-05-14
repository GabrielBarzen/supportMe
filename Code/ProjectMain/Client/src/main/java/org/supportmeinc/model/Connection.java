package org.supportmeinc.model;


import shared.Guide;
import shared.Thumbnail;
import shared.User;

import java.io.*;
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
        socket = new Socket(ip, port);

        outputStream = new ObjectOutputStream(this.socket.getOutputStream());
        inputStream = new ObjectInputStream(this.socket.getInputStream());

        send = new Send();
        receive = new Receive();

        send.start();
        receive.start();

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

    public Guide getGuide(UUID uuid) throws InterruptedException {
        Guide guide;
        guide = guideManager.getGuide(uuid);
        return guide;
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
            success = false;
        }

        Object returnAuthorObject = receiveBuffer.get();
        Thumbnail[] returnAuthor = null;
        if (returnAuthorObject instanceof Thumbnail[] && success) {
            returnAuthor = (Thumbnail[]) returnAuthorObject;
            success = true;
        } else {
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

    public void removeGuide(UUID uuid) { // revoke access to email on guide from uuid
        String request = requestBuilder(requestType.removeGuide, uuid.toString());
        send(request);
    }

    public String[] getAccessList(UUID uuid) { // get access list from guide uuid, null if no access on guide is granted
        String[] returnArray = {};
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
            System.out.println("Save guide: " + obj);
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

    public void downloadGuide(UUID uuid) {
        Guide guide;
        try {
            guide = getGuide(uuid);
            outputStream = new ObjectOutputStream(new FileOutputStream(user.getEmail()+ ".dat"));
            outputStream.writeObject(guide);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private class Receive extends Thread {

        @Override
        public void run() {
            try {
                Object userLogin = inputStream.readObject();
                if (userLogin instanceof User){
                    //Vad är de här till för???
                } else {
                    //VA???
                }

                while (!Thread.interrupted()) {

                    Object object = inputStream.readObject();
                    receiveBuffer.put(object);


                }
            } catch (IOException e) {
                try {
                    disconnect();
                } catch (IOException ex){

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

                    Object object = sendBuffer.get();

                    outputStream.writeObject(object);

                    outputStream.flush();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}
