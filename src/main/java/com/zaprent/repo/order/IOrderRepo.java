package com.zaprent.repo.order;

import com.zaprnt.beans.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepo extends MongoRepository<Order, String>, CustomOrderRepository {
}
