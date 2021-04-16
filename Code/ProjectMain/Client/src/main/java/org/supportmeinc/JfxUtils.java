package org.supportmeinc;
import javafx.scene.image.Image;

import java.io.*;
import java.net.URL;

public class JfxUtils {

    public static Image fromBytes(byte[] img){
        Image image;
        image = new Image(new BufferedInputStream(new ByteArrayInputStream(img)));
        return image;
    }

    public static byte[] toBytes(File file){
        return toBytes(file.getAbsolutePath());
    }

    public static byte[] toBytes(String filename) {
        URL url = JfxUtils.class.getResource("images/" + filename);
        System.out.println("img = " + url);
        return toBytes(url);

    }

    private static byte[] toBytes(URL url) {
        byte[] bytes = new byte[0];
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(url.getFile()))){
            bytes = bis.readAllBytes();
        }catch (IOException e){
            e.printStackTrace();
        }
        return bytes;
    }
}
