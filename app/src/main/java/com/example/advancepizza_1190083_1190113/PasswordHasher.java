package com.example.advancepizza_1190083_1190113;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class PasswordHasher {

    public static String hashPassword(String password) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add password bytes to digest
            md.update(password.getBytes(StandardCharsets.UTF_8));

            // Get the hashed bytes
            byte[] hashedBytes = md.digest();

            // Convert hashed bytes to a hexadecimal format
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                hexStringBuilder.append(String.format("%02x", hashedByte));
            }

            return hexStringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

