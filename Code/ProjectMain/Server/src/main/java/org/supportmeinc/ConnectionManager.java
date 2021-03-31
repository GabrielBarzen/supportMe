package org.supportmeinc;

import shared.Guide;
import shared.Thumbnail;
import shared.User;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;

public class ConnectionManager implements Runnable, ObjectReceivedListener{

    private ServerSocket serverSocket;
    private Thread acceptConnectionThread;

    private HashMap<User,Connection> userConnection;
    private GuideManager guideManager;


    public ConnectionManager(ServerSocket serverSocket, GuideManager guideManager) {
        this.serverSocket = serverSocket;
        this.guideManager = guideManager;
        start();
    }

    private void start(){;
        if (acceptConnectionThread == null) {
            acceptConnectionThread = new Thread(this);
        } else {
            return;
        }
        acceptConnectionThread.start();
    }

    LinkedList<Connection> newConnections = new LinkedList<>();

    @Override
    public void run() {
        while (!Thread.interrupted()){
            try {
                Connection connection = new Connection(serverSocket.accept());
                connection.setObjectReceivedListener(this);
                newConnections.add(connection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void objectReceived(Object object, User user) {

        if (object instanceof User){
            for (Connection connection: newConnections) {
                if(connection.getUser() == user){
                    userConnection.put(user, connection);
                    newConnections.remove(connection);
                }
            }
        }

        if (object instanceof Thumbnail){
            Thumbnail thumbnail = (Thumbnail) object;
            Guide guide = guideManager.getGuide(thumbnail.getGuideUUID());
            userConnection.get(user).sendObject(guide);
        }

        if (object instanceof Thumbnail[]){
            Thumbnail[] oldThumbnails = (Thumbnail[]) object;
            Thumbnail[] newThumbnails = guideManager.getThumbNails(oldThumbnails);
            userConnection.get(user).send(newThumbnails);
        }
    }
}
