package com.zaprent.service.user.impl;

import com.zaprent.helper.PasswordHasher;
import com.zaprent.repo.user.IUserRepo;
import com.zaprent.service.user.IUserService;
import com.zaprent.service.user.IUserValidationService;
import com.zaprnt.beans.dtos.request.user.*;
import com.zaprnt.beans.dtos.response.user.UserResponse;
import com.zaprnt.beans.error.ZError;
import com.zaprnt.beans.error.ZException;
import com.zaprnt.beans.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.zaprent.utils.UserUtils.*;
import static io.micrometer.common.util.StringUtils.isNotBlank;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepo  userRepo;
    private final PasswordHasher passwordHasher;
    private final IUserValidationService userValidationService;

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        if (isNull(request)) {
            return null;
        }
        User existingUser = findUser(new UserRequest().setUserName(request.getUserName()).setEmail(request.getEmail()));
        if (nonNull(existingUser)) {
            throw new ZException(ZError.USER_ALREADY_EXISTS, request.getUserName(), request.getEmail());
        }
        userValidationService.validatePasswordForCreation(request.getPassword());
        User user = getUserFromUserCreateRequest(request);
        user.setPassword(passwordHasher.hashPassword(user.getPassword()));
        return convertToUserResponse(userRepo.saveUser(user));
    }

    //TODO: need separate api for update phone number once OTP is in place.
    @Override
    public UserResponse updateUser(UserUpdateRequest request) {
        if (isNull(request)) {
            return null;
        }
        User user = findUserById(request.getUserId());
        updateUserFromUpdateRequest(user, request);
        return convertToUserResponse(userRepo.saveUser(user));
    }

    @Override
    public UserResponse updatePassword(UserPasswordUpdateRequest request) {
        if (isNull(request)) {
            return null;
        }
        User user = findUserById(request.getUserId());
        userValidationService.validatePasswordForUpdation(request.getPassword(), user.getPassword());
        user.setPassword(passwordHasher.hashPassword(request.getPassword()));
        return convertToUserResponse(userRepo.saveUser(user));
    }

    @Override
    public UserResponse updateUserRating(UserRatingUpdateRequest request) {
        if (isNull(request)) {
            return null;
        }
        User user = findUserById(request.getUserId());
        if (nonNull(request.getRenterRating())) {
            updateRenterRating(user, request.getRenterRating());
        }
        if (nonNull(request.getLenderRating())) {
            updateLenderRating(user, request.getLenderRating());
        }
        return convertToUserResponse(userRepo.saveUser(user));
    }

    @Override
    public User findUser(UserRequest request) {
        if (isNull(request)) {
            throw new RuntimeException("Request is null");
        }
        User user = null;
        if (isNotBlank(request.getUserId())) {
            user = findUserById(request.getUserId());
        }
        if (isNull(user) && isNotBlank(request.getUserName())) {
            user = userRepo.findUserByUserName(request.getUserName());
        }
        if (isNull(user) && isNotBlank(request.getEmail())) {
            user = userRepo.findUserByEmail(request.getEmail());
        }
        if (nonNull(user)) {
            return user;
        }
        else {
            throw new ZException(ZError.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public boolean deleteUser(String id) {
        return userRepo.deleteUserById(id);
    }

    @Override
    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    @Override
    public User findUserById(String id) {
        User user = userRepo.findUserById(id);
        if (isNull(user)) {
            throw new RuntimeException("User not found for id: " + id);
        }
        return user;
    }
}
