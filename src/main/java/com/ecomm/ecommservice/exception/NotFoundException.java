package com.ecomm.ecommservice.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException{

    public NotFoundException() {};

    public NotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s with %s : %s already exists", resourceName, field, fieldName));
    }

    public NotFoundException(String resourceName, String field, UUID fieldId) {
        super(String.format("%s with %s : %s not found", resourceName, field, fieldId.toString()));
    }

}
