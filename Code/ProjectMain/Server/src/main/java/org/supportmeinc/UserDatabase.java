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
import java.util.*;

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

    public UUID[] getGuideUUIDAccess(User user) {
        UUID[] returnValues;

        try {
            String query = "select get_all_access(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, user.getEmail());

            ResultSet rs = statement.executeQuery();
            ArrayList<UUID> UUIDList = new ArrayList<>();
            while (rs.next()){
                UUID uuid = (UUID) rs.getObject(1);
                UUIDList.add(uuid);
                System.out.println("Access of : " + uuid);

            }
            returnValues = UUIDList.toArray(new UUID[0]);

        } catch (SQLException e) {
            e.printStackTrace();
            returnValues = null;
        }
        return returnValues;
    }


    public UUID[] getGuideUUIDAuthor(User user) {
        UUID[] returnValues = null;
        System.out.println(user.getEmail());
        try {
            String query = "select get_all_author(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, user.getEmail());

            ResultSet rs = statement.executeQuery();
            ArrayList<UUID> UUIDList = new ArrayList<>();
            while (rs.next()){
                UUID uuid = (UUID) rs.getObject(1);
                UUIDList.add(uuid);
            }
            returnValues = UUIDList.toArray(new UUID[0]);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValues;
    }




    public boolean saveGuide(Guide guide) {
        boolean success = false;
        try {
            String query = "select add_guide(?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, guide.getAuthorEmail());
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

    public boolean grantAccess(String userEmail, UUID guideUUID) {
        System.out.println("granting acces to : " + userEmail + ", on guide : " + guideUUID);
        boolean success = false;
        try {
            String query = "select grant_access(?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, userEmail);
            statement.setObject(2, guideUUID);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                success = rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean revokeAccess(String userEmail, UUID guideUUID){
        boolean success = false;
        try {
            String query = "select revoke_access(?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, userEmail);
            statement.setObject(2, guideUUID);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                success = rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean removeGuide(UUID guideUUID){
        boolean success = false;
        try {
            String query = "select remove_guide(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setObject(1, guideUUID);

            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                success = rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public String[] getAccessList(UUID guideUUID) {
        String[] accessEmailArray = null;

        try {
            String query = "select get_access_list_for_guide(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setObject(1, guideUUID);

            ResultSet rs = statement.executeQuery();
            ArrayList<String> emails = new ArrayList<>();
            while (rs.next()){
                String uuid = rs.getString(1);
                emails.add(uuid);
            }
            if (emails.size() > 0) {
                accessEmailArray = emails.toArray(new String[0]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accessEmailArray;
    }
}