package org.supportmeinc;

import shared.User;

public class UserDatabaseConnection {
    public boolean authenticate(User user, String hashedPassword){
        return false;
    }

    public boolean lookupUser(User user) {
        if(user.getEmail().equals("1@1.com")){
            return true;
        }
        if(user.getEmail().equals("2@2.com")){
            return false;
        }
        if(user.getEmail().equals("2@2.com")){
            return false;
        }
        if(user.getEmail().equals("2@2.com")){
            return true;
        }


        return false;
    }

    public String getSalt(User user) {
        return null;
    }

    public boolean newUser(User user) {
        return false;
    }
}
