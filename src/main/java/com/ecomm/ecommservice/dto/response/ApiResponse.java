package com.ecomm.ecommservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponse<T> {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = " FAILURE";

    private HttpStatus statusCode;

    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    private ZonedDateTime responseTime;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(SUCCESS)
                .statusCode(HttpStatus.OK)
                .responseTime(ZonedDateTime.now(ZoneOffset.UTC))
                .data(data)
                .build();
    }

    public static ApiResponse<Void> successWithMessage(String message) {
        return ApiResponse.<Void>builder()
                .status(SUCCESS)
                .statusCode(HttpStatus.OK)
                .responseTime(ZonedDateTime.now(ZoneOffset.UTC))
                .message(message)
                .build();
    }

    public static ApiResponse<Void> successWithMessage(String message, HttpStatus statusCode) {
        return ApiResponse.<Void>builder()
                .status(SUCCESS)
                .statusCode(statusCode)
                .responseTime(ZonedDateTime.now(ZoneOffset.UTC))
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> error(ErrorResponse errorResponse) {
        return ApiResponse.<T>builder()
                .status(FAILURE)
                .statusCode(errorResponse.getStatusCode())
                .responseTime(ZonedDateTime.now(ZoneOffset.UTC))
                .error(errorResponse.getMessage())
                .errors(errorResponse.getErrors())
                .build();
    }
}

