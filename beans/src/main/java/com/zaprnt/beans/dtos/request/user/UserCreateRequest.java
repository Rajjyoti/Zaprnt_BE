package com.zaprnt.beans.dtos.request.user;

import com.zaprnt.beans.enums.ContactType;
import com.zaprnt.beans.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserCreateRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    private String middleName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private Date dateOfBirth;
    private String profilePicUrl;
    @NotNull
    private UserType role;
    private ContactType preferredContact;
}
