package com.filedatasample.demo.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@SuppressWarnings({"unchecked", "rawtypes"})
@RestControllerAdvice
public class CustomExceptionControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<com.filedatasample.demo.exception.ErrorResponse> handleApiException(CustomerException ex) {
        com.filedatasample.demo.exception.ErrorResponse error = new com.filedatasample.demo.exception.ErrorResponse(ex.getHttpStatus(), ex.getMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }
}
