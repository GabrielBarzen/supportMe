import org.supportmeinc.Server;

import java.io.*;
import java.net.URL;

public class ImageUtils {


    public static byte[] toBytes(File file){
        return toBytes(file.getAbsolutePath());
    }

    public static byte[] toBytes(String filename) {
        URL url = Server.class.getResource("images/" + filename);
        System.out.println(url);
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
