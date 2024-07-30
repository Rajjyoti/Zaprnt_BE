package com.zaprent.service.user.impl;

import com.zaprent.service.user.IAuthService;
import com.zaprent.service.user.IUserService;
import com.zaprent.service.user.IUserValidationService;
import com.zaprnt.beans.dtos.request.user.AuthRequest;
import com.zaprnt.beans.dtos.request.user.UserCreateRequest;
import com.zaprnt.beans.dtos.request.user.UserRequest;
import com.zaprnt.beans.dtos.request.user.UserUpdateRequest;
import com.zaprnt.beans.dtos.response.user.UserResponse;
import com.zaprnt.beans.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.zaprent.utils.UserUtils.convertToUserResponse;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserService userService;
    private final IUserValidationService userValidationService;

    @Override
    public UserResponse signUp(UserCreateRequest request) {
        if (isNull(request)) {
            return null;
        }
        return userService.createUser(request);
    }

    @Override
    public UserResponse signIn(AuthRequest request) {
        if (isNull(request)) {
            return null;
        }
        User user = userService.findUser(new UserRequest().setUserName(request.getUserNameOrEmail()).setEmail(request.getUserNameOrEmail()));
        userValidationService.validateUserCredentials(user, request.getPassword());
        return userService.updateUser(new UserUpdateRequest().setUserId(user.getId()).setLastLogin(System.currentTimeMillis()).setMiddleName(user.getMiddleName()).setProfilePicUrl(user.getProfilePicUrl()));
    }

    @Override
    public boolean signOut(String id) {
        User user = userService.findUser(new UserRequest().setUserId(id));
        return true;
    }
}
