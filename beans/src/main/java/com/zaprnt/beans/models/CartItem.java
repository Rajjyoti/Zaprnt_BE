package com.zaprnt.beans.models;

import com.zaprnt.beans.common.BaseMongoBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Document
@EqualsAndHashCode(callSuper = true)
@CompoundIndex(name = "productId_userId_cart", def = "{'productId': 1, 'userId': 1}", unique = true)
public class CartItem extends BaseMongoBean {
    private String productId;
    @Indexed
    private String userId;
    private int quantity;
    private BigDecimal price;
}
