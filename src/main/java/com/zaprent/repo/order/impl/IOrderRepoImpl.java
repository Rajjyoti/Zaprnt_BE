package com.zaprent.repo.order.impl;

import com.mongodb.client.result.DeleteResult;
import com.zaprent.repo.order.CustomOrderRepository;
import com.zaprnt.beans.models.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.zaprnt.beans.constants.Constants.DELETED_FIELD;
import static com.zaprnt.beans.constants.Constants.ID_FIELD;

@Repository
public class IOrderRepoImpl implements CustomOrderRepository {
    private final MongoTemplate mongoTemplate;

    public IOrderRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Order saveOrder(Order order) {
        return mongoTemplate.save(order);
    }

    @Override
    public Order getOrderById(String orderId) {
        return mongoTemplate.findOne(new Query(getDefaultCriteria().and(ID_FIELD).is(orderId)), Order.class);
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return mongoTemplate.find(new Query(getDefaultCriteria().and("userId").is(userId)), Order.class);
    }

    @Override
    public boolean deleteOrder(String id) {
        DeleteResult result = mongoTemplate.remove(new Query(Criteria.where(ID_FIELD).is(id)));
        return result.wasAcknowledged();
    }

    private Criteria getDefaultCriteria() {
        return Criteria.where(DELETED_FIELD).is(false);
    }
}
