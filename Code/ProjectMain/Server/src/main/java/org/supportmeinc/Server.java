package org.supportmeinc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;


public class Server {

    private static int port;

    public static void main(String[] args) {
        ServerLog logger = new ServerLog();
        Server server = new Server();
    }

    //Configuration methods//
    private void readConfig(URL url) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Paths.get(url.toURI()).toFile()))){
            String configEntry;

            while ((configEntry = bufferedReader.readLine()) != null){
                String[] entry = configEntry.split("=");
                switch (entry[0]){
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
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Server(){
        readConfig(getClass().getResource("config.conf"));
        try {
            ServerLog.log("Starting cm");
            ConnectionManager connectionManager = new ConnectionManager(new ServerSocket(port));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


