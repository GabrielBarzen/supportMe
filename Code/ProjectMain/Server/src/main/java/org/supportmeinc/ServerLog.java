package org.supportmeinc;

import java.util.Date;

public class ServerLog implements Runnable{

    public static Buffer<String> logBuffer = new Buffer<>();

    ServerLog(){
        Thread thread = new Thread(this);
        thread.start();
    }

    public static synchronized void log(String loggedMessage){
        logBuffer.put(new Date() + " : " + loggedMessage);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            try {
                System.out.println(logBuffer.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
