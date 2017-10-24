package com.clogic.resourceserver.security;

import lombok.experimental.PackagePrivate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

@Slf4j
@PackagePrivate
public class SecurityUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtils.class);

    private SecurityUtils() {
    }

    public static String generateHash(String input, String salt, Integer iterations) {
        String passwordHash = null;
        if (null == input) {
            return null;
        }
        try {
            char[] chars = input.toCharArray();
            byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);

            PBEKeySpec spec = new PBEKeySpec(chars, saltBytes, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            passwordHash = toHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Could not generate hashed password due to: ", e);
        }
        return passwordHash;
    }


    public static boolean validatePassword(String originalPassword, String storedPassword, String storedSalt, Integer storedIterations) {
        int diff = 1;
        try {
            byte[] salt = fromHex(storedSalt);
            byte[] hash = fromHex(storedPassword);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Validation\nSalt : {} \nHash : {}", Arrays.toString(salt), Arrays.toString(hash));
            }

            PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, storedIterations, hash.length * 8);

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] testHash = skf.generateSecret(spec).getEncoded();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("storedPass : {} \n" + "test hash : {}", storedPassword, Arrays.toString(testHash));
            }

            diff = hash.length ^ testHash.length;

            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Error in validating hashed password due to: ", e);
        }
        return diff == 0;

    }


    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }


    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

}
