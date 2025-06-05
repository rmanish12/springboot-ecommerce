package com.ecomm.ecommservice.exception;

import java.util.UUID;

public class BadCredentials extends RuntimeException{

    public BadCredentials() {};

    public BadCredentials(String resourceName, String field, String fieldName) {
        super(String.format("Bad credentials for %s with %s : %s", resourceName, field, fieldName));
    }

}
