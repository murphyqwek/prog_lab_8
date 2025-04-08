package org.example.util.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * HashSHA1Strategy - класс для хеширования по алгоритму SHA-1.
 *
 * @version 1.0
 */

public class HashSHA1Strategy implements HashStrategy {
    @Override
    public String hash(String toHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = md.digest(toHash.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm not found", e);
        }
    }
}
