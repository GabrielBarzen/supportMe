import org.supportmeinc.Server;
import shared.Thumbnail;
import shared.User;

import java.io.*;
import java.net.Socket;

public class ServerUserTest extends Thread {

    private static int port = 1028;

    public static void main(String[] args) {
        new ServerUserTest();
    }

    private User testDataUsers[];
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;

    ServerUserTest(){
        User existDataUser = new User("1@1.com","exist","123456789");
        User notExistDataUser = new User("2@2.com","notExist","123456789");
        User newDataUser = new User("3@3.com","newUser","123456789");
        newDataUser.setNewUser(true);
        User newDataUserDuplicate= new User("4@4.com","duplicateNewUser","123456789");
        newDataUserDuplicate.setNewUser(true);

        testDataUsers = new User[]{existDataUser, notExistDataUser, newDataUser, newDataUserDuplicate};

        try{
            Client(port);
        } catch (IOException e){

        }
    }

    public void Client(int port) throws IOException {
        System.out.println("Waiting connection");
        socket = new Socket("127.0.0.1",port);
        System.out.println("Connected");
        ObjectInputStream bis = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        ObjectOutputStream bos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        input = bis;
        output = bos;
        System.out.println("connection established");
        start();
    }

    @Override
    public void run() {
        System.out.println("connection established");
        while (!Thread.interrupted()){
            for (int i = 0; i < testDataUsers.length; i++) {
                try {
                    output.writeObject(testDataUsers[i]);
//                    Object object = input.readObject();
//                    if (object instanceof User){
//                        testDataUsers[i] = (User) object;
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                input.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
