package com.zaprent.repo.jwt;

import com.zaprnt.beans.models.JwtData;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class IJwtRepoImpl implements CustomJwtRepository {

    private final MongoTemplate mongoTemplate;

    public IJwtRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveJwtData(JwtData data) {
        mongoTemplate.save(data);
    }

    @Override
    public JwtData getJwtDataByToken(String token) {
        return mongoTemplate.findOne(new Query(Criteria.where("jwt").is(token)), JwtData.class);
    }
}
