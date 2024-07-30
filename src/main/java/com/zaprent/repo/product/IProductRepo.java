package com.zaprent.repo.product;

import com.zaprnt.beans.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepo extends MongoRepository<Product, String>, CustomProductRepository {
}
