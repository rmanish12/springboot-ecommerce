package com.ecomm.ecommservice.exception;

import com.ecomm.ecommservice.dto.response.ApiResponse;
import com.ecomm.ecommservice.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();

            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(
                ApiResponse.error(ErrorResponse.buildErrorResponseWithErrors(errors, HttpStatus.BAD_REQUEST)),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> resourceConflictException(ConflictException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(
                ApiResponse.error(ErrorResponse.buildErrorResponseWithMessage(e.getMessage(), HttpStatus.CONFLICT)),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> resourceNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(
                ApiResponse.error(ErrorResponse.buildErrorResponseWithMessage(e.getMessage(), HttpStatus.NOT_FOUND)),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> resourceException(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(
                ApiResponse.error(ErrorResponse.buildErrorResponseWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST)),
                HttpStatus.BAD_REQUEST
        );
    }
}
