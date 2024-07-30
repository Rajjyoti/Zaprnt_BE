package com.zaprent.service.user.impl;

import com.zaprent.helper.PasswordHasher;
import com.zaprent.service.user.IUserValidationService;
import com.zaprnt.beans.error.ZError;
import com.zaprnt.beans.error.ZException;
import com.zaprnt.beans.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class UserValidationServiceImpl implements IUserValidationService {
    private final PasswordHasher passwordHasher;
    @Override
    public void validatePasswordForCreation(String newPassword) {
        return;
    }

    @Override
    public void validatePasswordForUpdation(String newPassword, String existingPassword) {
        if (!passwordHasher.verifyPassword(newPassword, existingPassword)) {
            throw new ZException("password should not be same as recent password");
        }
    }

    @Override
    public void validateUserCredentials(User user, String password) {
        if (isNull(user)) {
            throw new ZException(ZError.USER_NOT_FOUND);
        }
        if (!passwordHasher.verifyPassword(password, user.getPassword())) {
            throw new ZException(ZError.INCORRECT_PASSWORD);
        }
    }
}
