package com.zaprent.repo.cart;

import com.zaprnt.beans.models.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepo extends MongoRepository<CartItem, String>, CustomCartRepository {
}
