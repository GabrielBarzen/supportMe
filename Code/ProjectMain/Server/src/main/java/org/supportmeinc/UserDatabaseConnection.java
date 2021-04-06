package org.supportmeinc;

import shared.User;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        try {
            String query = "select get_user(" + user.getEmail() + ");";

            Statement st = dbConnection.createStatement();
            ResultSet rs = st.executeQuery(query);

            return rs.getInt(0) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getSalt(User user) {
        try {
            String query = "select get_salt(" + user.getEmail() + ");";

            Statement st = dbConnection.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                return rs.getString(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean login(User user, String passwordHash) {
        try {
            String query = "select login(" + user.getEmail() + ", " + passwordHash + ");";

            Statement st = dbConnection.createStatement();
            ResultSet rs = st.executeQuery(query);

            return rs.getInt(0) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean registerUser(User user, String passwordHash, String salt) {
        try {
            Statement st = dbConnection.createStatement();
            ResultSet rs = st.executeQuery("");
        } catch (SQLException e) {

        }
        return false;
    }
}
