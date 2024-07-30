package com.zaprnt.beans.models;

import com.zaprnt.beans.common.BaseMongoBean;
import com.zaprnt.beans.enums.AccountStatus;
import com.zaprnt.beans.enums.ContactType;
import com.zaprnt.beans.enums.UserType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Accessors(chain = true)
@Document
@EqualsAndHashCode(callSuper = true)
public class User extends BaseMongoBean {
    @Indexed(unique = true)
    private String userName;
    @Indexed(unique = true)
    private String email;
    private String password;
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
    private int renterRatingCount;
    private int lenderRatingCount;
    private ContactType preferredContact;
    private Long lastLogin;
}
