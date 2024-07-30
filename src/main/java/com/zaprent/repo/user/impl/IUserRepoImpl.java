package com.zaprent.repo.user.impl;

import com.mongodb.client.result.DeleteResult;
import com.zaprent.repo.user.CustomUserRepository;
import com.zaprnt.beans.models.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.zaprnt.beans.constants.Constants.*;

@Repository
public class IUserRepoImpl implements CustomUserRepository {

    private final MongoTemplate mongoTemplate;

    public IUserRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User saveUser(User user) {
        return mongoTemplate.save(user);
    }
    @Override
    public void saveUsersInBulk(List<User> users) {
        mongoTemplate.insertAll(users);
    }

    @Override
    public User findUserById(String id) {
        return mongoTemplate.findOne(new Query(getDefaultCriteria().and(ID_FIELD).is(id)), User.class);
    }

    @Override
    public User findUserByEmail(String email) {
        return mongoTemplate.findOne(new Query(getDefaultCriteria().and(EMAIL_FIELD).is(email)), User.class);
    }

    @Override
    public User findUserByUserName(String userName) {
        return mongoTemplate.findOne(new Query(getDefaultCriteria().and(USERNAME_FIELD).is(userName)), User.class);
    }

    @Override
    public boolean deleteUserById(String id) {
        DeleteResult result = mongoTemplate.remove(new Query(Criteria.where(ID_FIELD).is(id)));
        return result.wasAcknowledged();
    }

    private Criteria getDefaultCriteria() {
        return Criteria.where(DELETED_FIELD).is(false);
    }
}
