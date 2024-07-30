package com.zaprent.repo.cart.impl;

import com.mongodb.client.result.DeleteResult;
import com.zaprent.repo.cart.CustomCartRepository;
import com.zaprnt.beans.models.CartItem;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.zaprnt.beans.constants.Constants.DELETED_FIELD;
import static com.zaprnt.beans.constants.Constants.ID_FIELD;

@Repository
public class ICartRepoImpl implements CustomCartRepository {
    private final MongoTemplate mongoTemplate;

    public ICartRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public CartItem saveCartItem(CartItem item) {
        return mongoTemplate.save(item);
    }

    @Override
    public CartItem getCartItemById(String cartId) {
        return mongoTemplate.findOne(new Query(getDefaultCriteria().and("_id").is(cartId)), CartItem.class);
    }

    @Override
    public CartItem getCartItemByProductIdAndUserId(String userId, String productId) {
        return mongoTemplate.findOne(new Query(getDefaultCriteria().and("userId").is(userId).and("productId").is(productId)), CartItem.class);
    }

    @Override
    public List<CartItem> getCartItemsByUserId(String userId) {
        return mongoTemplate.find(new Query(getDefaultCriteria().and("userId").is(userId)), CartItem.class);
    }

    @Override
    public boolean removeCartItemById(String id) {
        DeleteResult result = mongoTemplate.remove(new Query(Criteria.where(ID_FIELD).is(id)));
        return result.wasAcknowledged();
    }

    @Override
    public long removeCartItems(String userId, List<String> cartItemIds) {
        DeleteResult result = mongoTemplate.remove(new Query(Criteria.where("userId").is(userId).and("_id").in(cartItemIds)));
        return result.getDeletedCount();
    }

    private Criteria getDefaultCriteria() {
        return Criteria.where(DELETED_FIELD).is(false);
    }
}
