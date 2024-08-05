package com.zaprnt.beans.models;

import com.zaprnt.beans.common.mongo.BaseMongoBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Accessors(chain = true)
@Document
@EqualsAndHashCode(callSuper = true)
public class WishList extends BaseMongoBean {
    private String productId;
    @Indexed
    private String userId;
}
