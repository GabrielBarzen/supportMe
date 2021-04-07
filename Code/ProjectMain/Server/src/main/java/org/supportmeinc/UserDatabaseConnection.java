package org.supportmeinc;

import shared.User;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
            ServerLog.log("Unable to connect to database");
            throwables.printStackTrace();
        }
    }

    public boolean authenticate(User user, String hashedPassword){
        return false; //todo: attempt user authentication
    }

    public boolean lookupUser(User user) {
        return false; //todo: database user email lookup
    }

    public String getSalt(User user) {
        return null;
    }

    public boolean registerUser(User user) {
        return false;
    }

    public User login(User user, String passwordHash) {
        return null;
    }

    public boolean registerUser(User user, String passwordHash, String salt) {
        return false;
    }
}
