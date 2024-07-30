package com.zaprnt.repo;

import com.zaprnt.beans.models.Metadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMetadataRepo extends MongoRepository<Metadata, String>, CustomMetadataRepository {
}
