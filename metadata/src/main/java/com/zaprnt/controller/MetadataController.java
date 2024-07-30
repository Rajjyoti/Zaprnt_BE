package com.zaprnt.controller;

import com.zaprnt.beans.models.Metadata;
import com.zaprnt.service.MetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/metadata")
public class MetadataController {
    private final MetadataService metadataService;
    @GetMapping("/types/{type}")
    public ResponseEntity<Object> getProductMetadata(@PathVariable String type) {
        return new ResponseEntity<>(metadataService.getMetadata(type), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> saveProductMetadata(@RequestBody Metadata request) {
        return new ResponseEntity<>(metadataService.saveMetadata(request), HttpStatus.CREATED);
    }
}
