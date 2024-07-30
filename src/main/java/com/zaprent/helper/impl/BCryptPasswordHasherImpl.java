package com.zaprent.helper.impl;

import com.zaprent.helper.PasswordHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BCryptPasswordHasherImpl implements PasswordHasher {
    private final PasswordEncoder encoder;
    @Override
    public String hashPassword(String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean verifyPassword(String rawPassword, String encodedPasswordWithSalt) {
        return encoder.matches(rawPassword, encodedPasswordWithSalt);
    }
}
