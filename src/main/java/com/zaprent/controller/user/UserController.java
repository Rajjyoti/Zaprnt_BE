package com.zaprent.controller.user;

import com.zaprent.service.user.IUserService;
import com.zaprnt.beans.dtos.request.user.UserCreateRequest;
import com.zaprnt.beans.dtos.request.user.UserPasswordUpdateRequest;
import com.zaprnt.beans.dtos.request.user.UserRatingUpdateRequest;
import com.zaprnt.beans.dtos.request.user.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.zaprnt.beans.error.ZResponseEntityBuilder.okCreatedResponseEntity;
import static com.zaprnt.beans.error.ZResponseEntityBuilder.okResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/ids/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable String id) {
        return okResponseEntity(userService.findUser(new UserRequest().setUserId(id)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody UserCreateRequest request) {
        return okCreatedResponseEntity(userService.createUser(request));
    }

    @PutMapping("/password")
    public ResponseEntity<Map<String, Object>> updateUserPassword(@Valid @RequestBody UserPasswordUpdateRequest request) {
        return okResponseEntity(userService.updatePassword(request));
    }

    @PutMapping("/rating")
    public ResponseEntity<Map<String, Object>> updateUserRating(@Valid @RequestBody UserRatingUpdateRequest request) {
        return okResponseEntity(userService.updateUserRating(request));
    }


    /**
     * Delete APIs
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteUserById(@PathVariable String userId) {
        return okResponseEntity(userService.deleteUser(userId));
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Map<String, Object>> deleteAllUsers() {
        userService.deleteAllUsers();
        return okResponseEntity("Deleted");
    }
}
