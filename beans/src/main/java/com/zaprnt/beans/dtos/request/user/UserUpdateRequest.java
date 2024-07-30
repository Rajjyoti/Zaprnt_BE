package com.zaprnt.beans.dtos.request.user;

import com.zaprnt.beans.enums.AccountStatus;
import com.zaprnt.beans.enums.ContactType;
import com.zaprnt.beans.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserUpdateRequest {
    @NotBlank
    private String userId;
    private String userName;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private Date dateOfBirth;
    private String profilePicUrl;
    private Long lastLogin;
    private AccountStatus accountStatus;
    private UserType userType;
    private ContactType preferredContact;
}
