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
    private Connection connection = this;


    private ObjectReceivedListener objectReceivedListener;

    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;

    public Connection(Socket socket) throws IOException {
        ServerLog.log("Establishing connection");

        this.socket = socket;
        this.send = new Send();
        this.receive = new Receive();

        outputStream = new ObjectOutputStream(this.socket.getOutputStream());
        inputStream = new ObjectInputStream(this.socket.getInputStream());

        ServerLog.log("Starting send & receive threads");
        send.start();
        receive.start();
    }

    public void disconnect() throws IOException{
        send.interrupt();
        receive.interrupt();
        socket.close();
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
        System.out.println("sending object : " + object.getClass());
        send.sendObject(object);
    }

    private class Send extends Thread{

        Buffer<Object> objectBuffer = new Buffer<>();

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    try {
                        ServerLog.log("Send waiting for object to send");
                        outputStream.writeObject(objectBuffer.get());
                    } catch (IOException e) {
                    }
                }
            } catch (InterruptedException e){
                ServerLog.log("Connection with client lost");
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
                ServerLog.log("Waiting for user-Object from user");
                Object userObj = inputStream.readObject();
                if (userObj instanceof User && user == null){
                    setUser((User) userObj);
                    objectReceivedListener.objectReceived(connection,user);
                } else {
                    ServerLog.log("First object received not of class : User");
                    send.interrupt();
                }
                while (!Thread.interrupted()){
                    ServerLog.log("Waiting for object from user");
                    Object object = inputStream.readObject();
                    objectReceivedListener.objectReceived(object,getUser());
                }
            } catch (IOException e) {
                try {
                    disconnect();
                } catch (IOException ex){
                    System.out.println(ex.getMessage());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

