package com.zaprent.service.user;

import com.zaprnt.beans.dtos.request.user.*;
import com.zaprnt.beans.dtos.response.user.UserResponse;
import com.zaprnt.beans.models.User;

public interface IUserService {
    UserResponse createUser(UserCreateRequest request);

    UserResponse updateUser(UserUpdateRequest request);

    UserResponse updatePassword(UserPasswordUpdateRequest request);
    UserResponse updateUserRating(UserRatingUpdateRequest request);

    boolean deleteUser(String id);

    User findUser(UserRequest request);
    User findUserById(String id);

    void deleteAllUsers();
}
