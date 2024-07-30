package com.zaprent.service.user;

import com.zaprnt.beans.dtos.request.user.AuthRequest;
import com.zaprnt.beans.dtos.request.user.UserCreateRequest;
import com.zaprnt.beans.dtos.response.user.UserResponse;

public interface IAuthService {
    UserResponse signUp(UserCreateRequest request);
    UserResponse signIn(AuthRequest request);
    boolean signOut(String id);
}
