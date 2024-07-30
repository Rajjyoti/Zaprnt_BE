package com.zaprent.utils;

import com.zaprnt.beans.dtos.request.user.UserCreateRequest;
import com.zaprnt.beans.dtos.request.user.UserUpdateRequest;
import com.zaprnt.beans.dtos.response.user.UserResponse;
import com.zaprnt.beans.enums.AccountStatus;
import com.zaprnt.beans.enums.UserType;
import com.zaprnt.beans.models.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

import static io.micrometer.common.util.StringUtils.isNotBlank;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class UserUtils {
    public static User getUserFromUserCreateRequest(UserCreateRequest request) {
        if (isNull(request)) {
            return null;
        }
        return new User()
                .setUserName(request.getUserName())
                .setEmail(request.getEmail())
                .setRoles(getUserTypesFromCreateRequest(request.getRole()))
                .setAccountStatus(AccountStatus.ACTIVE)
                .setLastLogin(System.currentTimeMillis())
                .setDateOfBirth(request.getDateOfBirth())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setMiddleName(request.getMiddleName())
                .setPassword(request.getPassword())
                .setPhoneNumber(request.getPhoneNumber())
                .setPreferredContact(request.getPreferredContact())
                .setProfilePicUrl(request.getProfilePicUrl());
    }

    public static void updateUserFromUpdateRequest(User user, UserUpdateRequest request) {
        if (isNull(request)) {
            return;
        }
        if (isNotBlank(request.getUserName())) {
            user.setUserName(request.getUserName());
        }
        if (isNotBlank(request.getEmail())) {
            user.setEmail(request.getEmail());
        }
        if (nonNull(request.getUserType())) {
            user.setRoles(addUserType(user.getRoles(), request.getUserType()));
        }
        if (nonNull(request.getAccountStatus())) {
            user.setAccountStatus(request.getAccountStatus());
        }
        if (nonNull(request.getDateOfBirth())) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
        if (isNotBlank(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }
        if (isNotBlank(request.getLastName())) {
            user.setLastName(request.getLastName());
        }
        if (isNotBlank(request.getPhoneNumber())) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (nonNull(request.getLastLogin())) {
            user.setLastLogin(request.getLastLogin());
        }
        if (nonNull(request.getPreferredContact())) {
            user.setPreferredContact(request.getPreferredContact());
        }
        user.setMiddleName(request.getMiddleName());
        user.setProfilePicUrl(request.getProfilePicUrl());
    }

    public static void updateRenterRating(User user, BigDecimal newRating) {
        BigDecimal newAverage = calculateNeAverageRating(user.getRenterRating(), user.getRenterRatingCount(), newRating);
        user.setRenterRating(newAverage);
        user.setRenterRatingCount(user.getRenterRatingCount() + 1);
    }

    public static void updateLenderRating(User user, BigDecimal newRating) {
        BigDecimal newAverage = calculateNeAverageRating(user.getLenderRating(), user.getLenderRatingCount(), newRating);
        user.setLenderRating(newAverage);
        user.setLenderRatingCount(user.getLenderRatingCount() + 1);
    }

    public static Set<UserType> addUserType(Set<UserType> userTypes, UserType userType) {
        if (isNull(userTypes)) {
            userTypes = new HashSet<>();
        }
        userTypes.add(userType);
        return userTypes;
    }

    public static Set<UserType> getUserTypesFromCreateRequest(UserType userType) {
        Set<UserType> userTypes = new HashSet<>();
        userTypes.add(userType);
        return userTypes;
    }

    public static UserResponse convertToUserResponse(User user) {
        if (isNull(user)) {
            return null;
        }
        return new UserResponse()
                .setId(user.getId())
                .setUserName(user.getUserName())
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setMiddleName(user.getMiddleName())
                .setLastName(user.getLastName())
                .setLastLogin(user.getLastLogin())
                .setPhoneNumber(user.getPhoneNumber())
                .setDateOfBirth(user.getDateOfBirth())
                .setAccountStatus(user.getAccountStatus())
                .setRoles(user.getRoles())
                .setRenterRating(user.getRenterRating())
                .setLenderRating(user.getLenderRating())
                .setPreferredContact(user.getPreferredContact())
                .setLastLogin(user.getLastLogin())
                .setCreatedBy(user.getCreatedBy())
                .setModifiedTime(user.getModifiedTime())
                .setCreatedTime(user.getCreatedTime())
                .setLastModifiedBy(user.getLastModifiedBy());
    }

    private static BigDecimal calculateNeAverageRating(BigDecimal oldAverage, int numberOfRatings, BigDecimal newRating) {
        return  (oldAverage.multiply(BigDecimal.valueOf(numberOfRatings)).add(newRating)).divide(BigDecimal.valueOf(numberOfRatings).add(BigDecimal.ONE), 1, RoundingMode.HALF_UP);
    }
}
