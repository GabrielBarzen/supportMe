package org.supportmeinc;

import shared.User;

import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

public class UserDatabaseConnection {

    java.sql.Connection dbConnection;

    public UserDatabaseConnection(){
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

    public String getSalt(User user) {
        try {
            String query = "SELECT get_salt(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1,user.getEmail());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String retvalue = rs.getString(1);
                return retvalue;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User authenticate(User user, String passwordHash) {
        try {
            String query = "SELECT * FROM login(?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, passwordHash);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user.setUserName(rs.getString(1));
                user.setImage(rs.getBytes(2));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registerUser(User user, String passwordHash, String salt) {
        try {

            String query = "select register_user(?, ?, ?, ?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, passwordHash);
            statement.setString(3, user.getUserName());
            statement.setString(4, salt);
            statement.setBytes(5, user.getImage());
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                return rs.getInt(1) == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
