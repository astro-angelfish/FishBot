package moe.orangemc.fishbot.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncryptor {
    public static String encryptPassword(String username, String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes(StandardCharsets.UTF_8));
            md.update("|0w0|".getBytes(StandardCharsets.UTF_8));
            md.update(username.getBytes(StandardCharsets.UTF_8));
            byte[] encryptedBytes = md.digest();
            return Base64.getUrlEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
