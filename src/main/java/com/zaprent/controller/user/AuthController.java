package com.zaprent.controller.user;

import com.zaprent.service.user.IAuthService;
import com.zaprnt.beans.dtos.request.user.AuthRequest;
import com.zaprnt.beans.dtos.request.user.UserCreateRequest;
import com.zaprnt.beans.dtos.response.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.zaprent.utils.JWTUtils.getResponseHeadersForAuth;
import static com.zaprnt.beans.error.ZResponseEntityBuilder.okCreatedResponseEntity;
import static com.zaprnt.beans.error.ZResponseEntityBuilder.okResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody UserCreateRequest request) {
        UserResponse response = authService.signUp(request);
        HttpHeaders headers = getResponseHeadersForAuth(response.getUserName(), response.getEmail(), response.getRoles());
        return okCreatedResponseEntity(response, headers);
    }

    //TODO: what if user is already signed in?
    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signIn(@Valid @RequestBody AuthRequest request) {
        UserResponse response = authService.signIn(request);
        HttpHeaders headers = getResponseHeadersForAuth(response.getUserName(), response.getEmail(), response.getRoles());
        return okResponseEntity(response, headers);
    }

    @PostMapping("/signout/{id}")
    public ResponseEntity<Map<String, Object>> signOut(@PathVariable String id) {
        authService.signOut(id);
        return okResponseEntity("Signed Out");
    }
}
