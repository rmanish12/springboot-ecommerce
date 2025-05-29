package com.ecomm.ecommservice.exception;

public class ConflictException extends RuntimeException{

    public ConflictException() {};

    public ConflictException(String resourceName, String field, String fieldName) {
        super(String.format("%s with %s : %s already exists", resourceName, field, fieldName));
    }

    public ConflictException(String resourceName, String field, Long fieldId) {
        super(String.format("%s with %s : %d already exists", resourceName, field, fieldId));
    }

}
