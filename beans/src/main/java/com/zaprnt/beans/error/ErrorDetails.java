package com.zaprnt.beans.error;

public class ErrorDetails {
    private String message;
    private String key;

    public ErrorDetails(String message, String key) {
        this.message = message;
        this.key = key;
    }

    public ErrorDetails(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
