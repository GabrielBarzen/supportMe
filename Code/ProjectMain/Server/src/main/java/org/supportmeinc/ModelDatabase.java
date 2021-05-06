package org.supportmeinc;


import org.postgresql.ds.common.PGObjectFactory;
import org.postgresql.util.PGobject;
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
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

public class ModelDatabase {
    java.sql.Connection dbConnection;

    private String modelDbName;
    private String modelDbPassword;
    private String dbIp;
    private DatabaseManager databaseManager;


    public ModelDatabase(){
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

    public ModelDatabase(DatabaseManager databaseManager){
        this();
        this.databaseManager = databaseManager;
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

    public Thumbnail[] getThumbnailsFromUUID(UUID[] guideUUIDS) {
        Thumbnail[] returnValues = null;
        ArrayList<Thumbnail> temp = new ArrayList<>();
        for (UUID uuid: guideUUIDS) {
            temp.add(getThumbnail(uuid));
        }
        returnValues = temp.toArray(new Thumbnail[0]);
        System.out.println(Arrays.toString(returnValues));
        return returnValues;
    }

    public Card[] getCards(UUID guideUUID){
        Card[] cards = null;

        try {
            String query = "select * from get_cards(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setObject(1, guideUUID);

            ArrayList<Card> returnCardsList = new ArrayList<>();

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Card newCard;
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
            String query = "select * from get_thumbnail(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setObject(1, guideUUID);

            ResultSet rs = statement.executeQuery();
        if(rs.next()){
            UUID guideUUIDFromDatabase = (UUID) rs.getObject("guide_uuid");
            System.out.println("thumbnail with uuid : " + guideUUIDFromDatabase);
            returnThumbnail = new Thumbnail(guideUUIDFromDatabase);
            String title = rs.getString(2);
            returnThumbnail.setTitle(title);
            String text = rs.getString(3);
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
            String query = "select * from get_card(?)";
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
                String query = "select * from get_guide(?)";
                PreparedStatement statement = dbConnection.prepareStatement(query);
                statement.setObject(1, guideUUID);

                ResultSet rs = statement.executeQuery();
                if(rs.next()){
                    descriptionCardUUID = (UUID) rs.getObject(2);
                    guideUUIDFromDatabase = (UUID) rs.getObject(1);
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

    public boolean addGuide(Guide guide){
        boolean success = false;
        try {
            String query = "select add_guide(?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setObject(1, guide.getGuideUUID());
            statement.setString(2, guide.getAuthorEmail());
            statement.execute();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean addedCards = addCards(guide.getCards(), guide.getGuideUUID(), guide.getDescriptionCard().getCardUUID());
        if (addedCards) {
            boolean addedThumb = addThumbnail(guide.getThumbnail());
            if (addedThumb){
                ServerLog.log("added guide" + guide.getGuideUUID());
                success = true;
            }
        }
        return success;
    }

    private boolean addCards(Card[] cards, UUID guideUUID, UUID descriptionCardUUID) {
        boolean success = false;
        try {
            for (Card card: cards) {
                String query = "select add_card(?,?,?,?,?,?,?)";
                PreparedStatement statement = dbConnection.prepareStatement(query);
                statement.setObject(1, card.getCardUUID());
                statement.setObject(2, card.getAffirmUUID());
                statement.setObject(3, card.getNegUUID());
                statement.setString(4, card.getTitle());
                statement.setString(5, card.getText());
                statement.setBytes(6, card.getImage());
                statement.setObject(7, guideUUID);
                success = statement.execute();
                if(!success){
                    ServerLog.log("failed adding card");
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String query = "select add_description_card_guide(?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setObject(1, guideUUID);
            statement.setObject(2, descriptionCardUUID);
            statement.execute();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    private boolean addThumbnail(Thumbnail thumbnail) {
        boolean success = false;
        try {
            String query = "select add_thumbnail(?,?,?,?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setObject(1, thumbnail.getGuideUUID());
            statement.setString(2, thumbnail.getTitle());
            statement.setString(3, thumbnail.getDescription());
            statement.setBytes(4, thumbnail.getImage());
            success = statement.execute();
            if(!success){
                ServerLog.log("failed adding thumbnail");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean saveGuide(Guide guide) {
        boolean success = false;
        boolean guideExist = false;
        try {
            String query = "select exist_guide(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setObject(1, guide.getGuideUUID());
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                guideExist = rs.getBoolean(1);
            }
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (guideExist){
            success = removeGuide(guide);
        }
        if (success){
            success = addGuide(guide);
        }
        return success;

    }

    public boolean removeGuide(UUID uuid){
        return removeGuide(new Guide(uuid));
    }

    public boolean removeGuide(Guide guide) {
        boolean success = false;
        try {
            String query = "select remove_guide(?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setObject(1, guide.getGuideUUID());
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
