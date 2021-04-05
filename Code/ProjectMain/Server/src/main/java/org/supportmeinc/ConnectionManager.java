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

    private UserDatabaseConnection databaseConnection;


    public ConnectionManager(ServerSocket serverSocket, GuideManager guideManager) {
        this.serverSocket = serverSocket;
        this.guideManager = guideManager;
        databaseConnection = new UserDatabaseConnection();
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
            System.out.println("ConnectionManager : awaiting connection");
            try {
                Connection connection = new Connection(serverSocket.accept());
                System.out.println("ConnectionManager : connection received");
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

                Authenticator auth = new Authenticator(user, databaseConnection);

                boolean lookupSuccess = databaseConnection.lookupUser(user);
                boolean authSuccess = auth.isLoginSuccess();

                if (user.isNewUser() && lookupSuccess){
                    Server.log("user already exists"); //TODO: when database exists
                }
                else if (user.isNewUser() && !lookupSuccess){
                    Server.log("Adding new user"); //TODO: when database exists
                }
                if(!user.isNewUser() && !lookupSuccess){
                    Server.log("user does not exist"); //TODO: when database exists
                }
                if(!user.isNewUser() && lookupSuccess){
                    Server.log("Logging in"); //TODO: when database exists

//                    auth.authenticate();
//                    userConnection.put(user, connection);
//                    newConnections.remove(connection);
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
            userConnection.get(user).sendObject(newThumbnails);
        }
    }
}
