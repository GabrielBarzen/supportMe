package org.supportmeinc;

import shared.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class Authenticator {

    private final User user;
    private final UserDatabaseConnection databaseConnection;
    private boolean loginSuccess = false;

    Authenticator(User user, UserDatabaseConnection databaseConnection) {
        this.user = user;
        this.databaseConnection = databaseConnection;
    }

    private boolean newUser(){
       // return databaseConnection.registerUser(user);
        return false;
    }

    public User authenticate()  {
        String salt = null;
        String password = null;
        String passwordHash = null;

        if(user.isNewUser()){
            StringBuilder saltBuilder = new StringBuilder(20);

            Random random = new Random();
            char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVXYZ1234567890".toCharArray();

            for (int i = 0; i < 20; i++) {
                char c = chars[random.nextInt(chars.length)];
                saltBuilder.append(c);
            }

            salt = saltBuilder.toString();
        } else {
            salt = databaseConnection.getSalt(user);
        }
        try {
            password = String.format("%s%s", salt, user.getPassword());
            passwordHash = hashSHA256(password);
        } catch (NoSuchAlgorithmException e){
            ServerLog.log("ERROR : Authenticator.authenticate() no such algorithm");
        }

        if(user.isNewUser() && user != null && passwordHash != null && salt != null ){
            boolean success = databaseConnection.registerUser(user,passwordHash,salt);
            if(!success){
                ServerLog.log("Could not register user");
                return null;
            }
        }

        User loggedInUser = databaseConnection.login(user,passwordHash);

        return loggedInUser;
    }

    private static String hashSHA256(String password) throws NoSuchAlgorithmException {
        final MessageDigest digest;
        digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(password.getBytes(StandardCharsets.UTF_8 ));

        StringBuilder hexString = new StringBuilder(2 * hashbytes.length);
        for (int i = 0; i < hashbytes.length; i++) {
            String hex = Integer.toHexString(0xff & hashbytes[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

}
