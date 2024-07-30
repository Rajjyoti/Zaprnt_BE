package com.zaprnt.beans.dtos.request.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserRequest {
    private String userId;
    private String email;
    private String userName;
}
