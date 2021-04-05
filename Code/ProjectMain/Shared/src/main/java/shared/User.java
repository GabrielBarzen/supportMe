package shared;

import java.awt.*;

public class User {
    private String name;
    private String password;
    private Image image;

    public User(String name, String password, Image image) {
        this.name = name;
        this.password = password;
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }

}
