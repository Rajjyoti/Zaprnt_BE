package com.zaprent.repo.wishList.impl;

import com.mongodb.client.result.DeleteResult;
import com.zaprent.repo.wishList.CustomWishListRepository;
import com.zaprnt.beans.models.WishList;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.zaprnt.beans.constants.Constants.ID_FIELD;

@Repository
public class IWishListRepoImpl implements CustomWishListRepository {

    private final MongoTemplate mongoTemplate;

    public IWishListRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public WishList saveWishList(WishList wishList) {
        return mongoTemplate.save(wishList);
    }

    @Override
    public List<WishList> getWishListByUserId(String userId) {
        return mongoTemplate.find(new Query(Criteria.where("userId").is(userId)), WishList.class);
    }

    @Override
    public WishList getWishListItemById(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where(ID_FIELD).is(id)), WishList.class);
    }

    @Override
    public boolean removeFromWishList(String id) {
        DeleteResult result = mongoTemplate.remove(new Query(Criteria.where(ID_FIELD).is(id)));
        return result.wasAcknowledged();
    }
}
