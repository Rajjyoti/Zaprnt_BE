package com.zaprnt.beans.models;

import com.zaprnt.beans.common.mongo.BaseMongoBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document
@EqualsAndHashCode(callSuper = true)
@CompoundIndex(name = "userId_productId_review", def = "{'userId': 1, 'productId': 1}", unique = true)
public class Review extends BaseMongoBean {
    @Indexed
    private String userId;
    @Indexed
    private String productId;
    private int rating;
    private String title;
    private String comment;
}
