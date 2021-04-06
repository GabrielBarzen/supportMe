package org.supportmeinc;

import shared.Thumbnail;
import shared.User;

import java.io.*;
import java.net.Socket;

public class Connection {

    private Socket socket;
    private Send send;
    private Receive receive;
    private User user;


    private ObjectReceivedListener objectReceivedListener;

    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.send = new Send();
        this.receive = new Receive();
        ServerLog.log("New Connection");

        inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        ServerLog.log("Starting IO threads");
        send.start();
        receive.start();
        ServerLog.log("awaiting User");
    }

    public void setObjectReceivedListener(ObjectReceivedListener listener){
        this.objectReceivedListener = listener;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    public void sendObject(Object object) {
        send.sendObject(object);
    }

    private class Send extends Thread{


        Buffer<Object> objectBuffer = new Buffer<>();

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()){
                    try{
                        outputStream.writeObject(objectBuffer.get());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void sendObject(Object object) {
            objectBuffer.put(object);
        }
    }

    private class Receive extends Thread{

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()){
                        Object object = inputStream.readObject();
                        if (object instanceof User){
                            setUser((User) object);
                        }
                        objectReceivedListener.objectReceived(object,getUser());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

