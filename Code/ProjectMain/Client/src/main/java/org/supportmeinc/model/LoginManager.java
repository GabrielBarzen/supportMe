package org.supportmeinc.model;

import org.supportmeinc.ImageUtils;
import shared.User;

public class LoginManager {

    private User currentUser;

    public void login() {
        currentUser.setNewUser(false);
        currentUser.setEmail("Can be null if username is not");
        currentUser.setUserName("This will get changed when GUI is done");
        currentUser.setPassword("F");
    }

    public void register() {
        currentUser.setNewUser(true);
        currentUser.setUserName("Will be changed");
        currentUser.setEmail("willgetchanged@two.o");
        currentUser.setPassword("something");
    }

    public void updateUser() {
        currentUser.setUserName("Will we let them change this?");
        currentUser.setPassword("is this encrypted?");
        currentUser.setEmail("youDontwantGoogleToFollowYou@now.com");
        currentUser.setImage(ImageUtils.toBytes("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmedia.giphy.com%2Fmedia%2Fl3q2zVr6cu95nF6O4%2Fgiphy.gif&f=1&nofb=1"));

    }
}