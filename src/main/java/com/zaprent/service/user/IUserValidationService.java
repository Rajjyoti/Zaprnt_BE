package com.zaprent.service.user;

import com.zaprnt.beans.models.User;

public interface IUserValidationService {
    void validatePasswordForCreation(String newPassword);

    void validatePasswordForUpdation(String newPassword, String existingPassword);

    void validateUserCredentials(User user, String password);
}
