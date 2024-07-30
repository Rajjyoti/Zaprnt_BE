package com.zaprent.repo.user;

import com.zaprnt.beans.models.User;

import java.util.List;

public interface CustomUserRepository {
    User saveUser(User user);

    void saveUsersInBulk(List<User> users);

    User findUserById(String id);

    User findUserByEmail(String email);

    User findUserByUserName(String phone);

    boolean deleteUserById(String id);
}
