package org.supportmeinc;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class Server {

    private static int port;

    public static void main(String[] args) {
        System.out.println("wtf is happening");
        port = 1030;
        Server server = new Server(port);
    }

    public Server(int port){
        try {
            ServerLog.log("Starting cm");
            ConnectionManager connectionManager = new ConnectionManager(new ServerSocket(port), new GuideManager());
            ServerLog logger = new ServerLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

