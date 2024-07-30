package com.zaprent.repo.jwt;

import com.zaprnt.beans.models.JwtData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJwtRepo extends MongoRepository<JwtData, String>, CustomJwtRepository {
}
