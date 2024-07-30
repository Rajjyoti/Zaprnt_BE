package com.zaprent.repo.user;

import com.zaprnt.beans.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends MongoRepository<User, String>, CustomUserRepository {

}
