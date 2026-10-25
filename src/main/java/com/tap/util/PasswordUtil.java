package com.tap.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Work factor (10 to 12 is standard and secure)
    private static final int LOG_ROUNDS = 12;

    /**
     * Hashes a plain-text password with a randomly generated salt.
     * Use during Sign Up / Password Reset.
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(LOG_ROUNDS));
    }

    /**
     * Checks if a plain-text candidate password matches an existing BCrypt hash.
     * Use during Sign In / Login.
     */
    public static boolean checkPassword(String candidatePassword, String storedHash) {
        if (candidatePassword == null || storedHash == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(candidatePassword, storedHash);
        } catch (IllegalArgumentException e) {
            // Catches any malformed hashes
            return false;
        }
    }
}