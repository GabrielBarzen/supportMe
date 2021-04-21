package org.supportmeinc;


import shared.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

public class ModelDatabaseConnection {
    java.sql.Connection dbConnection;

    private String modelDbName;
    private String modelDbPassword;
    private String dbIp;

    public ModelDatabaseConnection(){
        URL pwdUrl = getClass().getResource("pwd.txt");;

        if (pwdUrl != null){
            readConfig(pwdUrl);
        } else {
            ServerLog.log("not able to read db connections");
        }

        try {
            String url = "jdbc:postgresql://"+dbIp+"/support_me_model";
            java.sql.Connection conn = null;
            Properties connectionProps = new Properties();
            connectionProps.put("user", modelDbName);
            connectionProps.put("password", modelDbPassword);
            dbConnection = DriverManager.getConnection(url, connectionProps);
            ServerLog.log("Connected to model-database");
        } catch (Exception e) {
            ServerLog.log("Unable to connect to model-database");
        }
    }

    private void readConfig(URL url) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Paths.get(url.toURI()).toFile()))) {
            String configEntry;

            while ((configEntry = bufferedReader.readLine()) != null) {
                String[] entry = configEntry.split("=");
                switch (entry[0]) {
                    case "model":
                        modelDbName = entry[1];
                        break;
                    case "model_password":
                        modelDbPassword = entry[1];
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

    public Thumbnail[] getCurrentThumbnails(UUID[] guideAccessUUID) {
        Thumbnail[] returnValues = null;
        for (UUID uuid: guideAccessUUID) {
            try {
                String query = "select get_thumbnail(?)";
                PreparedStatement statement = dbConnection.prepareStatement(query);
                statement.setObject(1, uuid);

                ResultSet rs = statement.executeQuery();
                ArrayList<Thumbnail> thumbnails = new ArrayList<>();
                while (rs.next()){
                    UUID guideUUID = (UUID) rs.getObject(1);
                    Thumbnail thumbnail = new Thumbnail(guideUUID);
                    String thumbnailTitle = rs.getString(4);
                    thumbnail.setTitle(thumbnailTitle);
                    String thumbnailText = rs.getString(5);
                    thumbnail.setDescription(thumbnailText);
                    byte[] thumbnailImage = rs.getBytes(6);
                    thumbnail.setImage(thumbnailImage);

                    thumbnails.add(thumbnail);
                }
                returnValues = thumbnails.toArray(new Thumbnail[0]);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return returnValues;
    }

    public Card[] getCards(UUID guideUUID){
        Card[] cards = null;

        try {
            String query = "select get_cards(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setObject(1, guideUUID);

            ArrayList<Card> returnCardsList = new ArrayList<>();

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Card newCard = new Card();
                UUID cardUUIDFromDatabase = (UUID) rs.getObject(1);
                newCard = new Card(cardUUIDFromDatabase);
                UUID affirmativeUUID = (UUID) rs.getObject(2);
                newCard.setAffirmUUID(affirmativeUUID);
                UUID negativeUUID = (UUID) rs.getObject(3);
                newCard.setNegUUID(negativeUUID);
                String cardTitle = rs.getString(4);
                newCard.setTitle(cardTitle);
                String cardText = rs.getString(5);
                newCard.setText(cardText);
                byte[] cardImage = rs.getBytes(6);
                newCard.setImage(cardImage);
                returnCardsList.add(newCard);
            }

            cards = returnCardsList.toArray(new Card[0]);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards; 
    }

    public Thumbnail getThumbnail(UUID guideUUID){
        Thumbnail returnThumbnail = null;
        try {
            String query = "select get_thumbnail(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setObject(1, guideUUID);

            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                UUID guideUUIDFromDatabase = (UUID) rs.getObject(1);
                returnThumbnail = new Thumbnail(guideUUIDFromDatabase);
                String title = rs.getString(2);
                returnThumbnail.setTitle(title);
                String text= rs.getString(3);;
                returnThumbnail.setDescription(text);
                byte[] image = rs.getBytes(4);
                returnThumbnail.setImage(image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnThumbnail;
    }

    public Card getCard(UUID cardUUID){
        Card returnCard = null;
        try {
            String query = "select get_card(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setObject(1, cardUUID);

            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                UUID cardUUIDFromDatabase = (UUID) rs.getObject(1);
                returnCard = new Card(cardUUIDFromDatabase);
                UUID affirmativeUUID = (UUID) rs.getObject(2);
                returnCard.setAffirmUUID(affirmativeUUID);
                UUID negativeUUID = (UUID) rs.getObject(3);
                returnCard.setNegUUID(negativeUUID);
                String cardTitle = rs.getString(4);
                returnCard.setTitle(cardTitle);
                String cardText = rs.getString(5);
                returnCard.setText(cardText);
                byte[] cardImage = rs.getBytes(6);
                returnCard.setImage(cardImage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnCard; //TODO: get card from database based on cardUUID
    }

    public Guide getGuide(UUID guideUUID){
        Guide guide = null;
        Card[] cards = getCards(guideUUID);
        Thumbnail thumbnail = getThumbnail(guideUUID);

        UUID descriptionCardUUID = null;
        UUID guideUUIDFromDatabase = null;

        if(!(cards == null || thumbnail == null)){
            try {
                String query = "select get_guide(?)";
                PreparedStatement statement = dbConnection.prepareStatement(query);
                statement.setObject(1, guideUUID);

                ResultSet rs = statement.executeQuery();
                if(rs.next()){
                    guideUUIDFromDatabase = (UUID) rs.getObject(1);
                    descriptionCardUUID = (UUID) rs.getObject(2);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (descriptionCardUUID != null) {
                guide = new Guide(guideUUIDFromDatabase);
                Card descriptionCard = getCard(descriptionCardUUID);
                guide.setCards(cards);
                guide.setAuthor("n/a"); //TODO: add author to modelDB guide table
                guide.setDescriptionCard(descriptionCard);
                guide.setThumbnail(thumbnail);
            }
        }

        return guide;
    }
}
