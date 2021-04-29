package org.supportmeinc;
import javafx.scene.image.Image;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

public class ImageUtils {

    public static Image toImage(byte[] img){
        Image image;
        image = new Image(new BufferedInputStream(new ByteArrayInputStream(img)));
        return image;
    }

    public static byte[] toBytes(File file) {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static byte[] toBytes(String filename) {
        URL url = ImageUtils.class.getResource("images/" + filename);
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
