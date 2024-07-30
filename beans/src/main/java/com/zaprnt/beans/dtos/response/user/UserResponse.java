package com.zaprnt.beans.dtos.response.user;

import com.zaprnt.beans.enums.AccountStatus;
import com.zaprnt.beans.enums.ContactType;
import com.zaprnt.beans.enums.UserType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Accessors(chain = true)
public class UserResponse {
    private String id;
    private String userName;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private Date dateOfBirth;
    private String profilePicUrl;
    private AccountStatus accountStatus;
    private Set<UserType> roles;
    private BigDecimal renterRating;
    private BigDecimal lenderRating;
    private ContactType preferredContact;
    private Long lastLogin;
    private String createdBy;
    private String lastModifiedBy;
    private boolean deleted;
    private Long createdTime;
    private Long modifiedTime;
}
