package org.supportmeinc;

import shared.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class ConnectionManager implements Runnable, ObjectReceivedListener{

    private ServerSocket serverSocket;
    private Thread acceptConnectionThread;

    private HashMap<User,Connection> userConnection;
    private DatabaseManager databaseManager;


    public ConnectionManager(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;

        databaseManager = new DatabaseManager();
        userConnection = new HashMap<>();

        start();
    }

    private void start(){
        if (acceptConnectionThread == null) {
            acceptConnectionThread = new Thread(this);
            acceptConnectionThread.start();
        }
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
        if (object instanceof Connection) {
            Connection connection = (Connection) object;
            ServerLog.log("ConnectionManager attempting auth");
            Authenticator auth = new Authenticator(user, databaseManager);
            User loggedInUser = auth.authenticate();
            if(loggedInUser != null) {
                ServerLog.log("Auth status : " + loggedInUser.getEmail() + " logged in" );
                ServerLog.log("Adding user to connections map");
                connection.setUser(loggedInUser);
                userConnection.put(loggedInUser,connection);
                connection.sendObject(loggedInUser);
            } else {
                ServerLog.log("Auth status : could not log in" );
                connection.sendObject(null);
                try {
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (object instanceof Guide) {
            Guide guide = (Guide) object;
            boolean success = databaseManager.saveGuide(guide, user);
            userConnection.get(user).sendObject(success);
        }

        if (object instanceof Thumbnail) {
            Thumbnail thumbnail = (Thumbnail) object;
            userConnection.get(user).sendObject(databaseManager.getGuide(thumbnail.getGuideUUID()));
        }

        if (object instanceof Thumbnail[]) {
            Thumbnail[] guideAccessThumbnails;
            Thumbnail[] guideAuthorThumbnails;

            guideAccessThumbnails = databaseManager.getAccessThumbnails(user);
            guideAuthorThumbnails = databaseManager.getAuthorThumbnails(user);

            userConnection.get(user).sendObject(guideAccessThumbnails);
            userConnection.get(user).sendObject(guideAuthorThumbnails);
        }

        if (object instanceof String) {
            String request = (String) object;
            String[] requestParts = request.split(":");
            System.out.println(Arrays.toString(requestParts));

            if (requestParts[0].equals("grant")) {
                databaseManager.grantAccess(requestParts[2], UUID.fromString(requestParts[1]));
            } else if (requestParts[0].equals("revoke")) {
                databaseManager.revokeAccess(requestParts[2], UUID.fromString(requestParts[1]));
            } else if (requestParts[0].equals("getAccessList")) {
                userConnection.get(user).sendObject(databaseManager.getAccessList(requestParts[1]));
            } else {ServerLog.log("could not grant/revoke access to guide");}

        }
    }
}
