package com.zaprent;

import com.zaprnt.beans.error.ErrorDetails;
import com.zaprnt.beans.error.ZException;
import com.zaprnt.beans.error.ZResponseEntityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Map;

import static java.util.Objects.nonNull;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ZException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(ZException ex, Locale locale) {
        String errorMessage;
        try{
            errorMessage = messageSource.getMessage(ex.getMessageKey(), ex.getParams(), locale);
        } catch (NoSuchMessageException e) {
            errorMessage = ex.getMessageKey();
        }
        ErrorDetails errorDetails = new ErrorDetails(errorMessage, ex.getErrorCode());
        return ZResponseEntityBuilder.errorResponseEntity(errorDetails, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return ZResponseEntityBuilder.errorResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

