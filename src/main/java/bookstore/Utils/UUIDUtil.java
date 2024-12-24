package bookstore.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UUIDUtil {
    public static String generateUUIDFromId(Long id) {
        try {
            String input = String.valueOf(id);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.substring(0, 20);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating UUID from ID", e);
        }
    }
}
