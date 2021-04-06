package org.supportmeinc;

import shared.User;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class UserDatabaseConnection {

    java.sql.Connection dbConnection;

    UserDatabaseConnection(){
        try {
            String url = "jdbc:postgresql://84.55.115.173/supportMeUser";
            java.sql.Connection conn = null;
            Properties connectionProps = new Properties();
            connectionProps.put("user", "postgres");
            connectionProps.put("password", "supportMe");
            dbConnection = DriverManager.getConnection(url, connectionProps);
            ServerLog.log("Connected to database");
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
        if(user.getEmail().equals("3@3.com")){
            return false;
        }
        if(user.getEmail().equals("4@4.com")){
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
