package org.supportmeinc;



import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import shared.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

public class UserDatabase {

    java.sql.Connection dbConnection;

    private String userDbName;
    private String userDbPassword;
    private String dbIp;
    private DatabaseManager databaseManager;

    public UserDatabase(){
        URL pwdUrl = getClass().getResource("pwd.txt");;

        if (pwdUrl != null){
            readConfig(pwdUrl);
        } else {
            ServerLog.log("not able to read db connections");
        }

        try {
            String url = "jdbc:postgresql://"+dbIp+"/support_me_user";
            java.sql.Connection conn = null;
            Properties connectionProps = new Properties();
            connectionProps.put("user", userDbName);
            connectionProps.put("password", userDbPassword);
            dbConnection = DriverManager.getConnection(url, connectionProps);
            ServerLog.log("Connected to user-database");
        } catch (Exception e) {
            ServerLog.log("Unable to connect to user-database");
        }
    }

    private void readConfig(URL url) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Paths.get(url.toURI()).toFile()))) {
            String configEntry;

            while ((configEntry = bufferedReader.readLine()) != null) {
                String[] entry = configEntry.split("=");
                switch (entry[0]) {
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

        } catch (FileNotFoundException e) {
            System.out.println("Config file not found");
        } catch (IOException | URISyntaxException e) {
            System.out.println("Read exception in config");
        }
    }

    public boolean getAuthor(UUID guideUUID){
        return false;//TODO get author from guide
    }

    public String getSalt(User user) {
        String returnValue = null;
        try {
            String query = "SELECT get_salt(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1,user.getEmail());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                returnValue = rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public User authenticate(User user, String passwordHash) {

        User returnValue = null;
        try {
            String query = "SELECT * FROM login(?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, passwordHash);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                returnValue = new User(user.getEmail(),rs.getString(1),null,rs.getBytes(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public boolean registerUser(User user, String passwordHash, String salt) {
        boolean returnValue = false;
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
                returnValue = rs.getInt(1) == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public UserDatabase(DatabaseManager databaseManager){
        this();
        this.databaseManager = databaseManager;
    }

    public UUID[] getGuideUUIAccess(User user) {
        UUID[] returnValues = null;
        System.out.println(user.getEmail());
        try {
            String query = "select get_all_access(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, user.getEmail());

            ResultSet rs = statement.executeQuery();
            ArrayList<UUID> UUIDList = new ArrayList<>();
            while (rs.next()){
                UUID uuid = (UUID) rs.getObject(1);
                System.out.println(uuid.toString());
                UUIDList.add((UUID) rs.getObject(1));
            }
            returnValues = UUIDList.toArray(new UUID[0]);
            System.out.println("retval uuid : " + returnValues[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValues;
    }

    public boolean giveAccess(String authorEmail, String userEmail, UUID guideUUID){
        boolean success = false;
        return success; //TODO : write queries and code for assigning access to guide
    }

    public boolean revokeAccess(String authorEmail, String userEmail, UUID guideUUID){
        boolean success = false;
        return success; //TODO : write queries and code for revoking access to guide
    }

    public boolean saveGuide(Guide guide, User user) {
        boolean success = false;
        try {
            String query = "select add_guide(?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setObject(2, guide.getGuideUUID());
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                success = rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}
