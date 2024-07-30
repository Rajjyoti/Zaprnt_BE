package com.zaprnt.beans.dtos.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class UserRatingUpdateRequest {
    @NotBlank
    private String userId;
    private BigDecimal renterRating;
    private BigDecimal lenderRating;
}
