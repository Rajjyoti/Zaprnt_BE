package com.zaprent.helper;

public interface PasswordHasher {
    String hashPassword(String password);
    boolean verifyPassword(String rawPassword, String hashedPassword);
}
