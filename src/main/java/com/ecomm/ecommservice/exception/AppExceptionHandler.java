package com.ecomm.ecommservice.exception;

import com.ecomm.ecommservice.dto.response.ApiResponse;
import com.ecomm.ecommservice.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthenticationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(
                ApiResponse.error(ErrorResponse.buildErrorResponseWithMessage("Not authorized", HttpStatus.UNAUTHORIZED)),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler({InsufficientAuthenticationException.class})
    public ResponseEntity<ApiResponse<String>> handleInsufficientAuthenticationException(InsufficientAuthenticationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(
                ApiResponse.error(ErrorResponse.buildErrorResponseWithMessage("Insufficient privilege", HttpStatus.FORBIDDEN)),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String message = fieldError.getDefaultMessage();

            errors.add(message);
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
                ApiResponse.error(ErrorResponse.buildErrorResponseWithMessage("Something went wrong", HttpStatus.BAD_REQUEST)),
                HttpStatus.BAD_REQUEST
        );
    }
}
