package org.supportmeinc;

import shared.User;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class UserDatabaseConnection {

    UserDatabaseConnection(){
        try {
            String url = "jdbc:postgresql://84.55.115.173/supportMeUser";
            java.sql.Connection conn = null;
            Properties connectionProps = new Properties();
            connectionProps.put("user", "postgres");
            connectionProps.put("password", "supportMe");
            conn = DriverManager.getConnection(url, connectionProps);
            System.out.println("Connected to database");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

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
