package com.zaprnt.service;

import com.zaprnt.beans.models.Metadata;
import com.zaprnt.repo.IMetadataRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetadataService {
    private final IMetadataRepo metadataRepo;

    public Metadata saveMetadata(Metadata metadata) {
        return metadataRepo.saveMetadata(metadata);
    }

    public Metadata getMetadata(String type) {
        return metadataRepo.getMetadata(type);
    }
}
