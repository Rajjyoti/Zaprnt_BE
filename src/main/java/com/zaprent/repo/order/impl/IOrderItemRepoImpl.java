package com.zaprent.repo.order.impl;

import com.mongodb.client.result.DeleteResult;
import com.zaprent.repo.order.CustomOrderItemRepository;
import com.zaprnt.beans.dtos.OrderItemStatusDetail;
import com.zaprnt.beans.enums.OrderStatus;
import com.zaprnt.beans.models.OrderItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.zaprnt.beans.constants.Constants.DELETED_FIELD;
import static com.zaprnt.beans.constants.Constants.ID_FIELD;

@Repository
public class IOrderItemRepoImpl implements CustomOrderItemRepository {
    private final MongoTemplate mongoTemplate;

    public IOrderItemRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return mongoTemplate.save(orderItem);
    }

    @Override
    public void saveOrderItems(List<OrderItem> orderItems) {
        mongoTemplate.insertAll(orderItems);
    }

    @Override
    public OrderItem getOrderItemById(String orderItemId) {
        return mongoTemplate.findOne(new Query(getDefaultCriteria().and(ID_FIELD).is(orderItemId)), OrderItem.class);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        return mongoTemplate.find(new Query(getDefaultCriteria().and("orderId").is(orderId)), OrderItem.class);
    }

    @Override
    public List<OrderItem> getOrderItemsByIds(String orderId, List<String> itemIds) {
        return mongoTemplate.find(new Query(getDefaultCriteria().and("orderId").is(orderId).and(ID_FIELD).in(itemIds)), OrderItem.class);
    }

    @Override
    public List<OrderItemStatusDetail> getOrderItemStatusDetailsForOrder(String orderId) {
        // Match stage to filter documents by orderId
        MatchOperation matchOperation = Aggregation.match(Criteria.where("orderId").is(orderId));

        // Group stage to group by status and count the number of documents in each group
        GroupOperation groupOperation = Aggregation.group("status")
                .count().as("count");

        // Projection stage to format the output
        ProjectionOperation projectionOperation = Aggregation.project()
                .and(ID_FIELD).as("status")
                .and("count").as("count");

        // Build the aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation, projectionOperation);

        // Execute the aggregation
        AggregationResults<OrderItemStatusDetail> results = mongoTemplate.aggregate(aggregation, OrderItem.class, OrderItemStatusDetail.class);

        return results.getMappedResults();
    }

    @Override
    public OrderStatus getLowestOrderStatusFromOrderItems(String orderId) {
        // Match stage to filter documents by orderId
        MatchOperation matchOperation = Aggregation.match(Criteria.where("orderId").is(orderId));

        // Sort stage to sort by status ordinal value in descending order
        SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Order.asc("status")));

        // Limit stage to get the highest status
        TypedAggregation<OrderItem> aggregation = Aggregation.newAggregation(OrderItem.class, matchOperation, sortOperation, Aggregation.limit(1));

        // Execute the aggregation
        AggregationResults<OrderItem> results = mongoTemplate.aggregate(aggregation, OrderItem.class);

        // Get the highest status or return a default value if no result is found
        OrderItem highestStatusOrderItem = results.getUniqueMappedResult();
        return highestStatusOrderItem != null ? highestStatusOrderItem.getStatus() : OrderStatus.PENDING_APPROVAL;
    }

    @Override
    public boolean deleteOrderItem(String id) {
        DeleteResult result = mongoTemplate.remove(new Query(Criteria.where(ID_FIELD).is(id)), OrderItem.class);
        return result.wasAcknowledged();
    }

    private Criteria getDefaultCriteria() {
        return Criteria.where(DELETED_FIELD).is(false);
    }

}
