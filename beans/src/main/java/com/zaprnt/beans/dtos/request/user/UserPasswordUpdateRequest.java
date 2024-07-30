package com.zaprnt.beans.dtos.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserPasswordUpdateRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
}
