package com.zaprnt.beans.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ZResponseEntityBuilder {

    public static ResponseEntity<Map<String, Object>> okResponseEntity(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Map<String, Object>> okResponseEntity(Object data, HttpHeaders headers) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", data);
        return ResponseEntity.ok().headers(headers).body(response);
    }

    public static ResponseEntity<Map<String, Object>> okCreatedResponseEntity(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public static ResponseEntity<Map<String, Object>> okCreatedResponseEntity(Object data, HttpHeaders headers) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", data);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
    }

    public static ResponseEntity<Map<String, Object>> errorResponseEntity(Object errorDetails, HttpStatus httpStatus, HttpHeaders headers) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "failed");
        response.put("errorDetails", errorDetails);
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).headers(headers).body(response);
    }

    public static ResponseEntity<Map<String, Object>> errorResponseEntity(Object errorDetails, HttpStatus httpStatus) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "failed");
        response.put("errorDetails", errorDetails);
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}

