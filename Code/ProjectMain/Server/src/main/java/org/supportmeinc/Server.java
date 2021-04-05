package org.supportmeinc;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;

public class Server {

    private static int port;

    public static void main(String[] args) {
        port = 1028;
        Server server = new Server(port);
    }



    public Server(int port){
        try {
            Server.log("Starting cm");
            ConnectionManager connectionManager = new ConnectionManager(new ServerSocket(port), new GuideManager());
        } catch (IOException e) {
            log(e.getMessage());
        }
    }

    public static void log(String loggedMessage){
        System.out.println(new Date() + " : " + loggedMessage);
    }
}
