package org.supportmeinc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;

public class Server {

    private static String ip;
    private static int port;

    public static void main(String[] args) {
        //readConfig();

        Server server = new Server(port);
    }

    private static void readConfig() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("config.conf"))){
            String configEntry;

            while ((configEntry = bufferedReader.readLine()) != null){
                String[] entry = configEntry.split("=");
                switch (entry[0]){
                    case "ip":
                        ip = entry[1];
                        break;
                    case "port":
                        port = Integer.parseInt(entry[1]);
                        break;
                    default:
                        System.out.println("Config entry : " + entry[0] + " is not a valid config entry");
                }
            }

        } catch (FileNotFoundException e){
            System.out.println("Config file not found");
        } catch (IOException e){
            System.out.println("Read exception in config");
        }
    }

    Server(int port){
        try {
            ConnectionManager connectionManager = new ConnectionManager(new ServerSocket(1028), new GuideManager());
        } catch (IOException e) {
            log(e.getMessage());
        }
    }

    public static void log(String loggedMessage){
        System.out.println(new Date() + " : " + loggedMessage);
    }
}
