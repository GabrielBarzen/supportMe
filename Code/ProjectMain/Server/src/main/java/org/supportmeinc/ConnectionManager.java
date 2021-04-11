package org.supportmeinc;

import shared.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;

public class ConnectionManager implements Runnable, ObjectReceivedListener{

    private ServerSocket serverSocket;
    private Thread acceptConnectionThread;

    private HashMap<User,Connection> userConnection;
    private GuideManager guideManager;

    private UserDatabaseConnection databaseConnection;


    public ConnectionManager(ServerSocket serverSocket, GuideManager guideManager) {
        this.serverSocket = serverSocket;
        this.guideManager = guideManager;
        databaseConnection = new UserDatabaseConnection();
        userConnection = new HashMap<>();
        start();
    }

    private void start(){
        if (acceptConnectionThread == null) {
            acceptConnectionThread = new Thread(this);
        } else {
            return;
        }
        acceptConnectionThread.start();
    }

    @Override
    public void run() {

        while (!Thread.interrupted()){
            ServerLog.log("awaiting connection");
            try {
                Connection connection = new Connection(serverSocket.accept());
                ServerLog.log("connection received");
                connection.setObjectReceivedListener(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void objectReceived(Object object, User user) {
        ServerLog.log("Object received from client " + object.getClass());
        if (object instanceof Connection){
            Connection connection = (Connection) object;
            ServerLog.log("ConnectionManager attempting auth");
            Authenticator auth = new Authenticator(user, databaseConnection);
            User loggedInUser = auth.authenticate();
            if(loggedInUser != null){
                ServerLog.log("Auth status : " + loggedInUser.getEmail() + " logged in" );
                ServerLog.log("Adding user to connections map");
                connection.setUser(loggedInUser);
                userConnection.put(loggedInUser,connection);
                connection.sendObject(loggedInUser);
            } else {
                ServerLog.log("Auth status : could not log in" );
                connection.sendObject(null);
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
            userConnection.get(user).sendObject(newThumbnails);
        }
    }
}
