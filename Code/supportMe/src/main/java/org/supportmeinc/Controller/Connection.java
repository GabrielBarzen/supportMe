package org.supportmeinc.Controller;

import org.supportmeinc.Model.Card;
import org.supportmeinc.Model.Guide;
import org.supportmeinc.Model.Thumbnail;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class Connection {

    private Socket socket;

    public Connection(String ip, int port) {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Guide getGuide(UUID guideUUID) {
        return fakeGuide();
    }

    public Thumbnail[] getThumbnails() {
        return null;
    }

    public Guide fakeGuide() {
        Guide guide = new Guide();
        Card card = new Card();
        //TODO skapa stubbe för description card och lägg till i guiden

        return guide;
    }
}
