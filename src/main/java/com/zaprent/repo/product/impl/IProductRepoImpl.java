package com.zaprent.repo.product.impl;

import com.mongodb.client.result.DeleteResult;
import com.zaprent.repo.product.CustomProductRepository;
import com.zaprnt.beans.enums.Category;
import com.zaprnt.beans.models.Product;
import com.zaprnt.beans.models.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.zaprnt.beans.constants.Constants.*;

@Repository
public class IProductRepoImpl implements CustomProductRepository {

    private final MongoTemplate mongoTemplate;

    public IProductRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Product saveProduct(Product product) {
        return mongoTemplate.save(product);
    }

    @Override
    public Product getProductById(String id) {
        return mongoTemplate.findOne(new Query(getDefaultCriteria().and(ID_FIELD).is(id)), Product.class);
    }

    @Override
    public List<Product> getProductsByIds(List<String> ids) {
        return mongoTemplate.find(new Query(getDefaultCriteria().and(ID_FIELD).in(ids)), Product.class);
    }

    @Override
    public Product getProductByOwnerId(String id) {
        return mongoTemplate.findOne(new Query(getDefaultCriteria().and("ownerId").is(id)), Product.class);
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return mongoTemplate.find(new Query(getDefaultCriteria().and("category").is(category)), Product.class);
    }

    @Override
    public boolean deleteProductById(String id) {
        DeleteResult deleteResult = mongoTemplate.remove(new Query(Criteria.where(ID_FIELD).is(id)));
        return deleteResult.wasAcknowledged();
    }

    private Criteria getDefaultCriteria() {
        return Criteria.where(DELETED_FIELD).is(false);
    }
}
