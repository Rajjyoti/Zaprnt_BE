package com.zaprent.repo.order;

import com.zaprnt.beans.models.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderItemRepo extends MongoRepository<OrderItem, String>, CustomOrderItemRepository{
}
