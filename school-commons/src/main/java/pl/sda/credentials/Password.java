package pl.sda.credentials;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class Password {

    private static final int KEY_LENGTH = 100;
    private static final int ITERATIONS = 5;
    private static final byte[] SALT = new byte[256];

    public static byte[] hashPassword(final String stringPass) {
        if (StringUtils.isBlank(stringPass)) {
            throw new IllegalArgumentException("Invalid input string");
        }
        char[] password = stringPass.toCharArray();
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password, SALT, ITERATIONS, KEY_LENGTH);
            SecretKey key = skf.generateSecret(spec);
            return key.getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Ups... Error in cryptography block.\n \t" + e);
        }
    }

    /**
     * CHECK IF PROVIDED PASSWORD IS THE SAME AS STORED IN DB
     *
     * @param password users password
     * @param dbPass   hashed pass from db
     * @return boolean
     */
    public static boolean checkPassword(String password, byte[] dbPass) {
        return Arrays.equals(hashPassword(password), dbPass);
    }
}
