package org.supportmeinc.model;
import javafx.scene.image.Image;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class JfxUtils {
    public static byte[] toBytes(String url) {
        byte[] bytes = new byte[0];
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(url))){
            bytes = bis.readAllBytes();
        }catch (IOException e){
            e.printStackTrace();
        }
        return bytes;
    }

    public static byte[] toBytes(File file){
        return toBytes(file.getAbsolutePath());
    }

    public static Image fromBytes(byte[] img){
        Image image;
        image = new Image(new BufferedInputStream(new ByteArrayInputStream(img)));
        return image;
    }

    public static byte[] toBytes(URL url) {
        try {
            File file = Paths.get(url.toURI()).toFile();
            return toBytes(file.getAbsolutePath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
