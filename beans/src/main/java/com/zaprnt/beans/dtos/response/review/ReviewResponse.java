package com.zaprnt.beans.dtos.response.review;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReviewResponse {
    private String id;
    private String userId;
    private String userName;
    private String userProfilePicUrl;
    private int rating;
    private String title;
    private String comment;
    private Long createdTime;
    private Long modifiedTime;
}
