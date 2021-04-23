package org.supportmeinc;

import shared.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class Authenticator {

    private final User user;
    private final DatabaseManager databaseManager;


    public Authenticator(User user, DatabaseManager databaseManager) {
        this.user = user;
        this.databaseManager = databaseManager;
    }

    public User authenticate()  {
        String salt = null;
        String password = null;
        String passwordHash = null;
        User returnUser = null;

        if(user.isNewUser()){
            StringBuilder saltBuilder = new StringBuilder(20);

            Random random = new Random();
            char[] chars = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789".toCharArray();

            for (int i = 0; i < 20; i++) {
                char c = chars[random.nextInt(chars.length)];
                saltBuilder.append(c);
            }

            salt = saltBuilder.toString();
        } else {
            salt = databaseManager.getSalt(user);
        }
        try {
            password = String.format("%s%s", salt, user.getPassword());
            passwordHash = hashSHA256(password);
        } catch (NoSuchAlgorithmException e){
            ServerLog.log("ERROR : Authenticator.authenticate() no such algorithm");
        }

        if(user.isNewUser() && passwordHash != null && salt != null){
            boolean success = databaseManager.registerUser(user,passwordHash,salt);
            if(!success){
                ServerLog.log("Could not register user");
            } else {
                returnUser = databaseManager.authenticate(user,passwordHash);
            }
        } else {
            returnUser = databaseManager.authenticate(user,passwordHash);
        }

        return returnUser;
    }

    private static String hashSHA256(String password) throws NoSuchAlgorithmException {
        final MessageDigest digest;
        digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(password.getBytes(StandardCharsets.UTF_8 ));

        StringBuilder hexString = new StringBuilder(2 * hashbytes.length);
        for (byte hashbyte : hashbytes) {
            String hex = Integer.toHexString(0xff & hashbyte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

}
