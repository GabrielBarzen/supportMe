package org.supportmeinc;

import shared.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class Authenticator {

    private final User user;
    private final UserDatabaseConnection databaseConnection;
    private boolean loginSuccess = false;

    Authenticator(User user, UserDatabaseConnection databaseConnection) {
        this.user = user;
        this.databaseConnection = databaseConnection;
    }

    private boolean newUser(){
        return databaseConnection.newUser(user);
    }

    public boolean authenticate() {
        String salt = databaseConnection.getSalt(user);
        String pwdString = String.format("%s%s",salt,user.getPassword());
        String hashedPassword = null;

        try {
            hashedPassword = hashSHA256(pwdString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (!(hashedPassword == null)){
            loginSuccess = databaseConnection.authenticate(user, hashedPassword);
        }
        return loginSuccess;
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
