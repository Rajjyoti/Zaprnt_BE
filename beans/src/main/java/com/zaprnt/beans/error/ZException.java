package com.zaprnt.beans.error;

import org.springframework.http.HttpStatus;

public class ZException extends RuntimeException {
    private String messageKey;
    private String errorCode;
    private String[] params;
    private HttpStatus statusCode = DEFAULT_STATUS_CODE;
    private static final HttpStatus DEFAULT_STATUS_CODE = HttpStatus.INTERNAL_SERVER_ERROR;

    public ZException(ZError error, String... params) {
        super(error.getKey());
        this.messageKey = error.getKey();
        this.errorCode = error.getCode();
        this.params = params;
    }

    public ZException(ZError error, HttpStatus status, String... params) {
        super(error.getKey());
        this.messageKey = error.getKey();
        this.errorCode = error.getCode();
        this.params = params;
        this.statusCode = status;
    }

    public ZException(String message) {
        super(message);
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String[] getParams() {
        return params;
    }
}

