package org.supportmeinc;

import shared.User;


import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import java.util.Properties;

public class UserDatabaseConnection {

    java.sql.Connection dbConnection;

    private String userDbName;
    private String userDbPassword;
    private String dbIp;

    public UserDatabaseConnection(){
        URL pwdUrl = getClass().getClassLoader().getResource(String.format(".%spwd.txt", File.separatorChar));;
        readConfig(pwdUrl);

        try {
            String url = "jdbc:postgresql://"+dbIp+"/support_me_user";
            java.sql.Connection conn = null;
            Properties connectionProps = new Properties();
            connectionProps.put("user", userDbName);
            connectionProps.put("password", userDbPassword);
            dbConnection = DriverManager.getConnection(url, connectionProps);
            ServerLog.log("Connected to database");
        } catch (SQLException throwables) {
            ServerLog.log("Unable to connect to database");
            throwables.printStackTrace();
        }
    }

    private void readConfig(URL url) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Paths.get(url.toURI()).toFile()))){
            String configEntry;

            while ((configEntry = bufferedReader.readLine()) != null){
                String[] entry = configEntry.split("=");
                switch (entry[0]){
                    case "user":
                        userDbName = entry[1];
                        break;
                    case "user_password":
                        userDbPassword = entry[1];
                        break;
                    case "db_ip":
                        dbIp = entry[1];
                        break;
                }
            }

        } catch (FileNotFoundException e){
            System.out.println("Config file not found");
        } catch (IOException | URISyntaxException e){
            System.out.println("Read exception in config");
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
