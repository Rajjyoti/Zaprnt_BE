package com.zaprnt.repo;

import com.zaprnt.beans.models.Metadata;

public interface CustomMetadataRepository {
    Metadata saveMetadata(Metadata metadata);
    Metadata getMetadata(String type);
    boolean deleteMetadata(String type);
}
