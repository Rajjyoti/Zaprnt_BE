package com.zaprnt.repo;

import com.mongodb.client.result.DeleteResult;
import com.zaprnt.beans.models.Metadata;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class IMetadataRepoImpl implements CustomMetadataRepository {
    private final MongoTemplate mongoTemplate;

    public IMetadataRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public Metadata saveMetadata(Metadata metadata) {
        return mongoTemplate.save(metadata);
    }

    @Override
    public Metadata getMetadata(String type) {
        return mongoTemplate.findOne(new Query(Criteria.where("type").is(type)), Metadata.class);
    }

    @Override
    public boolean deleteMetadata(String type) {
        DeleteResult result = mongoTemplate.remove(new Query(Criteria.where("type").is(type)));
        return result.wasAcknowledged();
    }
}
